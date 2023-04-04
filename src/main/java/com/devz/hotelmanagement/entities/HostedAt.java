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
@Table(name = "hosted_ats")
public class HostedAt extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetail invoiceDetail;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private Date checkin;

}
