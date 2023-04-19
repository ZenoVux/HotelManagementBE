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

    @Column()
    private Double area;

    @Column()
    private Double price;

    @Column(name = "max_adults_add")
    private Integer maxAdultsAdd;

    @Column(name = "max_childs_add")
    private Integer maxChildsAdd;

    @Column(name = "num_adults")
    private Integer numAdults;

    @Column(name = "num_childs")
    private Integer numChilds;

    @Column(name = "is_smoking")
    private Boolean isSmoking;

    @Column(name = "adult_surcharge")
    private Double adultSurcharge;

    @Column(name = "child_surcharge")
    private Double childSurcharge;

    @Column(name = "cancellation_policy")
    private String cancellationPolicy;

    @Column(name = "other_policy")
    private String otherPolicy;

    @Column()
    private String description;

    @Column()
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private List<RoomTypeImage> roomTypeImages;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private List<SupplyRoomType> supplyRoomTypes;

}
