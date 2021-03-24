package pers.peixinyi.jmeter.mq;

import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.internal.HexString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueManage {
    private String queueManagerName;
    private String host;
    private int port;
    private String channel;
    private int charset = 1392;

    private MQQueueManager queueManager;

    public void connect() throws MQException {
        MQEnvironment.channel = this.channel;
        MQEnvironment.port = this.port;
        MQEnvironment.hostname = this.host;
        MQEnvironment.CCSID = 1392;
        MQEnvironment.userID = "mqm";
        queueManager = new MQQueueManager("");
    }

    public void disconnect() throws MQException {
        if (this.queueManager != null) {
            this.queueManager.disconnect();
        }
    }

    public String putMessage(QueueMessage message, String queueName) {
        message.setMessage(message.getMessage());
        int openOptions = MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_OUTPUT;
        MQQueue queue = null;
        String msgId = null;
        try {
            queue = this.queueManager.accessQueue(queueName, openOptions);
            MQMessage messageObj = new MQMessage();
            MQPutMessageOptions putMessageOpt = new MQPutMessageOptions();
            putMessageOpt.options = MQConstants.MQPMO_FAIL_IF_QUIESCING | MQConstants.MQPMO_NEW_MSG_ID;
            messageObj.characterSet = this.charset;
            messageObj.format = MQConstants.MQFMT_STRING;
            messageObj.writeString(message.getMessage());
            if (message.getMessageId() != null) {
                messageObj.correlationId = HexString.parseHex(message.getMessageId());
            }
            queue.put(messageObj, putMessageOpt);
            msgId = HexString.hexString(messageObj.messageId);
            if (msgId == null) {
                throw new NullPointerException();
            }
        } catch (MQException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭队列
                if (queue != null) {
                    queue.close();
                }
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
        return msgId;
    }

    public QueueMessage getMessage(QueueMessage queueManage, String queueName, int waitInterval) throws MQException {
        int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_FAIL_IF_QUIESCING;
        MQQueue queue = null;
        MQMessage message = new MQMessage();
        try {
            queue = this.queueManager.accessQueue(queueName, openOptions);
            message.characterSet = this.charset;
            MQGetMessageOptions putMessageOpt = new MQGetMessageOptions();
            if (queueManage.getMessageId() != null) {
                putMessageOpt.options = putMessageOpt.options | MQConstants.MQGMO_FAIL_IF_QUIESCING | MQConstants.MQGMO_WAIT;
                putMessageOpt.matchOptions = MQConstants.MQMO_MATCH_CORREL_ID;
                message.correlationId = HexString.parseHex(queueManage.getMessageId());
            } else {
                putMessageOpt.options = putMessageOpt.options | MQConstants.MQGMO_WAIT;
                message.messageId = MQConstants.MQMI_NONE;
            }

            putMessageOpt.waitInterval = waitInterval * 1000;
            queue.get(message, putMessageOpt);
            String msg = message.readStringOfByteLength(message.getMessageLength());
            queueManage.setMessage(msg);
            queueManage.setMessageId(HexString.hexString(message.messageId));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭队列
                if (queue != null) {
                    queue.close();
                }
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
        return queueManage;
    }


}
