package com.vanguard8.util;

public class SequenceUtil {

    //生成一个整数的下一个值
    public static Integer NextSequence(Integer value) {
        Integer result = 1;
        if (value != null) {
            result = value + 1;
        }
        return result;
    }

    //生成一个数值格式字符串(例如 000001 )的下一个值
    //传入的值不允许为空与null，长度不能大于9位，否则返回null
    //prefix前缀，value除去prefix的目前值，length指value部分的长度
    //prefix两种情况，一是“”，二是三位字符串比如001
    public static String NextSequence(String prefix, String value, Integer length) {
        String result = null;
        Integer len = length;
        if (value != null && value != "") {
            len = value.length();
        } else {
            value = prefix + "0";
        }
        if (len <= 9) {
            int iTmp = (int) Math.pow(10, len);
            try {
                result = String.valueOf(iTmp + 1 + Integer.valueOf(value)).substring(1);
            } catch (NumberFormatException e) {
            }
        }
        return result;
    }
}
