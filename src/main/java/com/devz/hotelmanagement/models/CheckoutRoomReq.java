package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class CheckoutRoomReq {

    private String code;
    private Double lateCheckoutFee;

}
