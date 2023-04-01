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

    @Column(name = "num_adults")
    private Integer numAdults;

    @Column(name = "num_children")
    private Integer numChildren;

    @Column(name = "checkin_expected")
    private Date checkinExpected;

    @Column(name = "checkout_expected")
    private Date checkoutExpected;

    @Column
    private Double total;

    @Column(name = "total_room_fee")
    private Double totalRoomFee;

    @Column(name = "total_service_fee")
    private Double totalServiceFee;

    @Column(name = "late_checkout_fee")
    private Double lateCheckoutFee;

    @Column(name = "early_checkin_fee")
    private Double earlyCheckinFee;

    @Column
    private String note;

    @Column(name = "update_date")
    private Date updateDate;

}
