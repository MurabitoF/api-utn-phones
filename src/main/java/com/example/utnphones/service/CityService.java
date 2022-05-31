package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    public Page<City> getAllCities(Pageable pageable);

    public City getCityById(Long id) throws NotFoundEntityException;

    public City saveNewCity(final City newCity);

}
