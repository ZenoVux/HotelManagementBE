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
@Table(name = "room_types")
public class RoomType extends EntityBase {

    @Column()
    private String name;

    @Column(name = "adult_surcharge")
    private Integer adultSurcharge;

    @Column(name = "child_surcharge")
    private Integer childSurcharge;

    @Column(name = "cancellation_policy")
    private String cancellationPolicy;

    @Column(name = "other_policy")
    private String otherPolicy;

    @Column()
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

}
