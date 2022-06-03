package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.PhoneLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PhoneLineService {

    public Page<PhoneLine> getAllPhoneLines(Pageable pageable);
    public PhoneLine getPhoneLineByPhoneNumber(String phoneNumber) throws NotFoundEntityException;
    public PhoneLine saveNewPhoneLine(PhoneLine newPhoneLine);
}
