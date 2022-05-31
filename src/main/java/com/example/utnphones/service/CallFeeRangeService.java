package com.example.utnphones.service;

import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;

import java.util.List;

public interface CallFeeRangeService {

    public List<CallFeeRange> getAllRangesByCallFee(CallFee callFee);

    public CallFeeRange saveNewRange(CallFeeRange newCallFeeRange);
}
