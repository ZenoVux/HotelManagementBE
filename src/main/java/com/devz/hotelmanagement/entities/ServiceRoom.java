package com.devz.hotelmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class ServiceRoom extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @Column
    private String name;

    @Column
    private Double price;

    @Column()
    private String description;

    @Column()
    private Boolean status;
}
