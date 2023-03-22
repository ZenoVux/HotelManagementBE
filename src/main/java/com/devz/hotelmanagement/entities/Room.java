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
    private Double price;

    @Column(name = "max_adults_add")
    private Integer maxAdultsAdd;

    @Column(name = "max_childs_add")
    private Integer maxChildsAdd;

    @Column()
    private Double area;

    @Column(name = "is_smoking")
    private Boolean isSmoking;

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
    private List<SupplyRoom> supplyRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<RoomImage> roomImages;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<BookingDetail> bookingDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<InvoiceDetail> invoiceDetails;

}
