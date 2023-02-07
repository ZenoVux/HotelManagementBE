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
@Table(name = "payment_methods")
public class PaymentMethod extends TypeBase {

    @Column()
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentMethod")
    private List<Invoice> invoices;

}
