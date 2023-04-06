package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFrontIdCard {
    private String id;
    private String name;
    private String dob;
    private String sex;
    private String address;
    private String home;
}
