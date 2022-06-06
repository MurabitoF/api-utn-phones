package com.example.utnphones.controller;

import com.example.utnphones.dto.CallFeeRangeRequestDto;
import com.example.utnphones.dto.CallFeeRequestDto;
import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import com.example.utnphones.model.City;
import com.example.utnphones.service.CallFeeRangeService;
import com.example.utnphones.service.CallFeeService;
import com.example.utnphones.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/callsFees")
public class CallFeeController {

    private final CallFeeService callFeeService;
    private final CallFeeRangeService callFeeRangeService;
    private final CityService cityService;

    @Autowired
    public CallFeeController(CallFeeService callFeeService, CallFeeRangeService callFeeRangeService, CityService cityService) {
        this.callFeeService = callFeeService;
        this.callFeeRangeService = callFeeRangeService;
        this.cityService = cityService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<CallFee>> getAllCallFees(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<CallFee> callFees = callFeeService.getAllCallFees(pageable);

        if (!callFees.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(callFees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CallFee> getCallFeeById(@PathVariable Long id) throws NotFoundEntityException {
        return ResponseEntity.ok(callFeeService.getCallFeeById(id));
    }

    @GetMapping("/{id}/ranges/")
    public ResponseEntity<List<CallFeeRange>> getAllRangesFromCallFee(@PathVariable Long id) throws NotFoundEntityException {
        CallFee callFee = callFeeService.getCallFeeById(id);
        List<CallFeeRange> callFeeRanges = callFeeRangeService.getAllRangesByCallFee(callFee);

        if(callFeeRanges.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(callFeeRanges);
    }

    @PostMapping("/")
    public ResponseEntity<CallFee> saveNewCallFee(@RequestBody @Valid final CallFeeRequestDto callFeeRequest) throws NotFoundEntityException, EntityExitstExeption {
        City cityOrigin = cityService.getCityById(callFeeRequest.getCityOrigin());
        City cityDestination = cityService.getCityById(callFeeRequest.getCityDestination());

        CallFee newCallFee = CallFee.builder()
                .cityOrigin(cityOrigin)
                .cityDestination(cityDestination)
                .price(callFeeRequest.getPrice())
                .build();

        CallFee savedCallFee = callFeeService.saveNewCallFee(newCallFee);

        CallFeeRange newCallFeeRange = CallFeeRange.builder()
                .callFee(savedCallFee)
                .startAt(LocalTime.parse(callFeeRequest.getStartAt()))
                .endAt(LocalTime.parse(callFeeRequest.getEndAt()))
                .build();

        CallFeeRange callfeeRange = callFeeRangeService.saveNewRange(newCallFeeRange);

        newCallFee.getCallFeeRange().add(callfeeRange);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCallFee);
    }

    @PostMapping("/{id}/ranges/")
    public ResponseEntity<CallFeeRange> saveNewRangeToCallFee(@PathVariable Long id, @RequestBody final CallFeeRangeRequestDto callFeeRangeRequest) throws NotFoundEntityException, EntityExitstExeption {
        CallFee callFee = callFeeService.getCallFeeById(id);

        CallFeeRange newCallFeeRange = CallFeeRange.builder()
                .startAt(LocalTime.parse(callFeeRangeRequest.getStartAt()))
                .endAt(LocalTime.parse(callFeeRangeRequest.getEndAt()))
                .callFee(callFee)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(callFeeRangeService.saveNewRange(newCallFeeRange));
    }

    @PutMapping("/{callFeeId}/ranges/{rangeId}/")
    public ResponseEntity<CallFeeRange> updateRange(@PathVariable Long callFeeId, @PathVariable Long rangeId, @Valid @RequestBody CallFeeRangeRequestDto callFeeRangeRequest) throws NotFoundEntityException {
        callFeeService.getCallFeeById(callFeeId);
        CallFeeRange callFeeRange = CallFeeRange.builder()
                .startAt(LocalTime.parse(callFeeRangeRequest.getStartAt()))
                .endAt(LocalTime.parse(callFeeRangeRequest.getEndAt()))
                .build();

        return ResponseEntity.ok(callFeeRangeService.updateCallFeeRange(rangeId, callFeeRange));
    }

    @DeleteMapping("/{callFeeId}/ranges/{rangeId}/")
    public ResponseEntity<CallFeeRange> deleteCallFeeRange(@PathVariable Long callFeeId, @PathVariable Long rangeId) throws NotFoundEntityException {
        callFeeService.getCallFeeById(callFeeId);
        callFeeRangeService.deleteCallFeeRange(rangeId);

        return ResponseEntity.noContent().build();
    }
}
