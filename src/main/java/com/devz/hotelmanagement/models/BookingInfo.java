package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingInfo {

    private String code;

    private Date checkin;

    private Date checkout;

    private Long numOfRoom;

    private BigDecimal adults;

    private BigDecimal childs;

    private String note;

    private Integer status;

}
