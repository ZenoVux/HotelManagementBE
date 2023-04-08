package com.devz.hotelmanagement.models;

import lombok.Data;

import java.util.Date;

@Data
public class ChangeRoomReq {

    private String fromRoomCode;
    private String toRoomCode;
    private Date checkoutDate;
    private String note;

}
