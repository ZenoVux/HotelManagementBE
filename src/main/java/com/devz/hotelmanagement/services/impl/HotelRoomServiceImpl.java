package com.devz.hotelmanagement.services.impl;

import com.devz.hotelmanagement.entities.*;
import com.devz.hotelmanagement.models.*;
import com.devz.hotelmanagement.services.*;
import com.devz.hotelmanagement.statuses.InvoiceDetailStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private BookingService bookingService;

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
    private PromotionRoomService promotionRoomService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private AccountService accountService;

    @Override
    public HotelResp getHotel() {
        int status = 0, status1 = 0, status2 = 0, status3 = 0, status4 = 0, status5 = 0, status6 = 0;
        HotelResp hotelResp = new HotelResp();
        Setting setting = settingService.getSetting();
        List<Room> rooms = roomService.findAllByCodeASC();
        List<BookingDetail> bookingDetails = bookingDetailService.findAllWaitingCheckin();
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.findAllUsing();

        List<StatusCountResp> statusCountResps = new ArrayList<>();
        List<HotelRoomResp> hotelRoomResps = new ArrayList<>();
        for (Room room : rooms) {
            HotelRoomResp hotelRoomResp = new HotelRoomResp();
            hotelRoomResp.setCode(room.getCode());
            hotelRoomResp.setRoomType(room.getRoomType().getName());
            if (room.getStatus() == 0) {
                BookingDetail bookingDetail = bookingDetails.stream().filter(bkd -> bkd.getRoom().getCode().equals(room.getCode())).findAny().orElse(null);//bookingDetailService.findWaitingCheckinByRoomCode(room.getCode());
                if (bookingDetail != null) {
                    Date checkinExpected = bookingDetail.getCheckinExpected();
                    Date checkoutExpected = bookingDetail.getCheckoutExpected();
                    // ngày
                    LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
                    LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
                    LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
                    // giờ
                    LocalTime nowTime = LocalTime.now();
                    LocalTime checkinTime = LocalTime.ofInstant(setting.getCheckinTime().toInstant(), ZoneId.systemDefault());
                    checkinTime.minusMinutes(30); // trừ 30p

                    //(nowDate > checkinExpectedDate && nowDate < checkoutExpectedDate) || (nowDate == checkinExpectedDate && nowTime > checkinTime)
                    if ((nowDate.isAfter(checkinExpectedDate) && nowDate.isBefore(checkoutExpectedDate)) || (!nowDate.isBefore(checkinExpectedDate) && !nowDate.isAfter(checkinExpectedDate) && nowTime.isAfter(checkinTime))) {
                        hotelRoomResp.setBookingCode(bookingDetail.getBooking().getCode());
                        hotelRoomResp.setBookingDetailId(bookingDetail.getId());
                        hotelRoomResp.setCustomer(bookingDetail.getBooking().getCustomer().getFullName());
                        hotelRoomResp.setPhoneNumber(bookingDetail.getBooking().getCustomer().getPhoneNumber());
                        hotelRoomResp.setCheckinExpected(checkinExpected);
                        hotelRoomResp.setCheckoutExpected(checkoutExpected);
                        // chờ checkin
                        status4++;
                        hotelRoomResp.setStatus(4);
                    } else {
                        status++;
                        hotelRoomResp.setStatus(0);
                    }
                    bookingDetails.remove(bookingDetail);
                } else {
                    status++;
                    hotelRoomResp.setBookingCode("");
                    hotelRoomResp.setStatus(0);
                }
            } else if (room.getStatus() == 2) {
                InvoiceDetail invoiceDetail = invoiceDetails.stream().filter(ivd -> ivd.getRoom().getCode().equals(room.getCode())).findAny().orElse(null);;//invoiceDetailService.findUsingByRoomCode(room.getCode());
                if (invoiceDetail != null) {
                    hotelRoomResp.setBookingCode(invoiceDetail.getInvoice().getBooking().getCode());
                    hotelRoomResp.setInvoiceDetailId(invoiceDetail.getId());
                    hotelRoomResp.setCustomer(invoiceDetail.getInvoice().getBooking().getCustomer().getFullName());
                    hotelRoomResp.setPhoneNumber(invoiceDetail.getInvoice().getBooking().getCustomer().getPhoneNumber());
                    Date checkinExpected = invoiceDetail.getCheckinExpected();
                    Date checkoutExpected = invoiceDetail.getCheckoutExpected();
                    hotelRoomResp.setCheckinExpected(checkinExpected);
                    hotelRoomResp.setCheckoutExpected(checkoutExpected);
                    // ngày
                    LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
                    LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
                    // giờ
                    LocalTime nowTime = LocalTime.now();
                    LocalTime checkoutTime = LocalTime.ofInstant(setting.getCheckoutTime().toInstant(), ZoneId.systemDefault());

                    // nowDate > checkoutExpectedDate || (nowDate == checkoutExpectedDate && nowTime > checkoutTime)
                    if (nowDate.isAfter(checkoutExpectedDate) || (!nowDate.isBefore(checkoutExpectedDate) && !nowDate.isAfter(checkoutExpectedDate) && nowTime.isAfter(checkoutTime))) {
                        // quá hạn
                        status5++;
                        hotelRoomResp.setStatus(5);
                    } else {
                        // đang ở
                        status2++;
                        hotelRoomResp.setStatus(2);
                    }
                    invoiceDetails.remove(invoiceDetail);
                } else {
                    // trống
                    status++;
                    hotelRoomResp.setBookingCode("");
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
                hotelRoomResp.setBookingCode("");
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
    public HotelRoomResp getHotelRoom(String code) {
        Room room = roomService.findByCode(code);
        if (room == null) {
            return null;
        }
        Setting setting = settingService.getSetting();
        HotelRoomResp hotelRoomResp = new HotelRoomResp();
        hotelRoomResp.setCode(room.getCode());
        hotelRoomResp.setRoomType(room.getRoomType().getName());
        if (room.getStatus() == 0) {
            BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(room.getCode());
            if (bookingDetail != null) {
                Date checkinExpected = bookingDetail.getCheckinExpected();
                Date checkoutExpected = bookingDetail.getCheckoutExpected();
                // ngày
                LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
                LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
                LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
                // giờ
                LocalTime nowTime = LocalTime.now();
                LocalTime checkinTime = LocalTime.ofInstant(setting.getCheckinTime().toInstant(), ZoneId.systemDefault());
                checkinTime.minusMinutes(30); // trừ 30p

                //(nowDate > checkinExpectedDate && nowDate < checkoutExpectedDate) || (nowDate == checkinExpectedDate && nowTime > checkinTime)
                if ((nowDate.isAfter(checkinExpectedDate) && nowDate.isBefore(checkoutExpectedDate)) || (!nowDate.isBefore(checkinExpectedDate) && !nowDate.isAfter(checkinExpectedDate) && nowTime.isAfter(checkinTime))) {
                    hotelRoomResp.setBookingCode(bookingDetail.getBooking().getCode());
                    hotelRoomResp.setBookingDetailId(bookingDetail.getId());
                    hotelRoomResp.setCustomer(bookingDetail.getBooking().getCustomer().getFullName());
                    hotelRoomResp.setPhoneNumber(bookingDetail.getBooking().getCustomer().getPhoneNumber());
                    hotelRoomResp.setCheckinExpected(checkinExpected);
                    hotelRoomResp.setCheckoutExpected(checkoutExpected);
                    // chờ checkin
                    hotelRoomResp.setStatus(4);
                } else {
                    hotelRoomResp.setStatus(0);
                }
            } else {
                hotelRoomResp.setBookingCode("");
                hotelRoomResp.setStatus(0);
            }
        } else if (room.getStatus() == 2) {
            InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(room.getCode());
            if (invoiceDetail != null) {
                hotelRoomResp.setBookingCode(invoiceDetail.getInvoice().getBooking().getCode());
                hotelRoomResp.setInvoiceDetailId(invoiceDetail.getId());
                hotelRoomResp.setCustomer(invoiceDetail.getInvoice().getBooking().getCustomer().getFullName());
                hotelRoomResp.setPhoneNumber(invoiceDetail.getInvoice().getBooking().getCustomer().getPhoneNumber());
                Date checkinExpected = invoiceDetail.getCheckinExpected();
                Date checkoutExpected = invoiceDetail.getCheckoutExpected();
                hotelRoomResp.setCheckinExpected(checkinExpected);
                hotelRoomResp.setCheckoutExpected(checkoutExpected);
                // ngày
                LocalDate nowDate = LocalDate.now(ZoneId.systemDefault());
                LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
                // giờ
                LocalTime nowTime = LocalTime.now();
                LocalTime checkoutTime = LocalTime.ofInstant(setting.getCheckoutTime().toInstant(), ZoneId.systemDefault());

                // nowDate > checkoutExpectedDate || (nowDate == checkoutExpectedDate && nowTime > checkoutTime)
                if (nowDate.isAfter(checkoutExpectedDate) || (!nowDate.isBefore(checkoutExpectedDate) && !nowDate.isAfter(checkoutExpectedDate) && nowTime.isAfter(checkoutTime))) {
                    // quá hạn
                    hotelRoomResp.setStatus(5);
                } else {
                    // đang ở
                    hotelRoomResp.setStatus(2);
                }
            } else {
                // trống
                hotelRoomResp.setBookingCode("");
                hotelRoomResp.setStatus(0);
            }
        } else {
            hotelRoomResp.setBookingCode("");
            hotelRoomResp.setStatus(room.getStatus());
        }
        return hotelRoomResp;
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void checkin(CheckinRoomReq checkinRoomReq) {
        BookingDetail bookingDetail = bookingDetailService.findWaitingCheckinByRoomCode(checkinRoomReq.getCode());
        if (bookingDetail == null) {
            // Không tìm thấy BookingDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = bookingDetail.getRoom();
        if (room.getStatus() != 0) {
            // Trạng thái phòng không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        // update status for Room
        room.setStatus(2); // trạng thái đang ở
        if (roomService.update(room) == null) {
            // Cập nhật trạng thái Room thất bại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        Booking booking = bookingDetail.getBooking();
        List<CustomerCheckinReq> customerCheckinReqs = checkinRoomReq.getCustomers();
        List<ServiceCheckinReq> serviceCheckinReqs = checkinRoomReq.getServices();

        if (customerCheckinReqs.size() <= 0) {
            // Phòng không có người ở
            throw new RuntimeException("{\"error\":\"Chưa có thông tin người ở!\"}");
        }
        Invoice invoice = invoiceService.findFirstByBookingId(booking.getId());
        if (invoice == null) {
            invoice = new Invoice();
            invoice.setBooking(booking);
            invoice.setCreatedDate(new Date());
            invoice.setTotalDeposit(0.0);
            invoice.setDiscountMoney(0.0);
            invoice.setTotal(0.0);
            invoice.setTotalPayment(0.0);
            invoice.setNote("");
            invoice.setStatus(1); // trạng thái chờ

            String username = req.getAttribute("username").toString();

            Account account = accountService.findByUsernameAndActivate(username);
            if (account == null) {
                // Chưa đăng nhập!
                throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
            }
            invoice.setAccount(account);
            if (invoiceService.create(invoice) == null) {
                // lỗi tạo invoice
                throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
            }
        }
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setInvoice(invoice);
        invoiceDetail.setRoom(bookingDetail.getRoom());
        invoiceDetail.setRoomPrice(bookingDetail.getRoomPrice());
        invoiceDetail.setTotalRoomFee(0.0);
        invoiceDetail.setTotalServiceFee(0.0);
        invoiceDetail.setAdultSurcharge(0.0);
        invoiceDetail.setChildSurcharge(0.0);
        invoiceDetail.setOrtherSurcharge(0.0);
        invoiceDetail.setEarlyCheckinFee(0.0);
        invoiceDetail.setLateCheckoutFee(0.0);

        if (booking.getDeposit() != null && booking.getDeposit() > 0) {
            LocalDate checkinExpectedDate = LocalDate.ofInstant(bookingDetail.getCheckinExpected().toInstant(), ZoneId.systemDefault());
            LocalDate checkoutExpectedDate = LocalDate.ofInstant(bookingDetail.getCheckoutExpected().toInstant(), ZoneId.systemDefault());

            long days = ChronoUnit.DAYS.between(checkinExpectedDate, checkoutExpectedDate);
            invoiceDetail.setDeposit(bookingDetail.getRoomPrice() * days);
        } else {
            invoiceDetail.setDeposit(0.0);
        }

        invoiceDetail.setTotal(0.0);
        invoiceDetail.setCheckinExpected(bookingDetail.getCheckinExpected());
        invoiceDetail.setCheckoutExpected(bookingDetail.getCheckoutExpected());
        invoiceDetail.setCheckin(new Date());
        invoiceDetail.setStatus(1); // trạng thái đang sử dụng
        invoiceDetail.setNote("");
        if (invoiceDetailService.create(invoiceDetail) == null) {
            // check in thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // update InvoiceDetail for all UsedService in BookingDetail
        List<UsedService> usedServices = new ArrayList<>();
        serviceCheckinReqs.forEach(serviceCheckinReq -> {
            ServiceRoom serviceRoom = serviceRoomService.findById(serviceCheckinReq.getServiceId());
            if (serviceRoom != null && serviceRoom.getStatus()) {
                UsedService usedService = new UsedService();
                usedService.setServiceRoom(serviceRoom);
                usedService.setInvoiceDetail(invoiceDetail);

                Date startedTime = Date.from(LocalDate.now(ZoneId.systemDefault()).atStartOfDay(ZoneId.systemDefault()).toInstant());

                usedService.setStartedTime(startedTime);
                usedService.setEndedTime(invoiceDetail.getCheckoutExpected());
                usedService.setQuantity(serviceCheckinReq.getQuantity());
                usedService.setServicePrice(serviceRoom.getPrice());
                usedService.setStatus(true);
                usedService.setNote("");
                usedServices.add(usedService);
            }
        });
        usedServiceService.updateAll(usedServices);

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
            // Cập nhật trạng thái BookingDetail thất bại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        // trạng thái đã xác nhận
        if (booking.getStatus() == 2) {
            List<BookingDetail> bookingDetails = bookingDetailService.findByBookingId(booking.getId());
            // kiểm tra các đơn đặt chi tiết đã hoàn thành hay chưa
            boolean isSuccess = bookingDetails.stream().allMatch(bkd -> bkd.getStatus() == 2);
            if (isSuccess) {
                booking.setStatus(3); // trạng thái đã xử lý
                if (bookingService.update(booking) == null) {
                    // Cập nhật Booking thất bại
                    throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
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
                if (roomService.update(room) == null) {
                    // Cập nhật trạng thái phòng thất bại
                    throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                }
            } else {
                // Huỷ phòng thất bại
                throw new RuntimeException("{\"error\":\"Huỷ phòng thất bại!\"}");
            }
        } else if (room.getStatus() == 2) {
            InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(room.getCode());
            if (invoiceDetail != null) {
                // update status for invoiceDetail

                // tính tổng tiền dịch vụ
                List<UsedService> usedServices = usedServiceService.findAllByInvoiceDetailIdAndStatus(invoiceDetail.getId(), true);
                Double totalServiceFee = usedServices.stream()
                        .map(usedService -> {
                            LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
                            LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
                            return usedService.getServicePrice() * ChronoUnit.DAYS.between(startedDate, endedDate);
                        })
                        .reduce(0.0, Double::sum);

                invoiceDetail.setTotalServiceFee(totalServiceFee);

                // tính tổng tiền phòng
                Date checkinExpected = invoiceDetail.getCheckinExpected();
                Date checkoutExpected = invoiceDetail.getCheckoutExpected();

                LocalDate now = LocalDate.now(ZoneId.systemDefault());
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
                Double totalRoomFee = invoiceDetail.getRoomPrice() * days;

                invoiceDetail.setTotalRoomFee(totalRoomFee);
                //
                invoiceDetail.setTotal(totalRoomFee + totalServiceFee - invoiceDetail.getDeposit());
                invoiceDetail.setNote(note);
                invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
                invoiceDetailService.update(invoiceDetail);

                // Trạng thái dọn phòng
                room.setStatus(6);
                if (roomService.update(room) == null) {
                    // Cập nhật trạng thái phòng thất bại
                    throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                }
            } else {
                // Huỷ phòng thất bại
                throw new RuntimeException("{\"error\":\"Huỷ phòng thất bại!\"}");
            }
        } else {
            // Huỷ phòng thất bại
            throw new RuntimeException("{\"error\":\"Huỷ phòng thất bại!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void checkout(CheckoutRoomReq checkoutRoomReq) {
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(checkoutRoomReq.getCode());
        if (invoiceDetail == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = invoiceDetail.getRoom();
        if (room.getStatus() != 2) {
            // Trạng thái phòng không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        // trạng thái dọn phòng
        room.setStatus(6);
        if (roomService.update(room) == null) {
            // Cập nhật Room thất bại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Invoice invoice = invoiceDetail.getInvoice();
        RoomType roomType = room.getRoomType();

        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        // Tính tổng tiền dịch vụ
        List<UsedService> usedServices = usedServiceService.findAllByInvoiceDetailIdAndStatus(invoiceDetail.getId(), true);

        usedServices.forEach(usedService -> {
            usedService.setEndedTime(Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
            LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
            if (!startedDate.isAfter(endedDate) && !startedDate.isBefore(endedDate)) {
                usedService.setStatus(false);
            }
        });
        usedServiceService.updateAll(usedServices);

        Double totalServiceFee = usedServices.stream()
                .filter(usedService -> usedService.getStatus())
                .map(usedService -> {
                    LocalDate startedDate = LocalDate.ofInstant(usedService.getStartedTime().toInstant(), ZoneId.systemDefault());
                    LocalDate endedDate = LocalDate.ofInstant(usedService.getEndedTime().toInstant(), ZoneId.systemDefault());
                    return usedService.getServicePrice() * ChronoUnit.DAYS.between(startedDate, endedDate);
                })
                .reduce(0.0, Double::sum);

        invoiceDetail.setTotalServiceFee(totalServiceFee);

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
        Double totalRoomFee = invoiceDetail.getRoomPrice() * days;

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

        Double totalAdultSurcharge = 0.0;
        Double totalChildSurcharge = 0.0;
        if (numAdults > roomType.getNumAdults()) {
            totalAdultSurcharge = roomType.getAdultSurcharge() * (numAdults - roomType.getNumAdults());
        }
        if (numChilds > roomType.getNumChilds()) {
            totalChildSurcharge = roomType.getChildSurcharge() * (numChilds - roomType.getNumChilds());
        }

        invoiceDetail.setAdultSurcharge(totalAdultSurcharge);
        invoiceDetail.setChildSurcharge(totalChildSurcharge);

        // Tính phụ phí nhận / trả phòng
        Double lateCheckoutFee = invoiceDetail.getEarlyCheckinFee();
        Double earlyCheckinFee = invoiceDetail.getLateCheckoutFee();

        Double total = totalRoomFee + totalServiceFee + totalAdultSurcharge + totalChildSurcharge + invoiceDetail.getOrtherSurcharge() + lateCheckoutFee + earlyCheckinFee - invoiceDetail.getDeposit();

        invoiceDetail.setTotal(total);
        invoiceDetail.setCheckout(new Date());
        invoiceDetail.setStatus(2); // trạng thái chờ thanh toán
        if (invoiceDetailService.update(invoiceDetail) == null) {
            // Cập nhật InvoiceDetail thất bại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        List<InvoiceDetail> invoiceDetails = invoiceDetailService.findByInvoiceCode(invoice.getCode());
        // kiểm tra các hoá đơn chi tiết đã hoàn thành hay chưa
        boolean isSuccess = invoiceDetails.stream().allMatch(ivd -> ivd.getStatus() == 2);
        if (isSuccess) {
            // Tổng tiền trả trước
            Double totalDeposit = invoiceDetails.stream().map(ivd -> ivd.getDeposit()).reduce(0.0, Double::sum);
            invoice.setTotalDeposit(totalDeposit);

            // Tổng tiền hoá đơn
            Double totalInvoice = invoiceDetails.stream().map(ivd -> ivd.getTotal()).reduce(0.0, Double::sum);
            invoice.setTotal(totalInvoice);

            // trạng thái chờ thanh toán
            invoice.setStatus(2);
        }
        if (invoiceService.update(invoice) == null) {
            // Cập nhật Invoice thất bại!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void extendCheckoutDate(String code, Date extendDate, String note) {
        if (code == null || extendDate == null || note == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(code);
        if (invoiceDetail == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = invoiceDetail.getRoom();
        if (room.getStatus() != 2) {
            // Trạng thái phòng không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Date checkinExpected = invoiceDetail.getCheckinExpected();
        Date checkoutExpected = invoiceDetail.getCheckoutExpected();
        LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());

        LocalDate newCheckoutDate = LocalDate.ofInstant(extendDate.toInstant(), ZoneId.systemDefault());

        if (!checkoutExpectedDate.isAfter(newCheckoutDate) && !checkoutExpectedDate.isBefore(newCheckoutDate)) {
            // Ngày checkout hiện tại
            throw new RuntimeException("{\"error\":\"Ngày trả phòng không phải là ngày hiện tại!\"}");
        }
        List<BookingDetail> bookingDetails = bookingDetailService.findByRoomCodeAndCheckinAndCheckout(code, checkinExpected, Date.from(newCheckoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (bookingDetails.size() > 0) {
            // Ngày checkout không hợp lệ
            throw new RuntimeException("{\"error\":\"Ngày trả phòng không hợp lệ!\"}");
        }
        InvoiceDetailHistory invoiceDetailHistory = new InvoiceDetailHistory();
        invoiceDetailHistory.setInvoiceDetail(invoiceDetail);
        invoiceDetailHistory.setCheckinExpected(invoiceDetail.getCheckinExpected());
        invoiceDetailHistory.setCheckoutExpected(invoiceDetail.getCheckoutExpected());
        invoiceDetailHistory.setRoomPrice(invoiceDetail.getRoomPrice());
        invoiceDetailHistory.setDeposit(invoiceDetail.getDeposit());
        invoiceDetailHistory.setOrtherSurcharge(invoiceDetail.getOrtherSurcharge());
        invoiceDetailHistory.setEarlyCheckinFee(invoiceDetail.getEarlyCheckinFee());
        invoiceDetailHistory.setLateCheckoutFee(invoiceDetail.getLateCheckoutFee());
        invoiceDetailHistory.setNote(invoiceDetail.getNote());
        invoiceDetailHistory.setUpdateDate(new Date());
        if (invoiceDetailHistoryService.create(invoiceDetailHistory) == null) {
            throw new RuntimeException("Tạo InvoiceDetailHistory thất bại của room " + code);
        }
        invoiceDetail.setCheckoutExpected(Date.from(newCheckoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        invoiceDetail.setNote("Gia hạn thêm ngày. " + note);
        if (invoiceDetailService.update(invoiceDetail) == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

    @Override
    public void checkExtendCheckoutDate(String code, Date checkoutDate) {
        if (code == null || checkoutDate == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(code);
        if (invoiceDetail == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = invoiceDetail.getRoom();
        if (room.getStatus() != 2 && room.getStatus() != 5) {
            // Trạng thái phòng không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Date checkinExpected = invoiceDetail.getCheckinExpected();
        LocalDate newCheckoutDate = LocalDate.ofInstant(checkoutDate.toInstant(), ZoneId.systemDefault());
        List<BookingDetail> bookingDetails = bookingDetailService.findByRoomCodeAndCheckinAndCheckout(code, checkinExpected, Date.from(newCheckoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (bookingDetails.size() > 0) {
            // Ngày checkout không hợp lệ
            throw new RuntimeException("{\"error\":\"Ngày trả phòng không hợp lệ!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void ready(String code) {
        Room room = roomService.findByCode(code);
        if (room == null) {
            // Không tìm thấy phòng
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (room.getStatus() != 6) {
            // Trạng thái không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        room.setStatus(0);
        if (roomService.update(room) == null) {
            // Cập nhật trạng thái thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void change(String fromRoomCode, String toRoomCode, Date checkoutDate, String note) {
        if (fromRoomCode == null || toRoomCode == null || checkoutDate == null || note == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findUsingByRoomCode(fromRoomCode);
        if (invoiceDetail == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = invoiceDetail.getRoom();
        if (room.getStatus() != 2) {
            // Trạng thái phòng không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Date checkoutExpected = invoiceDetail.getCheckoutExpected();
        Date checkinExpected = invoiceDetail.getCheckinExpected();

        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate checkinExpectedDate = LocalDate.ofInstant(checkinExpected.toInstant(), ZoneId.systemDefault());
        LocalDate checkoutExpectedDate = LocalDate.ofInstant(checkoutExpected.toInstant(), ZoneId.systemDefault());
        LocalDate newCheckoutDate = LocalDate.ofInstant(checkoutDate.toInstant(), ZoneId.systemDefault());

        if (newCheckoutDate.isBefore(checkoutExpectedDate)) {
            // Ngày trả phòng phải sau ngày trả phòng hiện tại!
            throw new RuntimeException("{\"error\":\"Ngày trả phòng phải sau ngày trả phòng hiện tại!\"}");
        }

        Room oldRoom = invoiceDetail.getRoom();
        List<Room> rooms = roomService.findUnbookedRoomsByCheckinAndCheckout(invoiceDetail.getCheckinExpected(), Date.from(newCheckoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (rooms.size() <= 0) {
            // Không tìm thấy phòng hợp lệ
            throw new RuntimeException("{\"error\":\"Không tìm thấy phòng hợp lệ!\"}");
        }
        Room newRoom = rooms.stream().filter(r -> toRoomCode.equals(r.getCode())).findAny().orElse(null);
        if (newRoom == null) {
            throw new RuntimeException("Không tìm thấy room " + toRoomCode);
        }
        // Checkout phòng cũ - Tính tổng tiền phòng

        long days = ChronoUnit.DAYS.between(checkinExpectedDate, today);

        Double totalRoomFee = invoiceDetail.getRoomPrice() * days;

        invoiceDetail.setTotalRoomFee(totalRoomFee);
        invoiceDetail.setTotal(totalRoomFee);
        invoiceDetail.setCheckout(new Date());
        invoiceDetail.setStatus(2); // trạng thái đã sử dụng
        invoiceDetail.setNote("Đổi sang phòng " + toRoomCode + ". " + note);
        if (invoiceDetailService.update(invoiceDetail) == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // Trạng thái dọn phòng
        oldRoom.setStatus(6);
        if (roomService.update(oldRoom) == null) {
            // Cập nhật trạng thái phòng thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // Tạo InvoiceDetail cho phòng muốn đổi
        InvoiceDetail newInvoiceDetail = new InvoiceDetail();
        newInvoiceDetail.setRoom(newRoom);

        // Lấy giá phòng hiện tại
        List<RoomTypePromotion> roomTypePromotions = promotionRoomService.findAllCurrForRoomType();
        RoomTypePromotion roomTypePromotion = roomTypePromotions.stream().filter(rtp -> rtp.getRoomType().getCode().equals(newRoom.getRoomType().getCode())).findAny().orElse(null);
        if (roomTypePromotion != null) {
            Promotion promotion = roomTypePromotion.getPromotion();
            Double discount = newRoom.getRoomType().getPrice() / 100 * promotion.getPercent();
            if (discount >= promotion.getMaxDiscount()) {
                discount = promotion.getMaxDiscount();
            }
            newInvoiceDetail.setRoomPrice(newRoom.getRoomType().getPrice() - discount);
        } else {
            newInvoiceDetail.setRoomPrice(newRoom.getRoomType().getPrice());
        }

        newInvoiceDetail.setInvoice(invoiceDetail.getInvoice());
        newInvoiceDetail.setCheckin(new Date());
        newInvoiceDetail.setCheckinExpected(Date.from(LocalDate.now(ZoneId.systemDefault()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        newInvoiceDetail.setCheckoutExpected(Date.from(newCheckoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        newInvoiceDetail.setDeposit(0.0);
        newInvoiceDetail.setTotalRoomFee(0.0);
        newInvoiceDetail.setTotalServiceFee(0.0);
        newInvoiceDetail.setAdultSurcharge(0.0);
        newInvoiceDetail.setChildSurcharge(0.0);
        newInvoiceDetail.setOrtherSurcharge(0.0);
        newInvoiceDetail.setEarlyCheckinFee(0.0);
        newInvoiceDetail.setLateCheckoutFee(0.0);
        newInvoiceDetail.setTotal(0.0);
        newInvoiceDetail.setNote("");
        newInvoiceDetail.setStatus(1); // trạng thái đang sử dụng
        if (invoiceDetailService.create(newInvoiceDetail) == null) {
            // Tạo InvoiceDetail thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // Chuyển DS UsedService sang InvoiceDetail mới
        List<UsedService> usedServices = usedServiceService.findByInvoiceDetailId(invoiceDetail.getId());
        usedServices.forEach(usedService -> usedService.setInvoiceDetail(newInvoiceDetail));
        if (usedServiceService.updateAll(usedServices).size() != usedServices.size()) {
            // Cập nhật DS UsedService thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // Chuyển DS HostedAt sang InvoiceDetail mới
        List<HostedAt> hostedAts = hostedAtService.findByInvoiceDetailId(invoiceDetail.getId());
        hostedAts.forEach(hostedAt -> hostedAt.setInvoiceDetail(newInvoiceDetail));
        if (hostedAtService.updateAll(hostedAts).size() != hostedAts.size()) {
            // Cập nhật DS HostedAt thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        // Trạng thái đang ở
        newRoom.setStatus(2);
        if (roomService.update(newRoom) == null) {
            // Cập nhật trạng thái phòng thất bại
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void payment(String invoiceCode, String promotionCode, String paymentMethodCode, String note) {
        if (invoiceCode == null || paymentMethodCode == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Invoice invoice = invoiceService.findByCode(invoiceCode);
        if (invoice == null) {
            // Không tìm thấy Invoice
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() != 2) {
            // Trạng thái Invoice không hợp lệ
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        PaymentMethod paymentMethod = paymentMethodService.findByCode(paymentMethodCode);
        if (paymentMethod == null) {
            // Không tìm thấy PaymentMethod
            throw new RuntimeException("{\"error\":\"Vui lòng chọn phương thức thanh toán!\"}");
        }
        invoice.setPaymentMethod(paymentMethod);
        if (promotionCode != null) {
            Promotion promotion = promotionService.findByCode(promotionCode);
            if (promotion == null) {
                // Không tìm thấy Promotion
                throw new RuntimeException("{\"error\":\"Khuyến mại không hợp lệ!\"}");
            }
            if (!promotion.getStatus()) {
                // Promotion không đủ điều kiện sửa dụng Promotion
                throw new RuntimeException("{\"error\":\"Khuyến mại không hợp lệ!\"}");
            }
            if (!promotion.getType()) {
                // Promotion không đủ điều kiện sửa dụng Promotion
                throw new RuntimeException("{\"error\":\"Khuyến mại không hợp lệ!\"}");
            }
            if (promotion.getMinAmount() > invoice.getTotal()) {
                // Invoice không đủ điều kiện sửa dụng Promotion
                throw new RuntimeException("{\"error\":\"Hóa đơn không đủ điều kiện để sử dụng khuyến mại này!\"}");
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
            case "CASH":
                invoice.setPaidDate(new Date());
                invoice.setStatus(4);
                break;
            case "BANK":
            case "VNPAY":
            case "CREDIT":
                invoice.setStatus(3);
                break;
        }
        invoice.setNote(note);
        if (invoiceService.update(invoice) == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() == 4) {
            Customer customer = invoice.getBooking().getCustomer();
            if (customer.getCustomerType().getCode().equals("member")) {
                int count = invoiceService.countInvoiceByPeopleId(customer.getPeopleId());
                if (count >= 10) {
                    CustomerType customerType = new CustomerType();
                    customerType.setId(2);
                    customer.setCustomerType(customerType);
                    if (customerService.update(customer) == null) {
                        throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void confirmPayment(String invoiceCode) {
        if (invoiceCode == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Invoice invoice = invoiceService.findByCode(invoiceCode);
        if (invoice == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() != 3) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        invoice.setPaidDate(new Date());
        invoice.setStatus(4);
        if (invoiceService.update(invoice) == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() == 4) {
            Customer customer = invoice.getBooking().getCustomer();
            if (customer.getCustomerType().getCode().equals("member")) {
                int count = invoiceService.countInvoiceByPeopleId(customer.getPeopleId());
                if (count >= 10) {
                    CustomerType customerType = new CustomerType();
                    customerType.setId(2);
                    customer.setCustomerType(customerType);
                    if (customerService.update(customer) == null) {
                        throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public void updateInvoiceDetail(InvoiceDetailUpdateReq invoiceDetailUpdateReq) {
        if (invoiceDetailUpdateReq.getInvoiceDetailId() == null || invoiceDetailUpdateReq.getEarlyCheckinFee() == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        if (invoiceDetailUpdateReq.getLateCheckoutFee() == null || invoiceDetailUpdateReq.getNote() == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        if (invoiceDetailUpdateReq.getOrtherSurcharge() == null) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        if (invoiceDetailUpdateReq.getEarlyCheckinFee() < 0 || invoiceDetailUpdateReq.getLateCheckoutFee() < 0) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        if (invoiceDetailUpdateReq.getOrtherSurcharge() < 0) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        InvoiceDetail invoiceDetail = invoiceDetailService.findById(invoiceDetailUpdateReq.getInvoiceDetailId());
        if (invoiceDetail == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Invoice invoice = invoiceDetail.getInvoice();
        if (invoice.getStatus() != 1 && invoice.getStatus() != 2) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }

        String username = req.getAttribute("username").toString();

        InvoiceDetailHistory invoiceDetailHistory = new InvoiceDetailHistory();
        invoiceDetailHistory.setInvoiceDetail(invoiceDetail);
        invoiceDetailHistory.setCheckinExpected(invoiceDetail.getCheckinExpected());
        invoiceDetailHistory.setCheckoutExpected(invoiceDetail.getCheckoutExpected());
        invoiceDetailHistory.setRoomPrice(invoiceDetail.getRoomPrice());
        invoiceDetailHistory.setDeposit(invoiceDetail.getDeposit());
        invoiceDetailHistory.setOrtherSurcharge(invoiceDetail.getOrtherSurcharge());
        invoiceDetailHistory.setEarlyCheckinFee(invoiceDetail.getEarlyCheckinFee());
        invoiceDetailHistory.setLateCheckoutFee(invoiceDetail.getLateCheckoutFee());
        invoiceDetailHistory.setNote(invoiceDetail.getNote());
        invoiceDetailHistory.setCreatedBy(username);
        invoiceDetailHistory.setUpdateDate(new Date());
        if (invoiceDetailHistoryService.create(invoiceDetailHistory) == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        invoiceDetail.setOrtherSurcharge(invoiceDetailUpdateReq.getOrtherSurcharge());
        invoiceDetail.setEarlyCheckinFee(invoiceDetailUpdateReq.getEarlyCheckinFee());
        invoiceDetail.setLateCheckoutFee(invoiceDetailUpdateReq.getLateCheckoutFee());
        invoiceDetail.setNote(invoiceDetailUpdateReq.getNote());

        if (invoiceDetail.getStatus() == 2) {
            Room room = invoiceDetail.getRoom();
            RoomType roomType = room.getRoomType();

            // Tổng tiền dịch vụ
            Double totalServiceFee = invoiceDetail.getTotalServiceFee();

            // Phụ phí nhận / trả phòng
            Double lateCheckoutFee = invoiceDetail.getEarlyCheckinFee();
            Double earlyCheckinFee = invoiceDetail.getLateCheckoutFee();

            // Tính tổng tiền phòng
            Date checkinExpected = invoiceDetail.getCheckinExpected();
            Date checkoutExpected = invoiceDetail.getCheckoutExpected();

            LocalDate today = LocalDate.now(ZoneId.systemDefault());
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
            Double totalRoomFee = invoiceDetail.getRoomPrice() * days;

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

            Double totalAdultSurcharge = 0.0;
            Double totalChildSurcharge = 0.0;
            if (numAdults > roomType.getNumAdults()) {
                totalAdultSurcharge = roomType.getAdultSurcharge() * (numAdults - roomType.getNumAdults());
            }
            if (numChilds > roomType.getNumChilds()) {
                totalChildSurcharge = roomType.getChildSurcharge() * (numChilds - roomType.getNumChilds());
            }

            invoiceDetail.setAdultSurcharge(totalAdultSurcharge);
            invoiceDetail.setChildSurcharge(totalChildSurcharge);

            Double total = totalRoomFee + totalServiceFee + totalAdultSurcharge + totalChildSurcharge + invoiceDetail.getOrtherSurcharge() + lateCheckoutFee + earlyCheckinFee - invoiceDetail.getDeposit();

            invoiceDetail.setTotal(total);
        }
        if (invoiceDetailService.update(invoiceDetail) == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() == 2) {
            List<InvoiceDetail> invoiceDetails = invoiceDetailService.findByInvoiceCode(invoice.getCode());
            // Tổng tiền trả trước
            Double totalDeposit = invoiceDetails.stream().map(ivd -> ivd.getDeposit()).reduce(0.0, Double::sum);
            invoice.setTotalDeposit(totalDeposit);

            // Tổng tiền hoá đơn
            Double totalInvoice = invoiceDetails.stream().map(ivd -> ivd.getTotal()).reduce(0.0, Double::sum);
            invoice.setTotal(totalInvoice);
            if (invoiceService.update(invoice) == null) {
                throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class })
    public Invoice splitInvoice(InvoiceSplitReq invoiceSplitReq) {
        if (invoiceSplitReq.getInvoiceCode() == null || invoiceSplitReq.getRoomCodes().length == 0) {
            throw new RuntimeException("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Invoice invoice = invoiceService.findByCode(invoiceSplitReq.getInvoiceCode());
        if (invoice == null) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        if (invoice.getStatus() != 1 && invoice.getStatus() != 2) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.findByInvoiceCode(invoice.getCode());
        List<InvoiceDetail> filterInvoiceDetails = invoiceDetails.stream().filter(ivd -> ivd.getStatus() == InvoiceDetailStatus.COMPLETED.getCode()).collect(Collectors.toList());
        if (invoiceDetails.size() <= 0 || filterInvoiceDetails.size() <= 0) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        List<String> roomCodes = Arrays.asList(invoiceSplitReq.getRoomCodes());
        List<InvoiceDetail> splitInvoiceDetails = filterInvoiceDetails.stream().filter(ivd -> roomCodes.contains(ivd.getRoom().getCode())).collect(Collectors.toList());
        if (splitInvoiceDetails.size() <= 0) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Invoice newInvoice = new Invoice();
        newInvoice.setBooking(invoice.getBooking());
        newInvoice.setCreatedDate(new Date());

        // Tổng tiền trả trước
        Double totalDeposit = splitInvoiceDetails.stream().map(ivd -> ivd.getDeposit()).reduce(0.0, Double::sum);
        newInvoice.setTotalDeposit(totalDeposit);

        // Tổng tiền hoá đơn
        Double totalInvoice = splitInvoiceDetails.stream().map(ivd -> ivd.getTotal()).reduce(0.0, Double::sum);
        newInvoice.setTotal(totalInvoice);

        newInvoice.setDiscountMoney(0.0);
        newInvoice.setTotalPayment(0.0);
        invoice.setNote("");
        newInvoice.setStatus(2); // trạng thái chờ

        String username = req.getAttribute("username").toString();

        Account account = accountService.findByUsernameAndActivate(username);
        if (account == null) {
            // Chưa đăng nhập!
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        newInvoice.setAccount(account);
        if (invoiceService.create(newInvoice) == null) {
            // lỗi tạo invoice
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        splitInvoiceDetails.forEach(ivd -> ivd.setInvoice(newInvoice));
        if (invoiceDetailService.updateAll(splitInvoiceDetails).size() != splitInvoiceDetails.size()) {
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        return newInvoice;
    }

    @Override
    public PeopleInRoomResp peopleInRoom(Integer invoiceDetailId) {
        InvoiceDetail invoiceDetail = invoiceDetailService.findById(invoiceDetailId);
        if (invoiceDetail == null) {
            // Không tìm thấy InvoiceDetail của room
            throw new RuntimeException("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
        }
        Room room = invoiceDetail.getRoom();
        RoomType roomType = room.getRoomType();
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
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

        Double totalAdultSurcharge = 0.0;
        Double totalChildSurcharge = 0.0;
        if (numAdults > roomType.getNumAdults()) {
            totalAdultSurcharge = roomType.getAdultSurcharge() * (numAdults - roomType.getNumAdults());
        }
        if (numChilds > roomType.getNumChilds()) {
            totalChildSurcharge = roomType.getChildSurcharge() * (numChilds - roomType.getNumChilds());
        }

        PeopleInRoomResp peopleInRoomResp = new PeopleInRoomResp();
        peopleInRoomResp.setNumAdults((int) numAdults);
        peopleInRoomResp.setNumChilds((int) numChilds);
        peopleInRoomResp.setAdultSurcharge(totalAdultSurcharge);
        peopleInRoomResp.setChildSurcharge(totalChildSurcharge);
        return peopleInRoomResp;
    }

    @Override
    public List<RoomUnbookedResp> findAllRoomUnbooked(Date checkinDate, Date checkoutDate) {
        List<RoomTypePromotion> roomTypePromotions = promotionRoomService.findAllCurrForRoomType();
        List<Room> rooms = roomService.findUnbookedRoomsByCheckinAndCheckout(checkinDate, checkoutDate);
        List<RoomUnbookedResp> roomUnbookedResps = new ArrayList<>();
        for (Room room : rooms) {
            RoomTypePromotion roomTypePromotion = roomTypePromotions.stream().filter(rtp -> rtp.getRoomType().getCode().equals(room.getRoomType().getCode())).findAny().orElse(null);
            RoomUnbookedResp roomUnbookedResp = new RoomUnbookedResp();
            roomUnbookedResp.setCode(room.getCode());
            roomUnbookedResp.setRoomType(room.getRoomType().getName());
            roomUnbookedResp.setArea(room.getRoomType().getArea());
            roomUnbookedResp.setPrice(room.getRoomType().getPrice());
            if (roomTypePromotion != null) {
                Promotion promotion = roomTypePromotion.getPromotion();
                Double discount = room.getRoomType().getPrice() / 100 * promotion.getPercent();
                if (discount >= promotion.getMaxDiscount()) {
                    discount = promotion.getMaxDiscount();
                }
                roomUnbookedResp.setNewPrice(room.getRoomType().getPrice() - discount);
            }
            roomUnbookedResps.add(roomUnbookedResp);
        }
        return roomUnbookedResps;
    }

}
