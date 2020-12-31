package com.invicto.collector.option.enums;

public enum FutureExpiryEnum {

    NEAR(1),
    FAR(2),
    FARNEXT(3);

    private final int futureExpiry;

    FutureExpiryEnum(final int futureExpiry) {
        this.futureExpiry = futureExpiry;
    }
}
