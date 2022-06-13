package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Call;
import com.example.utnphones.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CallService {
    public Page<Call> getAllCalls(Pageable pageable);

    public Call getCallById(Long id) throws NotFoundEntityException;

    public Page<Call> getCallsMadeByNumber(Pageable pageable, Client phoneOrigen, LocalDateTime from, LocalDateTime until);

    public Call saveNewCall(final Call newCall);
}
