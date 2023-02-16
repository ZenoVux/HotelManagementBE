package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "num_adults")
    private Integer numAdults;

    @Column(name = "num_children")
    private Integer numChildren;

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
