package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class InvoiceDetailUpdateReq {

    private Integer invoiceDetailId;
    private Double roomPrice;
    private Double deposit;
    private Double earlyCheckinFee;
    private Double lateCheckoutFee;
    private String note;

}
