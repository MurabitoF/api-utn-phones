package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.repository.CallFeeRepository;
import com.example.utnphones.service.CallFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CallFeeServiceImpl implements CallFeeService {

    private final CallFeeRepository callFeeRepository;

    @Autowired
    public CallFeeServiceImpl(CallFeeRepository callFeeRepository) {
        this.callFeeRepository = callFeeRepository;
    }

    @Override
    public Page<CallFee> getAllCallFees(Pageable pageable) {
        return callFeeRepository.findAll(pageable);
    }

    @Override
    public CallFee getCallFeeById(Long id) throws NotFoundEntityException {
        return callFeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Call Fee"));
    }

    @Override
    public CallFee saveNewCallFee(CallFee newCallFee) {
        return callFeeRepository.save(newCallFee);
    }
}
