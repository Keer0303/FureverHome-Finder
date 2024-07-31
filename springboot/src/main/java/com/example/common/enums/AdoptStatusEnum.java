package com.example.common.enums;

public enum AdoptStatusEnum {
    ADOPT_YES("Adopted"),
    ADOPT_NO("Available"),
//    ADOPT_PENDING("In Process"),
    ;
    public String status;
    AdoptStatusEnum(String status) {
        this.status = status;
    }
}
