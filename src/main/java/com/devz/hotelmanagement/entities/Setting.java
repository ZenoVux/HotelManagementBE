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
@Table(name = "settings")
public class Setting extends EntityBase {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkin_time")
    private Date checkinTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkout_time")
    private Date checkoutTime;

}
