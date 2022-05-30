package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundException;
import com.example.utnphones.model.City;
import com.example.utnphones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAllCities(Pageable pageable){
        return cityRepository.findAll(pageable);
    }

    public City getCityById(Long id) throws NotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City"));
    }

    public City saveNewCity(final City newCity){
        return cityRepository.save(newCity);
    }
}
