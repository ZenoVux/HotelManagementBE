package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice_detail_histories")
public class InvoiceDetailHistory extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetail invoiceDetail;

    @Column(name = "checkin_expected")
    private Date checkinExpected;

    @Column(name = "checkout_expected")
    private Date checkoutExpected;

    @Column(name = "room_price")
    private Double roomPrice;

    @Column
    private Double deposit;

    @Column(name = "orther_surcharge")
    private Double ortherSurcharge;

    @Column(name = "late_checkout_fee")
    private Double lateCheckoutFee;

    @Column(name = "early_checkin_fee")
    private Double earlyCheckinFee;

    @Column
    private String note;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "update_date")
    private Date updateDate;

}
