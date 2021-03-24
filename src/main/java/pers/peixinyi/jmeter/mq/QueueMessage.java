package pers.peixinyi.jmeter.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 13:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueMessage {
    private String messageId;
    private String message;
    private String errorCode;
}
