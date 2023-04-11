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
@Table(name = "booking_details")
public class BookingDetail extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "checkin_expected")
    private Date checkinExpected;

    @Column(name = "checkout_expected")
    private Date checkoutExpected;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "room_price")
    private Double roomPrice;

    @Column
    private String note;

    @Column
    private Integer status;

    @Column(name = "created_date")
    private Date createdDate;

}
