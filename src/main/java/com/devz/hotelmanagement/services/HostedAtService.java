package com.devz.hotelmanagement.services;

import java.util.List;

import com.devz.hotelmanagement.entities.HostedAt;

public interface HostedAtService {
	List<HostedAt> findAll();

	HostedAt findById(int id);

	HostedAt create(HostedAt hostedAt);

	HostedAt update(HostedAt hostedAt);
}
