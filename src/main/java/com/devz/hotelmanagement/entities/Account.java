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
@Table(name = "accounts")
public class Account extends EntityBase {

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    @Column()
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column()
    private String address;

    @Column()
    private String email;

    @Column()
    private Boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Invoice> invoices;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Schedule> schedules;

}
