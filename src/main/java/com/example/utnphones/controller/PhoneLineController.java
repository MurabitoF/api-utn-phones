package com.example.utnphones.controller;

import com.example.utnphones.model.PhoneLine;
import com.example.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phoneLines")
public class PhoneLineController {

    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<PhoneLine>> getAllPhoneLines(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<PhoneLine> phoneLines = phoneLineService.getAllPhoneLines(pageable);

        if(!phoneLines.hasContent()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(phoneLines);
    }
}
