# jmeter-mq
一个可自定义配置参数的 Jmeter 压力测试 的自定义Jar包

参数
* HOST - 队列管理器IP
* PORT - 队列管理器Port
* CHANNEL - 队列管理器通道
* PUT_QUEUE_NAME - 放入的队列管理
* GET_QUEUE_NAME - 获取的队列管理
* WAIT_TIME - 等待时间
* MESSAGE - 放入的MESSAGE消息
* TYPE - 要执行的操作 0000 -> 1111,第一个 0 建立连接,第二个放入消息,第三个获取消息,第四个断开连接
* LABEL - LABEL