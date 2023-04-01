package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class ChangeRoomReq {

    private String fromRoomCode;
    private String toRoomCode;
    private String note;

}
