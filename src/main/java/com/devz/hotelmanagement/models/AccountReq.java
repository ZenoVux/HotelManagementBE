package com.devz.hotelmanagement.models;

import lombok.Data;

@Data
public class AccountReq {

    private String fullName;

    private String username;

    private String password;

    private String confirmPassword;

    private String phoneNumber;

    private String email;

    private Boolean status;

}
