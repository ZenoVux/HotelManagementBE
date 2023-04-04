package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "used_services")
public class UsedService extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceRoom serviceRoom;

    @ManyToOne
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetail invoiceDetail;

    @Column(name = "stated_time")
    private Date startedTime;

    @Column(name = "ended_time")
    private Date endedTime;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column
    private Integer quantity;

    @Column
    private Double amount;
}
