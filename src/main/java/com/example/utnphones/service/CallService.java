package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CallService {
    public Page<Call> getAllCalls(Pageable pageable);

    public Call getCallById(Long id) throws NotFoundEntityException;

    public Page<Call> getCallsMadeByNumber(Pageable pageable, String phoneOrigen);

    public Call saveNewCall(final Call newCall);
}
