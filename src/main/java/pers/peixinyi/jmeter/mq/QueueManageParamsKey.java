package pers.peixinyi.jmeter.mq;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 14:01
 */
public enum QueueManageParamsKey {
    /**
     * 队列管理器属性
     */
    HOST("Host"),
    PORT("Port"),
    CHANNEL("Channel"),
    PUT_QUEUE_NAME("PutQueueName"),
    GET_QUEUE_NAME("GetQueueName"),
    MESSAGE("Message"),
    LABEL("Label"),
    WAIT_TIME("WaitTime"),
    TYPE("Type");//-> 0000 -> 1111,第一个 0 建立连接,第二个放入消息,第三个获取消息,第四个断开连接
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    QueueManageParamsKey(String key) {
        this.key = key;
    }
}
