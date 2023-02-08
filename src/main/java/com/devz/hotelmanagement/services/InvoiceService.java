package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.Invoice;

public interface InvoiceService {
	List<Invoice> findAll();

	Invoice findById(int id);

	Invoice create(Invoice invoice);

	Invoice upadte(Invoice invoice);
}
