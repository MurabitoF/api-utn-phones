package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.City;
import com.example.utnphones.repository.CityRepository;
import com.example.utnphones.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAllCities(Pageable pageable){
        return cityRepository.findAll(pageable);
    }

    public City getCityById(Long id) throws NotFoundEntityException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("City"));
    }

    public City saveNewCity(final City newCity){
        return cityRepository.save(newCity);
    }
}
