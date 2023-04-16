package com.devz.hotelmanagement.rest.controllers;

import com.devz.hotelmanagement.entities.Account;
import com.devz.hotelmanagement.models.AccountReq;
import com.devz.hotelmanagement.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Account account = accountService.findByUsername(username.get());
        if (account != null) {
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Tên đăng nhập không tồn tại!\"}");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountReq accountReq) {
        Account account = new Account();
        account.setFullName(accountReq.getFullName());
        if (accountService.findByUsername(accountReq.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Tên đăng nhập đã tồn tại!\"}");
        }
        if (!accountReq.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Mật khẩu sai định dạng. Độ dài tối thiểu 8 ký tự, có ít nhất 1 chữ in hoa và 1 số.\"}");
        }
        if (!accountReq.getPassword().equals(accountReq.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Xác nhận Mật khẩu phải trùng mới Mật khẩu!\"}");
        }
        if (!accountReq.getEmail().matches("^\\w+@\\w+(\\.\\w+){1,2}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Email sai định dạng. email@example.com\"}");
        }
        account.setUsername(accountReq.getUsername());
        account.setPassword(passwordEncoder.encode(accountReq.getPassword()));
        account.setPhoneNumber(accountReq.getPhoneNumber());
        account.setEmail(accountReq.getEmail());
        account.setStatus(accountReq.getStatus());
        if (accountService.create(account) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AccountReq accountReq) {
        Account account = accountService.findByUsername(accountReq.getUsername());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Tên đăng nhập không tồn tại!\"}");
        }
        if (!accountReq.getPassword().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Mật khẩu sai định dạng. Độ dài tối thiểu 8 ký tự, có ít nhất 1 chữ in hoa và 1 số.\"}");
        }
        if (!accountReq.getPassword().equals(accountReq.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Xác nhận Mật khẩu phải trùng mới Mật khẩu!\"}");
        }
        if (!accountReq.getEmail().matches("^\\w+@\\w+(\\.\\w+){1,2}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Email sai định dạng. email@example.com\"}");
        }
        account.setFullName(accountReq.getFullName());
        account.setPassword(passwordEncoder.encode(accountReq.getPassword()));
        account.setPhoneNumber(accountReq.getPhoneNumber());
        account.setEmail(accountReq.getEmail());
        if (accountService.update(account) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(account);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
    }

    @PostMapping("/activate/{username}")
    public ResponseEntity<?> activate(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Account account = accountService.findByUsername(username.get());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Tài khoản không tồn tại!\"}");
        }
        String currusername = req.getAttribute("username").toString();
        if (currusername.equals(account.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Không thể thay đổi trang thái tài khoản đang đăng nhập!\"}");
        }
        account.setStatus(true);
        if (accountService.update(account) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
    }

    @PostMapping("/deactivate/{username}")
    public ResponseEntity<?> deactivate(@PathVariable("username") Optional<String> username) {
        if (!username.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Dữ liệu không hợp lệ!\"}");
        }
        Account account = accountService.findByUsername(username.get());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Tài khoản không tồn tại!\"}");
        }
        String currusername = req.getAttribute("username").toString();
        if (currusername.equals(account.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Không thể thay đổi trang thái tài khoản đang đăng nhập!\"}");
        }
        account.setStatus(false);
        if (accountService.update(account) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Có lỗi xảy ra vui lòng thử lại!\"}");
    }

}
