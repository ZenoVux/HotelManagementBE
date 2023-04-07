package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Column(name = "stated_date")
    private Date startedDate;

    @Column(name = "ended_date")
    private Date endedDate;

    @Column()
    private String description;

    @Column()
    private Boolean type;

    @Column()
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private List<Invoice> invoices;
    
    public void updateStatus() {
        Date now = new Date();
        if (now.before(startedDate)) {
            status = false;
        } else if (endedDate != null && endedDate.before(now)) {
            status = false;
        } else {
            status = true;
        }
    }

}
