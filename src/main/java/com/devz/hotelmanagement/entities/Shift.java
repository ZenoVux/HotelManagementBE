package com.devz.hotelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shifts")
public class Shift extends EntityBase {

    @Column()
    private Date start;

    @Column()
    private Date end;

    @Column() //tiền mặt
    private Double cash;

    @Column(name = "bank_transfer") //tiền chuyển khoản
    private Double bankTransfer;

    @Column(name = "costs_incurred") //chi phí phát sinh
    private Double costsIncurred;

    @Column(name = "handover_money") //tiền bàn giao
    private Double handoverMoney;

    @Column() //tiền bàn giao
    private Double amount;

    @Column()
    private String node;

    @Column()
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "shift")
    private List<Schedule> schedules;

}
