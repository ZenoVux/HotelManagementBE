package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {

    private String code;
    private String customer;
    private Integer bookingDetailId;
    private Integer invoiceDetailId;
    private Date checkinExpected;
    private Date checkoutExpected;
    private Integer status;

}
