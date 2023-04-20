package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class RoomUnbookedResp {

    private String code;
    private String roomType;
    private Double area;
    private Double price;
    private Double newPrice;

}
