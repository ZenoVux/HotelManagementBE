package com.devz.hotelmanagement.statuses;

public enum InvoiceStatus {

    PENDING(1),
    CONFIRMED(2),
    COMPLETED(3);

    private int code;

    InvoiceStatus(int code) { this.code = code; }

    public int getCode() { return code; }
}
