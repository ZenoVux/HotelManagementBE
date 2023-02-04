package com.devz.hotelmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_details")
public class RoomDetail extends EntityBase{

    @ManyToOne
    @JoinColumn(name = "room__id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "supply__id")
    private Supply supply;

}
