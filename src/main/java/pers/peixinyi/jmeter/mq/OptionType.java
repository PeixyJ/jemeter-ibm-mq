package pers.peixinyi.jmeter.mq;

/**
 * @author: peixinyi
 * @version: V1.0.0.0
 * @date: 2021-03-24 14:42
 */
public class OptionType {

    public static String type = "0000";


    public static Boolean checkPermissions(TypeUtil typeEnum) {
        char[] typeList = type.toCharArray();
        return Integer.parseInt(String.valueOf(typeList[typeEnum.getIndex()])) >= typeEnum.getValue();
    }

}
