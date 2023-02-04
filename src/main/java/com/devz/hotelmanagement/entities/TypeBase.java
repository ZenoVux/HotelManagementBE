package com.devz.hotelmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class TypeBase extends EntityBase {

    @Column()
    private String name;

    @Column()
    private String description;

}
