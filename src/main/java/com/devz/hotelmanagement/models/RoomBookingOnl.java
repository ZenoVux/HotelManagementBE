package com.devz.hotelmanagement.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingOnl {

    @JsonProperty("roomType")
    private String roomType;

    @JsonProperty("numRooms")
    private int numRooms;

}

