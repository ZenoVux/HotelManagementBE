package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private BookingDetailService bookingDetailService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UsedServiceService usedServiceService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private HostedAtService hostedAtService;

    @Autowired
    private RoomService roomService;

    @Override
    public Hotel getHotel() {
        int status = 0, status1 = 0, status2 = 0, status3 = 0, status4 = 0, status5 = 0;
        Hotel hotel = new Hotel();
        List<Room> rooms = roomService.findAll();
        List<StatusCount> statusCounts = new ArrayList<>();
        List<HotelRoom2> hotelRooms = new ArrayList<>();
        for (Room room : rooms) {
            HotelRoom2 hotelRoom = new HotelRoom2();
            hotelRoom.setCode(room.getCode());
            if (room.getStatus() == 0) {
                BookingDetail bookingDetail = bookingDetailService.findByCheckinRoomCode(room.getCode());
                if (bookingDetail != null) {
                    hotelRoom.setBookingDetailId(bookingDetail.getId());
                    hotelRoom.setCheckinExpected(bookingDetail.getCheckinExpected());
                    hotelRoom.setCheckoutExpected(bookingDetail.getCheckoutExpected());
                    // chờ checkin
                    status4++;
                    hotelRoom.setStatus(4);
                } else {
                    status++;
                    hotelRoom.setStatus(0);
                }
            } else if (room.getStatus() == 2) {
                InvoiceDetail invoiceDetail = invoiceDetailService.findByCheckoutRoomCode(room.getCode());
                if (invoiceDetail != null) {
                    hotelRoom.setInvoiceDetailId(invoiceDetail.getId());
                }
                BookingDetail bookingDetail = bookingDetailService.findByCheckedinRoomCode(room.getCode());
                if (bookingDetail != null) {
                    hotelRoom.setBookingDetailId(bookingDetail.getId());
                    Date checkin = bookingDetail.getCheckinExpected();
                    Date checkout = bookingDetail.getCheckoutExpected();
                    hotelRoom.setCheckinExpected(checkin);
                    hotelRoom.setCheckoutExpected(checkout);
                    if (checkin.compareTo(new Date()) <= 0 && checkout.compareTo(new Date()) >= 0) {
                        // đang ở
                        status2++;
                        hotelRoom.setStatus(2);
                    } else {
                        // quá hạn
                        status5++;
                        hotelRoom.setStatus(5);
                    }
                }
            } else {
                if (room.getStatus() == 1) {
                    status1++;
                } else {
                    status3++;
                }
                hotelRoom.setStatus(room.getStatus());
            }
            hotelRooms.add(hotelRoom);
        }
        statusCounts.add(new StatusCount(0, (long) status));
        statusCounts.add(new StatusCount(1, (long) status1));
        statusCounts.add(new StatusCount(2, (long) status2));
        statusCounts.add(new StatusCount(3, (long) status3));
        statusCounts.add(new StatusCount(4, (long) status4));
        statusCounts.add(new StatusCount(5, (long) status5));
        hotel.setStatusCounts(statusCounts);
        hotel.setHotelRooms(hotelRooms);
        return hotel;
    }

    @Override
    public InvoiceDetail checkin(String code) {
        BookingDetail bookingDetail = bookingDetailService.findByCheckinRoomCode(code);
        if (bookingDetail == null) {
            throw new RuntimeException("ko tồn tại BookingDetail của room " + code);
        }
        List<HostedAt> hostedAts = hostedAtService.findByBookingDetailId(bookingDetail.getId());
        if (hostedAts.size() <= 0) {
            throw new RuntimeException("phòng không có người " + code);
        }
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setRoom(bookingDetail.getRoom());
        invoiceDetail.setNumAdults(bookingDetail.getNumAdults());
        invoiceDetail.setNumChildren(bookingDetail.getNumChildren());
        invoiceDetail.setBookingDetail(bookingDetail);
        invoiceDetail.setCheckin(new Date());
        invoiceDetail.setStatus(0); // trạng thái chưa checkout
        if (invoiceDetailService.create(invoiceDetail) == null) {
            throw new RuntimeException("check in thất bại " + code);
        }
        // update InvoiceDetail for all UsedService in BookingDetail
        List<UsedService> usedServices = usedServiceService.findAllByBookingDetailCode(bookingDetail.getCode());
        usedServices.forEach(usedService -> usedService.setInvoiceDetail(invoiceDetail));
        usedServiceService.updateAll(usedServices);

        // update InvoiceDetail for all HostedAt in BookingDetail
        hostedAts.forEach(hostedAt -> hostedAt.setInvoiceDetail(invoiceDetail));
        hostedAtService.updateAll(hostedAts);

        // update status for BookingDetail
        bookingDetail.setInvoiceDetail(invoiceDetail);
        bookingDetail.setStatus(1); // trạng thái đã checkin
        bookingDetailService.update(bookingDetail);

        // update status for Room
        Room room = bookingDetail.getRoom();
        room.setStatus(2); // trạng thái đang ở
        roomService.update(room);
        return invoiceDetail;
    }

    @Override
    public void cancel(String code, String note) {
        Room room = roomService.findByCode(code);
        BookingDetail bookingDetail = null;
        InvoiceDetail invoiceDetail = null;
        if (room.getStatus() == 0) {
            bookingDetail = bookingDetailService.findByCheckinRoomCode(room.getCode());
            if (bookingDetail != null) {
                // update status for BookingDetail
                bookingDetail.setNote(note);
                bookingDetail.setStatus(2); // trạng thái huỷ
                bookingDetailService.update(bookingDetail);

                // trạng thái phòng trống
                room.setStatus(0);
                roomService.update(room);
            } else {
                throw new RuntimeException("Huỷ phòng thất bại");
            }
        } else if (room.getStatus() == 2) {
            bookingDetail = bookingDetailService.findByCheckedinRoomCode(room.getCode());
            invoiceDetail = invoiceDetailService.findByCheckoutRoomCode(room.getCode());
            if (bookingDetail != null && invoiceDetail != null) {
                // update status for BookingDetail
                bookingDetail.setNote(note);
                bookingDetail.setStatus(2); // trạng thái huỷ
                bookingDetailService.update(bookingDetail);

                // update status for invoiceDetail
                invoiceDetail.setNote(note);
                invoiceDetail.setStatus(2); // trạng thái huỷ
                invoiceDetailService.update(invoiceDetail);

                // trạng thái phòng trống
                room.setStatus(0);
                roomService.update(room);
            } else {
                throw new RuntimeException("Huỷ phòng thất bại");
            }
        } else {
            throw new RuntimeException("Huỷ phòng thất bại");
        }
    }

    @Override
    public Invoice checkout(String[] codes) {
        Double total = 0.0;
        Invoice invoice = new Invoice();
        invoice.setCreatedDate(new Date());
        if (invoiceService.create(invoice) == null) {
            // lỗi tạo invoice
            throw new RuntimeException("lỗi tạo invoice");
        }
        for (String code : codes) {
            InvoiceDetail invoiceDetail = invoiceDetailService.findByCheckoutRoomCode(code);
            if (invoiceDetail == null) {
                continue;
            }
            BookingDetail bookingDetail = bookingDetailService.findByInvoiceDetailId(invoiceDetail.getId());
            Room room = invoiceDetail.getRoom();

            // tính tổng tiền dịch vụ
            List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
            Double totalServiceFee = usedServices.stream()
                    .map(usedService -> usedService.getServiceRoom().getPrice() * usedService.getQuantity())
                    .reduce(0.0, Double::sum);

            invoiceDetail.setTotalServiceFee(totalServiceFee);

            // tính tổng tiền phòng
            Date checkin = bookingDetail.getCheckinExpected();
            Date now = new Date();

            Double totalRoomFee = room.getPrice() * (now.getDate() - checkin.getDate());

            invoiceDetail.setTotalRoomFee(totalRoomFee);
            //
            invoiceDetail.setTotal(totalRoomFee + totalServiceFee);
            invoiceDetail.setEarlyCheckinFee(0.0);
            invoiceDetail.setLateCheckoutFee(0.0);
            invoiceDetail.setCheckout(now);
            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setStatus(1); // trạng thái đã checkout

            if (invoiceDetailService.update(invoiceDetail) != null) {
                total += totalServiceFee;
                total += totalRoomFee;
                // update status for Room
                room.setStatus(0); // trạng thái trống
                roomService.update(room);
            }
        }
        invoice.setTotal(total);
        invoice.setStatus(0);
        return invoiceService.update(invoice);
    }

}
