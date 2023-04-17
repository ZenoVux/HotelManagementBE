package com.devz.hotelmanagement.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer number;

    @Column
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "floor")
    private List<Room> rooms;
}
