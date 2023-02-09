package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.HostedAt;
import com.devz.hotelmanagement.repositories.HostedAtRepository;
import com.devz.hotelmanagement.services.HostedAtService;

@Service
public class HostedAtServiceImpl implements HostedAtService {

	@Autowired
	HostedAtRepository hostedAtRepo;
	
	@Override
	public List<HostedAt> findAll() {
		return hostedAtRepo.findAll();
	}

	@Override
	public HostedAt findById(int id) {
		Optional<HostedAt> optional = hostedAtRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public HostedAt create(HostedAt hostedAt) {
		hostedAt.setId(null);
		return hostedAtRepo.save(hostedAt);
	}

	@Override
	public HostedAt update(HostedAt hostedAt) {
		return hostedAtRepo.save(hostedAt);
	}

}
