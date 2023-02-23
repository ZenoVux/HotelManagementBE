package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bed_rooms")
public class BedRoom extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "bed_type_id")
    private BedType bedType;

    @Column(name = "quantity_bed")
    private Integer quantityBed;
}
