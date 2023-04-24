package com.devz.hotelmanagement.models;


import lombok.Data;

import java.util.List;

@Data
public class CheckinRoomReq {

    private String code;
    private Double earlyCheckinFee;
    private List<CustomerCheckinReq> customers;
    private List<ServiceCheckinReq> services;

}
