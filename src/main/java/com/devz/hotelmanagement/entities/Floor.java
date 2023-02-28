package com.devz.hotelmanagement.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "floors")
public class Floor extends EntityBase {

    @Column
    private String name;

    @OneToMany(mappedBy = "floor")
    private List<Room> rooms;
}
