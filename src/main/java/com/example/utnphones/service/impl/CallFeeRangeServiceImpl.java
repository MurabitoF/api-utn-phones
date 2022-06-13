package com.example.utnphones.service.impl;

import com.example.utnphones.dto.CallFeeRangeRequestDto;
import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import com.example.utnphones.repository.CallFeeRangeRepository;
import com.example.utnphones.service.CallFeeRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class CallFeeRangeServiceImpl implements CallFeeRangeService {

    private final CallFeeRangeRepository callFeeRangeRepository;

    @Autowired
    public CallFeeRangeServiceImpl(CallFeeRangeRepository callFeeRangeRepository) {
        this.callFeeRangeRepository = callFeeRangeRepository;
    }

    @Override
    public List<CallFeeRange> getAllRangesByCallFee(CallFee callFee) {
        return callFeeRangeRepository.findAllByCallFee(callFee);
    }

    @Override
    public CallFeeRange getCallFeeRangeById(Long id) throws NotFoundEntityException {
        return callFeeRangeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Call fee range"));
    }

    @Override
    public CallFeeRange saveNewRange(CallFeeRange newCallFeeRange) throws EntityExitstExeption {
        List<CallFeeRange> ranges = this.findRangeByTime(newCallFeeRange.getCallFee(), newCallFeeRange.getStartAt(), newCallFeeRange.getEndAt());
        if(!ranges.isEmpty()){
            throw new EntityExitstExeption("Call fee range");
        }

        return callFeeRangeRepository.save(newCallFeeRange);
    }

    @Override
    public List<CallFeeRange> findRangeByTime(CallFee callFee, LocalTime startAt, LocalTime endAt) {
        return callFeeRangeRepository.findAllByCallFeeAndStartAtIsBetweenOrEndAtIsBetween(callFee, startAt, endAt);
    }

    @Override
    public CallFeeRange updateCallFeeRange(Long id, CallFeeRange callFeeRange) throws NotFoundEntityException {
        CallFeeRange updatedCallFeeRange = this.getCallFeeRangeById(id);

        if(!Objects.equals(callFeeRange.getStartAt(), updatedCallFeeRange.getStartAt())){
            updatedCallFeeRange.setStartAt(callFeeRange.getStartAt());
        }

        if(!Objects.equals(callFeeRange.getEndAt(), updatedCallFeeRange.getEndAt())){
            updatedCallFeeRange.setEndAt(callFeeRange.getEndAt());
        }

        return callFeeRangeRepository.save(updatedCallFeeRange);
    }

    @Override
    public void deleteCallFeeRange(Long id) throws NotFoundEntityException {
        CallFeeRange callFeeRange = this.getCallFeeRangeById(id);
        callFeeRangeRepository.delete(callFeeRange);
    }
}
