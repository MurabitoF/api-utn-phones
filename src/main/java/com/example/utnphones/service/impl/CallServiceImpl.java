package com.example.utnphones.service.impl;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Call;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.CallRepository;
import com.example.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public Page<Call> getAllCalls(Pageable pageable) {
        return callRepository.findAll(pageable);
    }

    @Override
    public Call getCallById(Long id) throws NotFoundEntityException {
        return callRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Call"));
    }

    @Override
    public Page<Call> getCallsMadeByNumber(Pageable pageable, Client phoneOrigen, LocalDateTime from, LocalDateTime until) {
        return callRepository.findAllByPhoneOriginAndCallDateBetween(pageable, phoneOrigen, from, until);
    }

    @Override
    @Transactional
    public Call saveNewCall(Call newCall) {
        Call savedCall = callRepository.save(newCall);
        entityManager.refresh(savedCall);
        return savedCall;
    }
}
