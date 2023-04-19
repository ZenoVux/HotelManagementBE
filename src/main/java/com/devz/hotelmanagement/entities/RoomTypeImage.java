package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_type_images")
public class RoomTypeImage extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Column(name = "file_name")
    private String fileName;

}
