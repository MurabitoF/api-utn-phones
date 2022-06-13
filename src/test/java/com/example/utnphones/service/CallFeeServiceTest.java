package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.CallFeeRepository;
import com.example.utnphones.service.impl.CallFeeServiceImpl;
import com.example.utnphones.utils.MockModels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CallFeeServiceTest {

    @InjectMocks
    private CallFeeServiceImpl callFeeService;

    @Mock
    private CallFeeRepository callFeeRepository;

    @Test
    void getAllCallsFeesTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<CallFee> aCallFeePage = aCallFeePage();

        Mockito.when(callFeeRepository.findAll(pageable)).thenReturn(aCallFeePage);

        Page<CallFee> response = callFeeService.getAllCallFees(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aCallFeePage, response);
    }

    @Test
    void getCallFeeByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFee aCallFee = aCallFee();
        aCallFee.setCallFeeId(1L);

        Mockito.when(callFeeRepository.findById(1L)).thenReturn(Optional.of(aCallFee));

        CallFee response = callFeeService.getCallFeeById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(1L, response.getCallFeeId());
    }

    @Test
    void getCallFeeByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(callFeeRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { callFeeService.getCallFeeById(impossibleId); });
    }

    @Test
    void saveNewCallFeeTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFee aCallFeeNoId = aCallFee();
        final CallFee aCallFee = aCallFee();
        aCallFee.setCallFeeId(1L);

        Mockito.when(callFeeRepository.save(aCallFeeNoId)).thenReturn(aCallFee);

        CallFee response = callFeeService.saveNewCallFee(aCallFeeNoId);

        assertNotNull(response, "Should be not null");
        assertEquals(aCallFee, response);
    }
}
