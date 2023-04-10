package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Room;
import com.devz.hotelmanagement.entities.RoomType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBooking {

    RoomType roomType;

    String name;

    String image;

    Long quantity;

    Long maxAdults;

    Long maxChilds;

    List<Room> listRooms;

    Double minPrice;

    Double maxPrice;

}
