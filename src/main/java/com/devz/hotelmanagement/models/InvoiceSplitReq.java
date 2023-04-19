package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class InvoiceSplitReq {

    private String invoiceCode;
    private String[] roomCodes;

}
