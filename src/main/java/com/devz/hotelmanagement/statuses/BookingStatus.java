package com.devz.hotelmanagement.statuses;

public enum BookingStatus {

    CANCELLED(0),
    PENDING(1),
    CONFIRMED(2),
    COMPLETED(3);

    private int code;

    BookingStatus(int code) { this.code = code; }

    public int getCode() { return code; }

}
