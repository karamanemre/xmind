package com.xmind.models.enums;

public enum DemandStatus {
    OPEN(0),
    RESPONDED(1),
    CLOSED(2);

    private final int code;

    DemandStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
