package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends TypeBase{

    @Column()
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "payment")
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "payment")
    private List<SupplyInvoice> supplyInvoices;

}
