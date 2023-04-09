package com.devz.hotelmanagement.services;

import java.io.IOException;

public interface VNPayService {

    String paymentUrl(String code, int amount, String ip) throws IOException;

}
