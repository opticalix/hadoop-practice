package com.opticalix;

public enum LogPart {
    IP(1),
    TIME(2),
    HOST(3),
    STATUS(4);

    private int index;

    LogPart(int i) {
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public static String getNameByIndex(int i) {
        if (i == IP.getIndex()) {
            return IP.name();
        } else if (i == TIME.getIndex()) {
            return TIME.name();
        } else if (i == HOST.getIndex()) {
            return HOST.name();
        } else if (i == STATUS.getIndex()) {
            return STATUS.name();
        }
        return "";
    }
}
