package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailInfo {

    private String code;

    private Customer customer;

    private List<Room> roomList;

}
