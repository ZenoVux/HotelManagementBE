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

    @Column
    private String note;

    @Column(name = "update_date")
    private Date updateDate;

}
