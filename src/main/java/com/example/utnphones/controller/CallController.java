package com.example.utnphones.controller;

import com.example.utnphones.dto.CallRequestDto;
import com.example.utnphones.dto.CallResponseDto;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Call;
import com.example.utnphones.model.Client;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.CallService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/api/calls")
public class CallController {

    private final CallService callService;
    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @Autowired
    public CallController(CallService callService, AccountService accountService, ModelMapper modelMapper) {
        this.callService = callService;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Call>> getAllCalls(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Call> calls = callService.getAllCalls(pageable);

        if(!calls.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(calls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Call> getCallById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(callService.getCallById(id));
    }

    @GetMapping(value = "/phoneOrigin/{phoneOrigin}")
    public ResponseEntity<Page<Call>> getCallByPhoneOrigin(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @PathVariable String phoneOrigin){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Call> calls = callService.getCallsMadeByNumber(pageable, phoneOrigin);

        if(!calls.hasContent()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calls);
    }

    @PostMapping("/")
    public ResponseEntity<CallResponseDto> saveNewCall(@RequestBody @Valid final CallRequestDto callRequest) throws NotFoundEntityException {
        Client phoneOrigin = accountService.getClientByPhoneNumber(callRequest.getOrigin());
        Client phoneDestination = accountService.getClientByPhoneNumber(callRequest.getDestination());

        Call newCall = callService.saveNewCall(new Call(
                LocalDateTime.parse(callRequest.getDatetime(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                callRequest.getDuration(),
                phoneOrigin,
                phoneDestination
        ));


        CallResponseDto response = modelMapper.map(newCall, CallResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
