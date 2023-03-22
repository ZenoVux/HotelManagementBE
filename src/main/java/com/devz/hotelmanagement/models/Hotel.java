package com.devz.hotelmanagement.models;

import lombok.Data;

import java.util.List;

@Data
public class Hotel {

    private List<StatusCount> statusCounts;
    private List<HotelRoom2> hotelRooms;

}
