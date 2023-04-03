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
@Table(name = "invoices")
public class Invoice extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column()
    private Double total;

    @Column(name = "total_deposit")
    private Double totalDeposit;

    @Column(name = "discount_money")
    private Double discountMoney;

    @Column(name = "total_payment")
    private Double totalPayment;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "paid_date")
    private Date paidDate;

    @Column()
    private String note;

    @Column()
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceDetail> invoiceDetails;

}
