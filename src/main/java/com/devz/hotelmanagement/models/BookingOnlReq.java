package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOnlReq {

    private Customer customer;

    private Date checkinExpected;

    private Date checkoutExpected;

    private String roomType;

    private Integer numRooms;

    private Integer numAdults;

    private Integer numChildren;

    private String note;

}
