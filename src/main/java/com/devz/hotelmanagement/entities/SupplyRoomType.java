package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply_room_types")
public class SupplyRoomType extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @Column()
    private Integer quantity;

}
