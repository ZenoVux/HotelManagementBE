package com.devz.hotelmanagement.models;

import com.devz.hotelmanagement.entities.BookingDetail;
import com.devz.hotelmanagement.entities.BookingDetailHistory;
import com.devz.hotelmanagement.entities.BookingHistory;
import com.devz.hotelmanagement.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingHistoryInfo {

    private BookingHistory bookingHistory;

    private List<BookingDetailHistory> bkdhList;

}
