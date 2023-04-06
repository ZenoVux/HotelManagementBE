package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiBackIdCardResponse {

    private int errorCode;

    private String errorMessage;

    private List<DataBackIdCard> data;

}
