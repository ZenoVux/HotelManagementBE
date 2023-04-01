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
@Table(name = "surcharges")
public class Surcharge extends EntityBase {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_start")
    private Date timeStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_end")
    private Date timeEnd;

    @Column
    private Double percent;

    @Column
    private Boolean type;

}
