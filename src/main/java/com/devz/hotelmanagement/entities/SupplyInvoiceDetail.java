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
@Table(name = "supply_invoice_details")
public class SupplyInvoiceDetail extends EntityBase {

    @Column()
    private Double price;

    @Column()
    private Integer quantity;

    @Column(name = "received_date")
    private Date receivedDate;

    @Column()
    private String note;

    @Column()
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

}
