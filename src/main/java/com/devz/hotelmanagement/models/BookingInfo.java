package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Account;
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

    private Long numOfRoom;

    private Integer adults;

    private Integer childs;

    private String note;

    private Date createdDate;

    private Integer status;

    private Double deposit;

}
