package com.devz.hotelmanagement.services;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.models.*;

import java.util.Date;
import java.util.List;

public interface HotelRoomService {

    HotelResp getHotel();

    HotelRoomResp getHotelRoom(String code);

    void checkin(CheckinRoomReq checkinRoomReq);

    void cancel(String code, String note);

    void checkout(CheckoutRoomReq checkoutRoomReq);

    void extendCheckoutDate(String code, Date extendDate, String note);

    void checkExtendCheckoutDate(String code, Date checkoutDate);

    void ready(String code);

    void change(String fromRoomCode, String toRoomCode, Date checkoutDate, String note);

    void payment(String invoiceCode, String promotionCode, String paymentMethodCode, String note);

    void confirmPayment(String invoiceCode);

    void updateInvoiceDetail(InvoiceDetailUpdateReq invoiceDetailUpdateReq);

    Invoice splitInvoice(InvoiceSplitReq invoiceSplitReq);

    PeopleInRoomResp peopleInRoom(Integer invoiceDetailId);

    List<RoomUnbookedResp> findAllRoomUnbooked(Date checkinDate, Date checkoutDate);

}
