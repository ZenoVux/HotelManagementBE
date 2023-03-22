package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.InvoiceDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom2 {

    private String code;
    private Integer bookingDetailId;
    private Integer invoiceDetailId;
    private Date checkinExpected;
    private Date checkoutExpected;
    private Integer status;
}
