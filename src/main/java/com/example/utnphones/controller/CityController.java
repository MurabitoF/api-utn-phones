package com.example.utnphones.controller;

import com.example.utnphones.dto.CityRequestDto;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.City;
import com.example.utnphones.model.Province;
import com.example.utnphones.service.CityService;
import com.example.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final ProvinceService provinceService;
    private final CityService cityService;

    @Autowired
    public CityController(ProvinceService provinceService, CityService cityService) {
        this.provinceService = provinceService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<City>> getAllCities(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<City> cities = cityService.getAllCities(pageable);

        if (!cities.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable final Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @PostMapping("/")
    public ResponseEntity<City> saveNewCity(@RequestBody final CityRequestDto city) throws NotFoundEntityException {
        Province province = provinceService.getProvinceById(city.getProvinceId());

        City newCity = City.builder()
                .cityName(city.getCityName())
                .areaCode(city.getAreaCode())
                .province(province)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.saveNewCity(newCity));
    }
}
