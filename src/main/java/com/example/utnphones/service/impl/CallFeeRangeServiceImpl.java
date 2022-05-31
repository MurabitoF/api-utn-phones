package com.example.utnphones.service.impl;

import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import com.example.utnphones.repository.CallFeeRangeRepository;
import com.example.utnphones.service.CallFeeRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallFeeRangeServiceImpl implements CallFeeRangeService {

    private final CallFeeRangeRepository callFeeRangeRepository;

    @Autowired
    public CallFeeRangeServiceImpl(CallFeeRangeRepository callFeeRangeRepository) {
        this.callFeeRangeRepository = callFeeRangeRepository;
    }

    @Override
    public List<CallFeeRange> getAllRangesByCallFee(CallFee callFee) {
        return callFeeRangeRepository.findByCallFee(callFee);
    }

    @Override
    public CallFeeRange saveNewRange(CallFeeRange newCallFeeRange) {
        return callFeeRangeRepository.save(newCallFeeRange);
    }
}
