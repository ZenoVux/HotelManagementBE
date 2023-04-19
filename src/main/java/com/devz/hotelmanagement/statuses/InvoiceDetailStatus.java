package com.devz.hotelmanagement.statuses;

public enum InvoiceDetailStatus {

    PENDING(1),
    COMPLETED(2);

    private int code;

    InvoiceDetailStatus(int code) { this.code = code; }

    public int getCode() { return code; }
}
