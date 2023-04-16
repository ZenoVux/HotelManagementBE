package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailEdit {

    private String bkdCode;
    private String roomCode;

    private String type;

    private Double room_price;

    private Date checkin_expected;

    private Date checkout_expected;

    private String note;

}
