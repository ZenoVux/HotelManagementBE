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
@Table(name = "supply_invoices")
public class SupplyInvoice extends EntityBase {

    @Column()
    private Double amount;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "due_date") //Ngày dự kiến giao
    private Date dueDate;

    @Column()
    private String note;

    @Column()
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "invoice_type_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

}
