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
@Table(name = "suppliers")
public class Supplier extends EntityBase{

    @Column()
    private String name;

    @Column()
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column()
    private String email;

    @Column()
    private String image;

    @Column()
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<SupplyInvoice> supplyInvoices;

}
