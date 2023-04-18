package com.devz.hotelmanagement.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentInvoiceReq {

    private String invoiceCode;
    private String promotionCode;
    private String paymentMethodCode;
    private String note;

}
