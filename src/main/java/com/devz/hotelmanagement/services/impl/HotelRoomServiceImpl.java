package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private SettingService settingService;

    @Override
    public Hotel getHotel() {
        int status = 0, status1 = 0, status2 = 0, status3 = 0, status4 = 0, status5 = 0;
        Hotel hotel = new Hotel();
        Setting setting = settingService.getSetting();
        List<Room> rooms = roomService.findAll();
        List<StatusCount> statusCounts = new ArrayList<>();
        List<HotelRoom> hotelRooms = new ArrayList<>();
        for (Room room : rooms) {
            HotelRoom hotelRoom = new HotelRoom();
            hotelRoom.setCode(room.getCode());
            if (room.getStatus() == 0) {
                BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(room.getCode());
                if (bookingDetail != null) {
                    hotelRoom.setBookingDetailId(bookingDetail.getId());
                    hotelRoom.setCustomer(bookingDetail.getBooking().getCustomer().getFullName());
                    Date checkin = bookingDetail.getBooking().getCheckinExpected();
                    Date checkout = bookingDetail.getBooking().getCheckoutExpected();
                    hotelRoom.setCheckinExpected(checkin);
                    hotelRoom.setCheckoutExpected(checkout);
                    // chờ checkin
                    status4++;
                    hotelRoom.setStatus(4);
                } else {
                    status++;
                    hotelRoom.setStatus(0);
                }
            } else if (room.getStatus() == 2) {
                InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(room.getCode());
                if (invoiceDetail != null) {
                    hotelRoom.setInvoiceDetailId(invoiceDetail.getId());
                    hotelRoom.setCustomer(invoiceDetail.getInvoice().getBooking().getCustomer().getFullName());
                    Date checkin = invoiceDetail.getInvoice().getBooking().getCheckinExpected();
                    Date checkout = invoiceDetail.getInvoice().getBooking().getCheckoutExpected();
                    hotelRoom.setCheckinExpected(checkin);
                    hotelRoom.setCheckoutExpected(checkout);
                    Date now = new Date();
                    Date checkoutTime = setting.getCheckoutTime();
                    if (checkout.getDate() < now.getDate() || (checkout.getDate() < now.getDate() && checkoutTime.getHours() >= now.getHours())) {
                        // quá hạn
                        status5++;
                        hotelRoom.setStatus(5);
                    } else {
                        // đang ở
                        status2++;
                        hotelRoom.setStatus(2);
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
    public InvoiceDetail checkin(CheckinRoomReq checkinRoomReq) {
        BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(checkinRoomReq.getCode());
        if (bookingDetail == null) {
            throw new RuntimeException("Không tìm thấy BookingDetail của room " + checkinRoomReq.getCode());
        }
        List<CustomerCheckinReq> customerCheckinReqs = checkinRoomReq.getCustomers();
        List<ServiceCheckinReq> serviceCheckinReqs = checkinRoomReq.getServices();

        if (customerCheckinReqs.size() <= 0) {
            throw new RuntimeException("Phòng không có người ở " + checkinRoomReq.getCode());
        }
        Invoice invoice = invoiceService.findFirstByBookingId(bookingDetail.getBooking().getId());
        if (invoice == null) {
            invoice = new Invoice();
            invoice.setBooking(bookingDetail.getBooking());
            invoice.setCreatedDate(new Date());
            invoice.setTotal(0.0);
            invoice.setStatus(1);

            Account account = new Account();
            account.setId(5);
            invoice.setAccount(account);
            if (invoiceService.create(invoice) == null) {
                // lỗi tạo invoice
                throw new RuntimeException("lỗi tạo invoice");
            }
        }
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoice(invoice);
        invoiceDetail.setRoom(bookingDetail.getRoom());
//        invoiceDetail.setNumAdults(bookingDetail.getNumAdults());
//        invoiceDetail.setNumChildren(bookingDetail.getNumChildren());
        invoiceDetail.setTotalRoomFee(0.0);
        invoiceDetail.setTotalServiceFee(0.0);
        invoiceDetail.setEarlyCheckinFee(0.0);
        invoiceDetail.setLateCheckoutFee(0.0);
        invoiceDetail.setTotal(0.0);
        invoiceDetail.setCheckin(new Date());
        invoiceDetail.setStatus(1); // trạng thái đang sử dụng
        if (invoiceDetailService.create(invoiceDetail) == null) {
            throw new RuntimeException("check in thất bại " + checkinRoomReq.getCode());
        }
        // update InvoiceDetail for all UsedService in BookingDetail
        List<UsedService> usedServices = new ArrayList<>();
        serviceCheckinReqs.forEach(serviceCheckinReq -> {
            UsedService usedService = new UsedService();

            ServiceRoom serviceRoom = serviceRoomService.findById(serviceCheckinReq.getServiceId());
            if (serviceRoom != null) {
                serviceRoom.setId(serviceCheckinReq.getServiceId());
                usedService.setServiceRoom(serviceRoom);

                usedService.setQuantity(serviceCheckinReq.getQuantity());
                usedService.setInvoiceDetail(invoiceDetail);
                usedServices.add(usedService);
            }
        });
        usedServiceService.updateAll(usedServices);

        // update InvoiceDetail for all HostedAt in BookingDetail
        List<HostedAt> hostedAts = new ArrayList<>();
        customerCheckinReqs.forEach(customerCheckinReq -> {
            HostedAt hostedAt = new HostedAt();
            hostedAt.setInvoiceDetail(invoiceDetail);

            Customer customer = new Customer();
            customer.setId(customerCheckinReq.getCustomerId());
            hostedAt.setCustomer(customer);

            hostedAt.setCheckin(new Date());
            hostedAts.add(hostedAt);
        });
        hostedAtService.updateAll(hostedAts);

        // update status for BookingDetail
        bookingDetail.setStatus(2); // trạng thái đã nhận
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
        if (room.getStatus() == 0) {
            BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(room.getCode());
            if (bookingDetail != null) {
                // update status for BookingDetail
                bookingDetail.setNote(note);
                bookingDetail.setStatus(0); // trạng thái huỷ
                bookingDetailService.update(bookingDetail);

                // trạng thái phòng trống
                room.setStatus(0);
                roomService.update(room);
            } else {
                throw new RuntimeException("Huỷ phòng thất bại");
            }
        } else if (room.getStatus() == 2) {
            InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(room.getCode());
            if (invoiceDetail != null) {
                // update status for invoiceDetail

                // tính tổng tiền dịch vụ
                List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
                Double totalServiceFee = usedServices.stream()
                        .map(usedService -> usedService.getServiceRoom().getPrice() * usedService.getQuantity())
                        .reduce(0.0, Double::sum);

                invoiceDetail.setTotalServiceFee(totalServiceFee);

                // tính tổng tiền phòng
                Date checkin = invoiceDetail.getInvoice().getBooking().getCheckinExpected();
                Date now = new Date();

                Double totalRoomFee = room.getPrice() * (now.getDate() - checkin.getDate());

                invoiceDetail.setTotalRoomFee(totalRoomFee);
                //
                invoiceDetail.setTotal(totalRoomFee + totalServiceFee);
                invoiceDetail.setNote(note);
                invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
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
    public InvoiceDetail checkout(CheckoutRoom checkoutRoom) {
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(checkoutRoom.getCode());
        if (invoiceDetail == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + checkoutRoom.getCode());
        }
        Invoice invoice = invoiceDetail.getInvoice();
        Room room = invoiceDetail.getRoom();

        // tính tổng tiền dịch vụ
        List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
        Double totalServiceFee = usedServices.stream()
                .map(usedService -> usedService.getServiceRoom().getPrice() * usedService.getQuantity())
                .reduce(0.0, Double::sum);

        invoiceDetail.setTotalServiceFee(totalServiceFee);

        // tính tổng tiền phòng
        Date checkin = invoice.getBooking().getCheckinExpected();
        Date now = new Date();

        Double totalRoomFee = room.getPrice() * (now.getDate() - checkin.getDate());

        invoiceDetail.setTotalRoomFee(totalRoomFee);
        //
        invoiceDetail.setTotal(totalRoomFee + totalServiceFee);
        invoiceDetail.setEarlyCheckinFee(0.0);
        invoiceDetail.setLateCheckoutFee(0.0);
        invoiceDetail.setCheckout(now);
        invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
        if (invoiceDetailService.update(invoiceDetail) != null) {
            room.setStatus(0); // trạng thái trống
            roomService.update(room);
        }
        return invoiceDetail;
    }

}
