package com.devz.hotelmanagement.controllers;

import com.devz.hotelmanagement.entities.Invoice;
import com.devz.hotelmanagement.services.InvoiceService;
import com.devz.hotelmanagement.services.VNPayService;
import com.devz.hotelmanagement.utilities.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private VNPayService vnPayService;
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/invoice/{code}")
    public void invoice(HttpServletRequest req, HttpServletResponse resp, @PathVariable("code") String code) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Invoice invoice = invoiceService.findByCode(code);
        if (invoice == null) {
            resp.getWriter().println("<script type=\"text/javascript\">");
            resp.getWriter().println("alert('Hoá đơn không tồn tại!');");
            resp.getWriter().println("window.close();");
            resp.getWriter().println("</script>");
            return;
        }
        if (invoice.getStatus() != 3) {
            resp.getWriter().println("<script type=\"text/javascript\">");
            resp.getWriter().println("alert('Hoá đơn không hợp lệ!');");
            resp.getWriter().println("window.close();");
            resp.getWriter().println("</script>");
            return;
        }
        String ip = VNPayUtil.getIpAddress(req);

        String paymentUrl = vnPayService.paymentUrl(code + "-" + VNPayUtil.getRandomNumber(8), invoice.getTotalPayment().intValue(), ip);
        // chuyển hướng trang sang VNPay
        resp.sendRedirect(paymentUrl);
    }

    @GetMapping("/vnpay/return")
    public void vnpayReturn(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode(params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(req.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");

        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPayUtil.hashAllFields(fields);

        String vnp_TxnRef = req.getParameter("vnp_TxnRef");
        String vnp_Amount = req.getParameter("vnp_Amount");
		String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
		String vnp_BankCode = req.getParameter("vnp_BankCode");
		String vnp_TransactionNo = req.getParameter("vnp_TransactionNo");
		String vnp_PayDate = req.getParameter("vnp_PayDate");
        String vnp_TransactionStatus = req.getParameter("vnp_TransactionStatus");

        if (signValue.equals(vnp_SecureHash)) {
            String code = vnp_TxnRef.split("-")[0];
            Invoice invoice = invoiceService.findByCode(code);
            if (invoice == null) {
                resp.sendRedirect("http://127.0.0.1:5500/#!/invoices");
                return;
            }
            if ("00".equals(req.getParameter("vnp_TransactionStatus"))) {
                System.out.println("Success");
                invoice.setPaidDate(new Date());
                invoice.setStatus(4);
                invoiceService.update(invoice);
            } else {
                System.out.println("Failed");
            }
            resp.sendRedirect("http://127.0.0.1:5500/#!/invoices/" + code);
        } else {
            System.out.println("invalid signature");
            resp.sendRedirect("http://127.0.0.1:5500/#!/invoices");
        }
    }

}
