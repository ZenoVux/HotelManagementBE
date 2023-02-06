package com.devz.hotelmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback extends EntityBase {

    @Column()
    private String name;

    @Column()
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column()
    private Integer rating;

    @Column()
    private String comment;

}
