package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_images")
public class RoomImage extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "file_name")
    private String fileName;

}
