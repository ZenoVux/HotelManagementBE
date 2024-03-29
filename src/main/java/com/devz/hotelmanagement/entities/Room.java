package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends EntityBase {

    @Column()
    private String description;

    @Column()
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<BedRoom> bedRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<BookingDetail> bookingDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<InvoiceDetail> invoiceDetails;

}
