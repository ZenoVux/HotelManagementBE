package com.devz.hotelmanagement.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devz.hotelmanagement.entities.Image;
import com.devz.hotelmanagement.repositories.ImageRepository;
import com.devz.hotelmanagement.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepo;

    @Override
    public List<Image> findAll() {
        return imageRepo.findAll();
    }

    @Override
    public Image findById(int id) {
        Optional<Image> optional = imageRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Image findByCode(String code) {
        return null;
    }

    @Override
    public Image create(Image image) {
        image.setId(null);
        return imageRepo.save(image);
    }

    @Override
    public Image update(Image image) {
        return imageRepo.save(image);
    }

	@Override
	public void deleteById(Integer id) {
		imageRepo.deleteById(id);
	}

}
