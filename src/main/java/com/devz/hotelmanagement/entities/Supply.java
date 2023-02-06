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
@Table(name = "supplies")
public class Supply extends EntityBase {

    @Column()
    private String name;

    @Column()
    private Integer quantity;

    @Column()
    private String image;

    @Column()
    private String description;

    @Column()
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "supply_type_id")
    private SupplyType supplyType;

    @JsonIgnore
    @OneToMany(mappedBy = "supply")
    private List<RoomDetail> roomDetails;

}
