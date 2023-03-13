package com.devz.hotelmanagement.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {

    private String roomCode;
    private String floorName;
    private String customerName;
    private Date checkinExpected;
    private Date checkoutExpected;
    private Integer roomStatus;
}
