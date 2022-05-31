package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProvinceService {

    public Page<Province> getAllProvinces(Pageable pageable);
    public Province getProvinceById(Long id) throws NotFoundEntityException;
    public Province saveNewProvince(final Province newProvince);

}
