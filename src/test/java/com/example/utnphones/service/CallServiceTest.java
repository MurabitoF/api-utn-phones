package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Call;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.CallRepository;
import com.example.utnphones.service.impl.CallServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CallServiceTest {

    @InjectMocks
    private CallServiceImpl callService;

    @Mock
    private CallRepository callRepository;

    @MockBean
    private EntityManager entityManager;

    @Test
    void getAllCallsTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Call> aCallPage = aCallPage();

        Mockito.when(callRepository.findAll(pageable)).thenReturn(aCallPage);

        final Page<Call> response = callService.getAllCalls(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aCallPage, response);
    }

    @Test
    void getCallByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Call aCall = aCall();
        aCall.setCallId(1L);

        Mockito.when(callRepository.findById(1L)).thenReturn(Optional.of(aCall));

        final Call response = callService.getCallById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(1L, response.getCallId());
    }

    @Test
    void getCallByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(callRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> {
            callService.getCallById(impossibleId);
        });
    }

    @Test
    void getCallsMadeByNumberTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Call> aCallPage = aCallPage();
        final Client aPhoneNumber = aClient();
        final LocalDateTime aLocalDateTime = LocalDateTime.now();

        Mockito.when(callRepository.findAllByPhoneOriginAndCallDateBetween(pageable, aPhoneNumber, aLocalDateTime, aLocalDateTime)).thenReturn(aCallPage);

        final Page<Call> response = callService.getCallsMadeByNumber(pageable, aPhoneNumber, aLocalDateTime, aLocalDateTime);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aCallPage, response);
    }

//    @Test
//    void saveNewCallTest() {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        final Call aCallNoId = aCall();
//        final Call aCall = aCall();
//        aCall.setCallId(1L);
//        aCall.setCallFee(aCallFee());
//        aCall.setCallFeeRange(aCallFeeRange());
//        aCall.setTotal(100D);
//
//        Mockito.when(callRepository.save(aCallNoId)).thenReturn(aCall);
//
//        final Call response = callService.saveNewCall(aCallNoId);
//
//        assertNotNull(response, "Should be not null");
//        assertEquals(aCall, response);
//    }
}
