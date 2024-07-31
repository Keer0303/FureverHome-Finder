package com.example.common.enums;

public enum AnimalStatusEnum {
    ADOPT_YES("Adopted"),
    ADOPT_NO("Available"),
    ADOPT_GIVEUP("GiveUp Adoption"),
    ADOPT_PENDING("Pending"),
    ;
    public String status;
    AnimalStatusEnum(String status) {
        this.status = status;
    }
}
