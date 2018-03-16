package com.opticalix.util;

import java.util.Collection;

public class LogUtil {

    public static void p(Object o) {
        System.out.println(o);
    }

    public static <T> String getCollectionMsg(Collection<T> collection) {
        return getCollectionMsg(collection, new MsgCollector<T>(){
            @Override
            public String getMsg(T o) {
                return o.toString();
            }
        });
    }

    public static <T> String getCollectionMsg(Collection<T> collection, MsgCollector<T> msgCollector) {
        StringBuilder sb = new StringBuilder();
        if (collection == null) {
            sb.append("NULL");
            return sb.toString();
        } else {
            sb.append("[");
            for (T o : collection) {
                if (o == null) {
                    sb.append("null,");
                } else {
                    sb.append(msgCollector.getMsg(o)).append(",");
                }
            }
            sb.append("size=").append(collection.size()).append("]");
        }
        return sb.toString();
    }

    public abstract static class MsgCollector<T> {
        public abstract String getMsg(T element);
    }

}
