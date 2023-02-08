package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.InvoiceType;

public interface InvoiceTypeService {
	List<InvoiceType> findAll();

	InvoiceType findById(int id);

	InvoiceType create(InvoiceType invoiceType);

	InvoiceType upadte(InvoiceType invoiceType);
}
