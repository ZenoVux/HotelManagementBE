package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResp {

    private String code;
    private String bookingCode;
    private String customer;
    private String phoneNumber;
    private String staff;
    private Double total;
    private Integer status;
    private Date createdDate;

}
