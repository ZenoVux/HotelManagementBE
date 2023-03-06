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
@Table(name = "promotions")
public class Promotion extends EntityBase {

    @Column()
    private String name;

    @Column()
    private Integer percent;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column()
    private String description;

    @Column()
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private List<Invoice> invoices;

}
