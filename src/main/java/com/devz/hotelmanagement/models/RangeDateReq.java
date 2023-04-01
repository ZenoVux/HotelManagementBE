package com.devz.hotelmanagement.models;

import lombok.Data;

import java.util.Date;

@Data
public class RangeDateReq {

    private Date startDate;
    private Date endDate;

}
