package pers.peixinyi.jmeter.mq;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 13:35
 */
public class JmeterMqApplicationRun {
    public static void main(String[] args) {
        Arguments params = new Arguments();
        params.addArgument(QueueManageParamsKey.HOST.getKey(), "192.168.10.155");
        params.addArgument(QueueManageParamsKey.PORT.getKey(), "5001");
        params.addArgument(QueueManageParamsKey.CHANNEL.getKey(), "C.S01.C");

        params.addArgument(QueueManageParamsKey.PUT_QUEUE_NAME.getKey(), "EQ.S01.DC10001.PUT");
        params.addArgument(QueueManageParamsKey.GET_QUEUE_NAME.getKey(), "EQ.S01.DC10001.GET");

        params.addArgument(QueueManageParamsKey.WAIT_TIME.getKey(), "1");

        params.addArgument(QueueManageParamsKey.MESSAGE.getKey(), "<a>B</a>");

        params.addArgument(QueueManageParamsKey.TYPE.getKey(), "1121");

        params.addArgument(QueueManageParamsKey.LABEL.getKey(), "S01-TEST");

        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        MQTest mqTest = new MQTest();
        mqTest.runTest(arg0);
    }
}
