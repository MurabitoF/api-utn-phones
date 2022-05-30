package com.example.utnphones.controller;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Province;
import com.example.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Province>> getAllProvinces(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Province> provinces = provinceService.getAllProvinces(pageable);

        if(!provinces.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable final Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(provinceService.getProvinceById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Province> saveNewProvince(@RequestBody final Province province){
        return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.saveNewProvince(province));
    }
}
