package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.PaymentMethod;
import com.devz.hotelmanagement.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingReq {

    private MultipartFile frontIdCard;

    private MultipartFile backIdCard;

    private Customer customer;

    private BookingDetailRangeDay[] bookingDetailRangeDay;

    private String paymentCode;

    private String note;

}
