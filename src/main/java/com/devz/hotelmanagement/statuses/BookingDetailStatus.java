package com.devz.hotelmanagement.statuses;

public enum BookingDetailStatus {

    CANCELLED(0),
    PENDING(1),
    CONFIRMED(2),
    COMPLETED(3);

    private int code;

    BookingDetailStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
