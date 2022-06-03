package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.PhoneLine;
import com.example.utnphones.repository.PhoneLineRepository;
import com.example.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneLineServiceImpl implements PhoneLineService {

    private final PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineServiceImpl(PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    @Override
    public Page<PhoneLine> getAllPhoneLines(Pageable pageable) {
        return phoneLineRepository.findAll(pageable);
    }

    @Override
    public PhoneLine getPhoneLineByPhoneNumber(String phoneNumber) throws NotFoundEntityException {
        return phoneLineRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundEntityException("Phone line"));
    }

    @Override
    public PhoneLine saveNewPhoneLine(PhoneLine newPhoneLine) {
        return phoneLineRepository.save(newPhoneLine);
    }
}
