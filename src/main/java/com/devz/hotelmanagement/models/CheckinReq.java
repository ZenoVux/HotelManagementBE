package com.devz.hotelmanagement.models;


import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.entities.UsedService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinReq {

    private String roomCode;
    private Integer bookingDetailId;
    private List<HostedAt> hostedAts;
    private List<UsedService> usedServices;

}
