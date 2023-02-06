package com.devz.hotelmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "used_service")
public class UsedService extends EntityBase {

    @ManyToOne
    @Column(name = "service_id")
    private Service service;

    @ManyToOne
    @Column(name = "booking_detail")
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
