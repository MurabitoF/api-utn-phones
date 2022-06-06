package com.example.utnphones.service;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;

import java.time.LocalTime;
import java.util.List;

public interface CallFeeRangeService {

    public List<CallFeeRange> getAllRangesByCallFee(CallFee callFee);

    public CallFeeRange getCallFeeRangeById(Long id) throws NotFoundEntityException;

    public CallFeeRange saveNewRange(CallFeeRange newCallFeeRange) throws EntityExitstExeption;

    public List<CallFeeRange> findRangeByTime(CallFee callFee, LocalTime startAt, LocalTime endAt);

    public CallFeeRange updateCallFeeRange(Long  id, CallFeeRange callFeeRange) throws NotFoundEntityException;

    public void deleteCallFeeRange(Long id) throws NotFoundEntityException;
}
