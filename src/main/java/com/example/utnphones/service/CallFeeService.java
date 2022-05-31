package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CallFeeService {

    public Page<CallFee> getAllCallFees(Pageable pageable);
    public CallFee getCallFeeById(Long id) throws NotFoundEntityException;
    public CallFee saveNewCallFee(CallFee newCallFee);

}
