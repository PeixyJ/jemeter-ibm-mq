package pers.peixinyi.jmeter.mq;

import lombok.Data;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 14:31
 */

public enum TypeUtil {
    /**
     * 建立连接
     */
    CONNECT(0, 1, "CONNECT"),
    PUT(1, 1, "PUT"),
    GET(2, 1, "GET"),
    GET_BY_ID(2, 2, "GET_BY_ID"),
    DISCONNECT(3, 1, "DISCONNECT");

    private int index;
    private int value;
    private String remake;

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public String getRemake() {
        return remake;
    }

    TypeUtil(int index, int value, String remake) {
        this.index = index;
        this.value = value;
        this.remake = remake;
    }
}
