package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "bed_types")
public class BedType extends TypeBase {

    @JsonIgnore
    @OneToMany(mappedBy = "bedType")
    private List<BedRoom> bedRooms;

}
