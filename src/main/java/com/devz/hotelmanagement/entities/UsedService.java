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
@Table(name = "used_services")
public class UsedService extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "booking_detail")
    private BookingDetail bookingDetail;

    @Column(name = "stated_time")
    private Date startedTime;

    @Column(name = "ended_time")
    private Date endedTime;

    @Column
    private Integer quantity;

    @Column
    private Double amount;
}
