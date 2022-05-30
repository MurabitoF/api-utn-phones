package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Province;
import com.example.utnphones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Page<Province> getAllProvinces(Pageable pageable){
        return provinceRepository.findAll(pageable);
    }

    public Province getProvinceById(Long id) throws NotFoundEntityException {
        return provinceRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Province"));
    }

    public Province saveNewProvince(final Province newProvince){
        return provinceRepository.save(newProvince);
    }
}
