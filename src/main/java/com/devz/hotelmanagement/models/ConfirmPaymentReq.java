package com.devz.hotelmanagement.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConfirmPaymentReq {

    private String invoiceCode;

}
