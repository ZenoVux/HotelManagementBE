package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmReq {

    private String bookingCode;

    private String note;

    private Room[] rooms;

}
