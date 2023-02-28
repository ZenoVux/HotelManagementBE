package com.devz.hotelmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomBooking {

    @Id
    String name; //Loại phòng + loại giường + số lượnng

    Integer maxAdults;

    Integer maxChild;

    Integer quantity;

    Double price;

}
