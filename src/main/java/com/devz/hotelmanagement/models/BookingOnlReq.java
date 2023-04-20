package com.devz.hotelmanagement.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOnlReq {

    @JsonProperty("numAdults")
    private int numAdults;

    @JsonProperty("numChildren")
    private int numChildren;

    @JsonProperty("numRoomsBooking")
    private RoomBookingOnl[] numRoomsBooking;

    @JsonProperty("checkinDate")
    private String checkinDate;

    @JsonProperty("checkoutDate")
    private String checkoutDate;

    @JsonProperty("roomType")
    private String roomType;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("note")
    private String note;

}

