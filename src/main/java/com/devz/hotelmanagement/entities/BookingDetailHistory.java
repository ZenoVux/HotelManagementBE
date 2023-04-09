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
@Table(name = "booking_detail_histories")
public class BookingDetailHistory extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "booking_history_id")
    private BookingHistory bookingHistory;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private String note;

    @Column(name = "checkin_expected")
    private Date checkinExpected;

    @Column(name = "checkout_expected")
    private Date checkoutExpected;

}
