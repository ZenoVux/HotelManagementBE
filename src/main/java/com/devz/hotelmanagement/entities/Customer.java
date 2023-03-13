package com.devz.hotelmanagement.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "customer_type_id")
    private CustomerType customerType;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private Boolean gender;

    @Column(name = "people_id")
    private String peopleId;

    @Column
    private String address;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<CustomerImage> customerImages;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<HostedAt> hostedAts;

}
