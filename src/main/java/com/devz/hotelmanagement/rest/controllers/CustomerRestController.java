package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Booking;
import com.devz.hotelmanagement.entities.Customer;
import com.devz.hotelmanagement.entities.CustomerType;
import com.devz.hotelmanagement.services.BookingService;
import com.devz.hotelmanagement.services.CustomerService;
import com.devz.hotelmanagement.services.StorageService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/in-use")
    public List<Customer> getCustomerInUse() {
        List<Customer> customers = customerService.getCustomerInUse();
        return customers;
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
        }
        return customer;
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/create-member")
    public Customer createMember(@RequestBody Customer customer) {
        CustomerType customerType = new CustomerType();
        customerType.setId(1);
        customer.setCustomerType(customerType);
        return customerService.create(customer);
    }

    @PutMapping
    public Customer update(@RequestBody Customer customer) {
        return customerService.update(customer);
    }

    @GetMapping("/search-by-people-id/{peopleId}")
    public ResponseEntity<Customer> searchByPeopleId(@PathVariable("peopleId") String peopleId) {
        Customer customer = customerService.searchByPeopleId(peopleId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history-booking/{id}")
    public List<Booking> getHistoryBooking(@PathVariable("id") Integer id) {
        List<Booking> bookings = bookingService.getBookingByCusId(id);
        return bookings;
    }

}
