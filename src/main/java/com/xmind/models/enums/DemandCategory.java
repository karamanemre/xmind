package com.xmind.models.enums;

public enum DemandCategory {

    TECHNICAL_SUPPORT(0),
    OTHER(1);

    private final int code;

    DemandCategory(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
