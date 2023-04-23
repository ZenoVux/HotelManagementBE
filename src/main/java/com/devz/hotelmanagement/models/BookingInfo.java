package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingInfo {

    private String code;

    private Long numOfRoom;

    private String customer;

    private String phoneNumber;

    private String note;

    private Date createdDate;

    private Integer status;

    private Double deposit;

    private Integer adults;

    private Integer childs;

}
