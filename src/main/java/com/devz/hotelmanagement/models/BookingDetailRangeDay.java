package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailRangeDay {

    private Date checkinDate;

    private Date checkoutDate;

    private Integer numAdults;

    private Integer numChildren;

    private Room[] rooms;

}
