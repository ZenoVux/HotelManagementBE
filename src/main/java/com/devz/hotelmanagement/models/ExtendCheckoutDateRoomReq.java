package com.devz.hotelmanagement.models;

import lombok.Data;

import java.util.Date;

@Data
public class ExtendCheckoutDateRoomReq {

    private String code;
    private Date extendDate;
    private String note;

}
