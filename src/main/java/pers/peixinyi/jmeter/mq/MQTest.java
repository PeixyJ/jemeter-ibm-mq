package pers.peixinyi.jmeter.mq;

import com.ibm.mq.MQException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 13:36
 * this is unsafe
 */
public class MQTest extends AbstractJavaSamplerClient {

    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument(QueueManageParamsKey.HOST.getKey(), "");
        params.addArgument(QueueManageParamsKey.PORT.getKey(), "");
        params.addArgument(QueueManageParamsKey.CHANNEL.getKey(), "");

        params.addArgument(QueueManageParamsKey.PUT_QUEUE_NAME.getKey(), "");
        params.addArgument(QueueManageParamsKey.GET_QUEUE_NAME.getKey(), "");

        params.addArgument(QueueManageParamsKey.WAIT_TIME.getKey(), "3");

        params.addArgument(QueueManageParamsKey.MESSAGE.getKey(), "");

        params.addArgument(QueueManageParamsKey.TYPE.getKey(), "1001");

        params.addArgument(QueueManageParamsKey.LABEL.getKey(), "");
        return params;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        QueueManage queueManage = new QueueManage();
        QueueMessage queueMessage = new QueueMessage();

        SampleResult sampleResult = new SampleResult();

        sampleResult.setSampleLabel(javaSamplerContext.getParameter(QueueManageParamsKey.LABEL.getKey()));
        //队列管理器
        queueManage.setHost(javaSamplerContext.getParameter(QueueManageParamsKey.HOST.getKey()));
        queueManage.setPort(Integer.parseInt(javaSamplerContext.getParameter(QueueManageParamsKey.PORT.getKey())));
        queueManage.setChannel(javaSamplerContext.getParameter(QueueManageParamsKey.CHANNEL.getKey()));

        String putQueue = javaSamplerContext.getParameter(QueueManageParamsKey.PUT_QUEUE_NAME.getKey());
        String getQueue = javaSamplerContext.getParameter(QueueManageParamsKey.GET_QUEUE_NAME.getKey());
        //消息
        queueMessage.setMessage(javaSamplerContext.getParameter(QueueManageParamsKey.CHANNEL.getKey()));
        String waitTime = javaSamplerContext.getParameter(QueueManageParamsKey.WAIT_TIME.getKey());

        OptionType.type = javaSamplerContext.getParameter(QueueManageParamsKey.TYPE.getKey());

        sampleResult.sampleStart();
        //判断是否需要建立连接
        if (OptionType.checkPermissions(TypeUtil.CONNECT)) {
            System.out.println("建立连接");
            try {
                queueManage.connect();
            } catch (MQException e) {
                sampleResult.setSuccessful(false);
                sampleResult.setResponseMessage(queueMessage.toString() + "\t\t" + e.getMessage());
                e.printStackTrace();
                return sampleResult;
            }
        }
        //判断是否需要放入消息
        if (OptionType.checkPermissions(TypeUtil.PUT)) {
            System.out.println("发起Put");
            queueMessage.setMessageId(queueManage.putMessage(queueMessage, putQueue));
        }
        //判断是否需要获取消息
        if (OptionType.checkPermissions(TypeUtil.GET)) {
            System.out.println("发起Get");
            if (!OptionType.checkPermissions(TypeUtil.GET_BY_ID)) {
                queueMessage.setMessageId(null);
            }
            try {
                QueueMessage result = queueManage.getMessage(queueMessage, getQueue, Integer.parseInt(waitTime));
                System.out.println(result.toString());
            } catch (MQException e) {
                e.printStackTrace();
                sampleResult.setSuccessful(false);
                sampleResult.setResponseMessage(queueMessage.toString() + "\t\t" + e.getMessage());
                return sampleResult;
            }
        }
        //判断是否需要断开连接
        if (OptionType.checkPermissions(TypeUtil.DISCONNECT)) {
            System.out.println("断开连接");
            try {
                queueManage.disconnect();
            } catch (MQException e) {
                sampleResult.setSuccessful(false);
                sampleResult.setResponseMessage(queueMessage.toString() + "\t\t" + e.getMessage());
                e.printStackTrace();
                return sampleResult;
            }
        }
        sampleResult.sampleEnd();
        sampleResult.setSuccessful(true);
        return sampleResult;
    }

}
