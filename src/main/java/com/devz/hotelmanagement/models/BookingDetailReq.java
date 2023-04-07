package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailReq {

    private String bookingCode;

    private Date checkinExpected;

    private Date checkoutExpected;

    private String note;

    private Integer numAdults;

    private Integer numChilds;

    private Room[] rooms;

}
