package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class PeopleInRoomResp {

    private Integer numAdults;

    private Integer numChilds;

    private Double adultSurcharge;

    private Double childSurcharge;

}
