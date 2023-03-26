package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class ServiceCheckinReq {

    private Integer serviceId;
    private Integer quantity;

}
