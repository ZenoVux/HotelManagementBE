package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomResp {

    private String code;
    private String roomType;
    private String customer;
    private String phoneNumber;
    private String bookingCode;
    private Integer bookingDetailId;
    private Integer invoiceDetailId;
    private Date checkinExpected;
    private Date checkoutExpected;
    private Integer status;

}
