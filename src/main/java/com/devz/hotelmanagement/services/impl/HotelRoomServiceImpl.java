package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
    private InvoiceDetailHistoryService invoiceDetailHistoryService;

    @Autowired
    private HostedAtService hostedAtService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ServiceRoomService serviceRoomService;

    @Autowired
    private SurchargeService surchargeService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private SettingService settingService;

    @Override
    public HotelResp getHotel() {
        int status = 0, status1 = 0, status2 = 0, status3 = 0, status4 = 0, status5 = 0, status6 = 0;
        HotelResp hotelResp = new HotelResp();
        Setting setting = settingService.getSetting();
        List<Room> rooms = roomService.findAllByCodeASC();
        List<StatusCountResp> statusCountResps = new ArrayList<>();
        List<HotelRoomResp> hotelRoomResps = new ArrayList<>();
        for (Room room : rooms) {
            HotelRoomResp hotelRoomResp = new HotelRoomResp();
            hotelRoomResp.setCode(room.getCode());
            hotelRoomResp.setRoomType(room.getRoomType().getName());
            if (room.getStatus() == 0) {
                BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(room.getCode());
                if (bookingDetail != null) {
                    hotelRoomResp.setBookingDetailId(bookingDetail.getId());
                    hotelRoomResp.setCustomer(bookingDetail.getBooking().getCustomer().getFullName() + " - " + bookingDetail.getBooking().getCustomer().getPhoneNumber());
                    Date checkinExpected = bookingDetail.getCheckinExpected();
                    Date checkoutExpected = bookingDetail.getCheckoutExpected();
                    hotelRoomResp.setCheckinExpected(checkinExpected);
                    hotelRoomResp.setCheckoutExpected(checkoutExpected);
                    // ngày
                    LocalDate nowDate = LocalDate.now();
                    LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
                    // giờ
                    LocalTime nowTime = LocalTime.now();
                    LocalTime checkinTime = LocalTime.ofInstant(setting.getCheckinTime().toInstant(), ZoneId.systemDefault());
                    checkinTime.minusMinutes(30); // trừ 30p

                    // nowDate là sau checkinExpectedDate || (nowDate ko là sau và trc checkinExpectedDate (nowDate == checkinExpectedDate) && nowTime là sau checkinTime)
                    if (nowDate.isAfter(checkinExpectedDate) || (!nowDate.isBefore(checkinExpectedDate) && !nowDate.isAfter(checkinExpectedDate) && nowTime.isAfter(checkinTime))) {
                        // chờ checkin
                        status4++;
                        hotelRoomResp.setStatus(4);
                    } else {
                        status++;
                        hotelRoomResp.setStatus(0);
                    }
                } else {
                    status++;
                    hotelRoomResp.setStatus(0);
                }
            } else if (room.getStatus() == 2) {
                InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(room.getCode());
                if (invoiceDetail != null) {
                    hotelRoomResp.setInvoiceDetailId(invoiceDetail.getId());
                    hotelRoomResp.setCustomer(invoiceDetail.getInvoice().getBooking().getCustomer().getFullName() + " - " + invoiceDetail.getInvoice().getBooking().getCustomer().getPhoneNumber());
                    Date checkinExpected = invoiceDetail.getCheckinExpected();
                    Date checkoutExpected = invoiceDetail.getCheckoutExpected();
                    hotelRoomResp.setCheckinExpected(checkinExpected);
                    hotelRoomResp.setCheckoutExpected(checkoutExpected);
                    // ngày
                    LocalDate nowDate = LocalDate.now();
                    LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
                    // giờ
                    LocalTime nowTime = LocalTime.now();
                    LocalTime checkoutTime = LocalTime.ofInstant(setting.getCheckoutTime().toInstant(), ZoneId.systemDefault());

                    // nowDate là sau checkoutExpectedDate || (nowDate ko là sau và trc checkoutExpectedDate && nowTime là sau checkoutTime)
                    if (nowDate.isAfter(checkoutExpectedDate) || (!nowDate.isBefore(checkoutExpectedDate) && !nowDate.isAfter(checkoutExpectedDate) && nowTime.isAfter(checkoutTime))) {
                        // quá hạn
                        status5++;
                        hotelRoomResp.setStatus(5);
                    } else {
                        // đang ở
                        status2++;
                        hotelRoomResp.setStatus(2);
                    }
                } else {
                    // trống
                    status++;
                    hotelRoomResp.setStatus(0);
                }
            } else {
                if (room.getStatus() == 1) {
                    status1++;
                } else if (room.getStatus() == 3) {
                    status3++;
                } else {
                    status6++;
                }
                hotelRoomResp.setStatus(room.getStatus());
            }
            hotelRoomResps.add(hotelRoomResp);
        }
        statusCountResps.add(new StatusCountResp(0, (long) status));
        statusCountResps.add(new StatusCountResp(1, (long) status1));
        statusCountResps.add(new StatusCountResp(2, (long) status2));
        statusCountResps.add(new StatusCountResp(3, (long) status3));
        statusCountResps.add(new StatusCountResp(4, (long) status4));
        statusCountResps.add(new StatusCountResp(5, (long) status5));
        statusCountResps.add(new StatusCountResp(6, (long) status6));
        hotelResp.setStatusCounts(statusCountResps);
        hotelResp.setHotelRooms(hotelRoomResps);
        return hotelResp;
    }

    @Override
    public void checkin(CheckinRoomReq checkinRoomReq) {
        BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(checkinRoomReq.getCode());
        if (bookingDetail == null) {
            throw new RuntimeException("Không tìm thấy BookingDetail của room " + checkinRoomReq.getCode());
        }
        Booking booking = bookingDetail.getBooking();
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
            invoice.setTotalDeposit(0.0);
            invoice.setDiscountMoney(0.0);
            invoice.setTotal(0.0);
            invoice.setTotalPayment(0.0);
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
        invoiceDetail.setRoomPrice(bookingDetail.getRoomPrice());
        invoiceDetail.setTotalRoomFee(0.0);
        invoiceDetail.setTotalServiceFee(0.0);
        invoiceDetail.setEarlyCheckinFee(0.0);
        invoiceDetail.setLateCheckoutFee(0.0);

        if (booking.getDeposit() > 0) {
            LocalDate checkinExpectedDate = LocalDate.ofInstant(bookingDetail.getCheckinExpected().toInstant(), ZoneId.systemDefault());
            LocalDate checkoutExpectedDate = LocalDate.ofInstant(bookingDetail.getCheckoutExpected().toInstant(), ZoneId.systemDefault());

            long days = ChronoUnit.DAYS.between(checkinExpectedDate, checkoutExpectedDate);
            invoiceDetail.setDeposit(bookingDetail.getRoom().getPrice() * days * 0.1);
        } else {
            invoiceDetail.setDeposit(0.0);
        }

        invoiceDetail.setTotal(0.0);
        invoiceDetail.setCheckinExpected(bookingDetail.getCheckinExpected());
        invoiceDetail.setCheckoutExpected(bookingDetail.getCheckoutExpected());
        invoiceDetail.setCheckin(new Date());
        invoiceDetail.setStatus(1); // trạng thái đang sử dụng
        if (invoiceDetailService.create(invoiceDetail) == null) {
            throw new RuntimeException("check in thất bại " + checkinRoomReq.getCode());
        }
        // update InvoiceDetail for all UsedService in BookingDetail
        serviceCheckinReqs.forEach(serviceCheckinReq -> {
            UsedService usedService = new UsedService();

            ServiceRoom serviceRoom = new ServiceRoom();
            serviceRoom.setId(serviceCheckinReq.getServiceId());
            usedService.setServiceRoom(serviceRoom);

            usedService.setServicePrice(serviceRoom.getPrice());
            usedService.setQuantity(serviceCheckinReq.getQuantity());
            usedService.setStartedTime(new Date());
            usedService.setIsUsed(false);
            usedService.setInvoiceDetail(invoiceDetail);
            usedServiceService.create(usedService);
        });

        // update InvoiceDetail for all HostedAt in BookingDetail
        List<HostedAt> hostedAts = new ArrayList<>();
        customerCheckinReqs.forEach(customerCheckinReq -> {
            HostedAt hostedAt = new HostedAt();
            hostedAt.setInvoiceDetail(invoiceDetail);

            Customer customer = customerService.findById(customerCheckinReq.getCustomerId());
            if (customer != null) {
                customer.setId(customerCheckinReq.getCustomerId());
                hostedAt.setCustomer(customer);

                hostedAt.setCheckin(new Date());
                hostedAts.add(hostedAt);
            }
        });
        hostedAtService.updateAll(hostedAts);

        // update status for BookingDetail
        bookingDetail.setStatus(2); // trạng thái đã nhận
        if (bookingDetailService.update(bookingDetail) == null) {
            throw new RuntimeException("Cập nhật trạng thái BookingDetail " + bookingDetail.getCode() + " thất bại!");
        }

        // update status for Room
        Room room = bookingDetail.getRoom();
        room.setStatus(2); // trạng thái đang ở
        if (roomService.update(room) == null) {
            throw new RuntimeException("Cập nhật trạng thái Room " + room.getCode() + " thất bại!");
        }
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
                        .filter(usedService -> usedService.getIsUsed())
                        .map(usedService -> usedService.getServiceRoom().getPrice() * usedService.getQuantity())
                        .reduce(0.0, Double::sum);

                invoiceDetail.setTotalServiceFee(totalServiceFee);

                // tính tổng tiền phòng
                Date checkinExpected = invoiceDetail.getCheckinExpected();
                Date checkoutExpected = invoiceDetail.getCheckoutExpected();

                LocalDate now = LocalDate.now();
                LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
                LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());

                long days;
                if (!now.isAfter(checkinExpectedDate) && !now.isBefore(checkinExpectedDate)) {
                    days = 0;
                } else if (now.isAfter(checkoutExpectedDate)) {
                    days = ChronoUnit.DAYS.between(checkinExpectedDate, checkoutExpectedDate);
                } else {
                    days = ChronoUnit.DAYS.between(checkinExpectedDate, now);
                }
                Double totalRoomFee = room.getPrice() * days;

                invoiceDetail.setTotalRoomFee(totalRoomFee);
                //
                invoiceDetail.setTotal(totalRoomFee + totalServiceFee - invoiceDetail.getDeposit());
                invoiceDetail.setNote(note);
                invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
                invoiceDetailService.update(invoiceDetail);

                // Trạng thái dọn phòng
                room.setStatus(6);
                if (roomService.update(room) == null) {
                    throw new RuntimeException("Cập nhật trạng thái phòng " + room.getCode() + " thất bại");
                }
            } else {
                throw new RuntimeException("Huỷ phòng thất bại");
            }
        } else {
            throw new RuntimeException("Huỷ phòng thất bại");
        }
    }

    @Override
    public void checkout(CheckoutRoomReq checkoutRoomReq) {
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(checkoutRoomReq.getCode());
        if (invoiceDetail == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + checkoutRoomReq.getCode());
        }
        Invoice invoice = invoiceDetail.getInvoice();
        Room room = invoiceDetail.getRoom();
        RoomType roomType = room.getRoomType();

        // Tính tổng tiền dịch vụ
        List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
        Double totalServiceFee = usedServices.stream()
                .filter(usedService -> usedService.getIsUsed())
                .map(usedService -> usedService.getServiceRoom().getPrice() * usedService.getQuantity())
                .reduce(0.0, Double::sum);

        invoiceDetail.setTotalServiceFee(totalServiceFee);

        LocalDate today = LocalDate.now();

        // Tính tổng tiền phòng
        Date checkinExpected = invoiceDetail.getCheckinExpected();
        Date checkoutExpected = invoiceDetail.getCheckoutExpected();
        LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());

        long days;
        if (!today.isAfter(checkinExpectedDate) && !today.isBefore(checkinExpectedDate)) {
            days = 1;
        } else if (today.isAfter(checkoutExpectedDate)) {
            days = ChronoUnit.DAYS.between(checkinExpectedDate, checkoutExpectedDate);
        } else {
            days = ChronoUnit.DAYS.between(checkinExpectedDate, today);
        }
        Double totalRoomFee = room.getPrice() * days;

        invoiceDetail.setTotalRoomFee(totalRoomFee);
        // Tính phụ phí người ở
        List<HostedAt> hostedAts = hostedAtService.findByInvoiceDetailId(invoiceDetail.getId());

        long numAdults = hostedAts.stream().filter(hostedAt -> {
            LocalDate birthdate = LocalDate.ofInstant(hostedAt.getCustomer().getDateOfBirth().toInstant(), ZoneId.systemDefault());
            int age = Period.between(birthdate, today).getYears();
            return age >= 13;
        }).count();

        long numChilds = hostedAts.stream().filter(hostedAt -> {
            LocalDate birthdate = LocalDate.ofInstant(hostedAt.getCustomer().getDateOfBirth().toInstant(), ZoneId.systemDefault());
            int age = Period.between(birthdate, today).getYears();
            return age < 13;
        }).count();

        Double totalAdultSurcharge = roomType.getAdultSurcharge() * (numAdults - room.getNumAdults());
        Double totalChildSurcharge = roomType.getChildSurcharge() * (numChilds - room.getNumChilds());

        //
        Double lateCheckoutFee = 0.0;
        Double earlyCheckinFee = 0.0;

        invoiceDetail.setLateCheckoutFee(lateCheckoutFee);
        invoiceDetail.setEarlyCheckinFee(earlyCheckinFee);

        Double total = totalRoomFee + totalServiceFee + lateCheckoutFee + earlyCheckinFee - invoiceDetail.getDeposit();

        invoiceDetail.setTotal(total);
        invoiceDetail.setCheckout(new Date());
        invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
        if (invoiceDetailService.update(invoiceDetail) == null) {
            throw new RuntimeException("Cập nhật InvoiceDetail " + invoiceDetail.getCode() + " thất bại!");
        }
        // trạng thái dọn phòng
        room.setStatus(6);
        if (roomService.update(room) == null) {
            throw new RuntimeException("Cập nhật Room " + room.getCode() + " thất bại!");
        }

        List<InvoiceDetail> invoiceDetails = invoiceDetailService.findByInvoiceCode(invoice.getCode());
        // cập nhật tổng tiền trả trước
        Double totalDeposit = invoiceDetails.stream().map(ivd -> ivd.getDeposit()).reduce(0.0, Double::sum);
        invoice.setTotalDeposit(totalDeposit);

        // cập nhật tổng tiền hoá đơn
        Double totalInvoice = invoiceDetails.stream().map(ivd -> ivd.getTotal()).reduce(0.0, Double::sum);
        invoice.setTotal(totalInvoice - totalDeposit);
        // kiểm tra các hoá đơn chi tiết đã hoàn thành hay chưa
        boolean isSuccess = invoiceDetails.stream().allMatch(ivd -> ivd.getStatus() == 2);
        if (isSuccess) {
            // trạng thái chờ thanh toán
            invoice.setStatus(2);
        }
        if (invoiceService.update(invoice) == null) {
            throw new RuntimeException("Cập nhật Invoice " + invoice.getCode() + " thất bại!");
        }
    }

    @Override
    public InvoiceDetail extendCheckoutDate(String code, Date extendDate, String note) {
        if (code == null || extendDate == null || note == null) {
            throw new RuntimeException("Dữ liệu không hợp lệ");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(code);
        if (invoiceDetail == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + code);
        }
        Date checkinExpected = invoiceDetail.getCheckinExpected();
        Date checkoutExpected = invoiceDetail.getCheckoutExpected();
        LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());

        Date newCheckout = extendDate; // checkout gia hạn
        LocalDate newCheckoutDate = LocalDate.ofInstant(newCheckout.toInstant(), ZoneId.systemDefault());

        if (!checkoutExpectedDate.isAfter(newCheckoutDate) && !checkoutExpectedDate.isBefore(newCheckoutDate)) {
            throw new RuntimeException("Ngày checkout hiện tại " + code);
        }
        List<BookingDetail> bookingDetails = bookingDetailService.findByRoomCodeAndCheckinAndCheckout(code, checkinExpected, newCheckout);
        for (BookingDetail bookingDetail : bookingDetails) {
            System.out.println(bookingDetail.getRoom().getCode());
        }
        if (bookingDetails.size() > 0) {
            throw new RuntimeException("Ngày checkout không hợp lệ " + code);
        }
        InvoiceDetailHistory invoiceDetailHistory = new InvoiceDetailHistory();
        invoiceDetailHistory.setInvoiceDetail(invoiceDetail);
        invoiceDetailHistory.setCheckinExpected(invoiceDetail.getCheckinExpected());
        invoiceDetailHistory.setCheckoutExpected(invoiceDetail.getCheckoutExpected());
        invoiceDetailHistory.setTotal(invoiceDetail.getTotal());
        invoiceDetailHistory.setTotalRoomFee(invoiceDetail.getTotalRoomFee());
        invoiceDetailHistory.setTotalServiceFee(invoiceDetail.getTotalServiceFee());
        invoiceDetailHistory.setEarlyCheckinFee(invoiceDetail.getEarlyCheckinFee());
        invoiceDetailHistory.setLateCheckoutFee(invoiceDetail.getLateCheckoutFee());
        invoiceDetailHistory.setNote("Gia hạn thêm ngày. " + note);
        invoiceDetailHistory.setUpdateDate(new Date());
        if (invoiceDetailHistoryService.create(invoiceDetailHistory) == null) {
            throw new RuntimeException("Tạo InvoiceDetailHistory thất bại của room " + code);
        }
        invoiceDetail.setCheckoutExpected(newCheckout);
        if (invoiceDetailService.update(invoiceDetail) == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + code);
        }
        return invoiceDetail;
    }

    @Override
    public void ready(String code) {
        Room room = roomService.findByCode(code);
        if (room == null) {
            throw new RuntimeException("Không tìm thấy phòng " + code);
        }
        if (room.getStatus() != 6) {
            throw new RuntimeException("Trạng thái không hợp lệ");
        }
        room.setStatus(0);
        if (roomService.update(room) == null) {
            throw new RuntimeException("Cập nhật trạng thái thất bại");
        }
    }

    @Override
    public void change(String fromRoomCode, String toRoomCode, String note) {
        if (fromRoomCode == null || toRoomCode == null || note == null) {
            throw new RuntimeException("Dữ liệu không hợp lệ");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(fromRoomCode);
        if (invoiceDetail == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + fromRoomCode);
        }
        Room oldRoom = invoiceDetail.getRoom();
        List<Room> rooms = roomService.findUnbookedRoomsByCheckinAndCheckout(invoiceDetail.getCheckinExpected(), invoiceDetail.getCheckoutExpected());
        if (rooms.size() <= 0) {
            throw new RuntimeException("Không tìm thấy phòng hợp lệ");
        }
        Room newRoom = rooms.stream().filter(room -> toRoomCode.equals(room.getCode())).findAny().orElse(null);
        if (newRoom == null) {
            throw new RuntimeException("Không tìm thấy room " + toRoomCode);
        }
        // Checkout phòng cũ - Tính tổng tiền phòng
        Date checkinExpected = invoiceDetail.getCheckinExpected();

        LocalDate now = LocalDate.now();
        LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());

        long days = ChronoUnit.DAYS.between(checkinExpectedDate, now);

        Double totalRoomFee = oldRoom.getPrice() * days;

        invoiceDetail.setTotalRoomFee(totalRoomFee);
        invoiceDetail.setTotal(totalRoomFee);
        invoiceDetail.setCheckout(new Date());
        invoiceDetail.setStatus(2); // trạng thái đã sử dụng
        invoiceDetail.setNote("Đổi sang phòng " + toRoomCode + ". " + note);
        if (invoiceDetailService.update(invoiceDetail) == null) {
            throw new RuntimeException("Không tìm thấy InvoiceDetail của room " + fromRoomCode);
        }
        // Trạng thái dọn phòng
        oldRoom.setStatus(6);
        if (roomService.update(oldRoom) == null) {
            throw new RuntimeException("Cập nhật trạng thái phòng " + fromRoomCode + " thất bại");
        }
        // Tạo InvoiceDetail cho phòng muốn đổi
        InvoiceDetail newInvoiceDetail = new InvoiceDetail();
        newInvoiceDetail.setRoom(newRoom);
        newInvoiceDetail.setRoomPrice(newRoom.getPrice());
        newInvoiceDetail.setInvoice(invoiceDetail.getInvoice());
        newInvoiceDetail.setCheckin(new Date());
        newInvoiceDetail.setCheckinExpected(new Date());
        newInvoiceDetail.setCheckoutExpected(invoiceDetail.getCheckoutExpected());
        newInvoiceDetail.setDeposit(0.0);
        newInvoiceDetail.setTotalRoomFee(0.0);
        newInvoiceDetail.setTotalServiceFee(0.0);
        newInvoiceDetail.setEarlyCheckinFee(0.0);
        newInvoiceDetail.setLateCheckoutFee(0.0);
        newInvoiceDetail.setTotal(0.0);
        newInvoiceDetail.setNote("");
        newInvoiceDetail.setStatus(1); // trạng thái đang sử dụng
        if (invoiceDetailService.create(newInvoiceDetail) == null) {
            throw new RuntimeException("Tạo InvoiceDetail thất bại");
        }
        // Chuyển DS UsedService sang InvoiceDetail mới
        List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
        usedServices.forEach(usedService -> usedService.setInvoiceDetail(newInvoiceDetail));
        if (usedServiceService.updateAll(usedServices).size() != usedServices.size()) {
            throw new RuntimeException("Cập nhật DS UsedService thất bại");
        }
        // Chuyển DS HostedAt sang InvoiceDetail mới
        List<HostedAt> hostedAts = hostedAtService.findByInvoiceDetailId(invoiceDetail.getId());
        hostedAts.forEach(hostedAt -> hostedAt.setInvoiceDetail(newInvoiceDetail));
        if (hostedAtService.updateAll(hostedAts).size() != hostedAts.size()) {
            throw new RuntimeException("Cập nhật DS HostedAt thất bại");
        }
        // Trạng thái đang ở
        newRoom.setStatus(2);
        if (roomService.update(newRoom) == null) {
            throw new RuntimeException("Cập nhật trạng thái phòng " + toRoomCode + " thất bại");
        }
    }

    @Override
    public void payment(String invoiceCode, String promotionCode, String paymentMethodCode) {
        if (invoiceCode == null || paymentMethodCode == null) {
            throw new RuntimeException("Dữ liệu không hợp lệ");
        }
        Invoice invoice = invoiceService.findByCode(invoiceCode);
        if (invoice == null) {
            throw new RuntimeException("Không tìm thấy Invoice " + invoiceCode);
        }
        if (invoice.getStatus() != 2) {
            throw new RuntimeException("Trạng thái Invoice " + invoiceCode + " không hợp lệ");
        }
        PaymentMethod paymentMethod = paymentMethodService.findByCode(paymentMethodCode);
        if (paymentMethod == null) {
            throw new RuntimeException("Không tìm thấy PaymentMethod " + invoiceCode);
        }
        invoice.setPaymentMethod(paymentMethod);
        if (promotionCode != null) {
            Promotion promotion = promotionService.findByCode(promotionCode);
            if (promotion == null) {
                throw new RuntimeException("Không tìm thấy Promotion " + invoiceCode);
            }
            if (promotion.getMinAmount() > invoice.getTotal()) {
                throw new RuntimeException("Invoice " + invoiceCode + " không đủ điều kiện sửa dụng Promotion " + promotionCode);
            }
            Double discount = invoice.getTotal() / 100 * promotion.getPercent();
            if (discount >= promotion.getMaxDiscount()) {
                discount = promotion.getMaxDiscount();
            }
            invoice.setDiscountMoney(discount);
            invoice.setTotalPayment(invoice.getTotal() - discount);
            invoice.setPromotion(promotion);
        } else {
            invoice.setDiscountMoney(0.0);
            invoice.setTotalPayment(invoice.getTotal());
        }
        switch (paymentMethod.getCode()) {
            case "PM001":
                invoice.setPaidDate(new Date());
                invoice.setStatus(4);
                break;
            case "PM002":
            case "PM003":
            case "PM004":
                invoice.setStatus(3);
                break;
        }
        if (invoiceService.update(invoice) == null) {
            throw new RuntimeException("Cập nhật Invoice " + invoiceCode + " không thành công");
        }
    }

    @Override
    public void confirmPayment(String invoiceCode) {
        if (invoiceCode == null) {
            throw new RuntimeException("Dữ liệu không hợp lệ");
        }
        Invoice invoice = invoiceService.findByCode(invoiceCode);
        if (invoice == null) {
            throw new RuntimeException("Không tìm thấy Invoice " + invoiceCode);
        }
        if (invoice.getStatus() != 3) {
            throw new RuntimeException("Trạng thái Invoice " + invoiceCode + " không hợp lệ");
        }
        invoice.setPaidDate(new Date());
        invoice.setStatus(4);
        if (invoiceService.update(invoice) == null) {
            throw new RuntimeException("Cập nhật Invoice " + invoiceCode + " không thành công");
        }
    }

}
