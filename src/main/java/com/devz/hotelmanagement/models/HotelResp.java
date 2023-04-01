package com.devz.hotelmanagement.models;

import lombok.Data;

import java.util.List;

@Data
public class HotelResp {

    private List<StatusCountResp> statusCounts;
    private List<HotelRoomResp> hotelRooms;

}
