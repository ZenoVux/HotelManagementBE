package com.devz.hotelmanagement.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
public class BookingRoomReq {

    private String invoiceCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkoutExpected;

    private String[] roomCodes;

}
