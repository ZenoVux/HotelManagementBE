package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class UsedServiceReq {

    private Integer invoiceDetailId;
    private Integer serviceId;
    private Integer quantity;

}
