package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice_details")
public class InvoiceDetail extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "booking_detail_id")
    private BookingDetail bookingDetail;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "num_adults")
    private Integer numAdults;

    @Column(name = "num_children")
    private Integer numChildren;

    @Column
    private Date checkin;

    @Column
    private Date checkout;

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

    @Column
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "invoiceDetail")
    private List<HostedAt> hostedAts;

    @JsonIgnore
    @OneToMany(mappedBy = "invoiceDetail")
    private List<UsedService> usedServices;

    @JsonIgnore
    @OneToMany(mappedBy = "invoiceDetail")
    private List<InvoiceDetailHistory> invoiceDetailHistories;

}
