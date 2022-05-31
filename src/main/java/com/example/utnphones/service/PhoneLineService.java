package com.example.utnphones.service;

import com.example.utnphones.model.PhoneLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneLineService {

    public Page<PhoneLine> getAllPhoneLines(Pageable pageable);
    public PhoneLine saveNewPhoneLine(PhoneLine newPhoneLine);
}
