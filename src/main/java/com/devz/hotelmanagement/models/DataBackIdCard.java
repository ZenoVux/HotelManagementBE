package com.devz.hotelmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBackIdCard {

    private String features;

    private String featuresProb;

    private String issueDate;

    private String issueDateProb;

    private List<String> mrz;

    private String mrzProb;

    private String overallScore;

    private String QRcode;

    private String issueLoc;

    private String issueLocProb;

    private String typeNew;

    private String type;

}
