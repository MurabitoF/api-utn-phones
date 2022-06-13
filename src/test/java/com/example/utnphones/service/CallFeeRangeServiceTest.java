package com.example.utnphones.service;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.CallFee;
import com.example.utnphones.model.CallFeeRange;
import com.example.utnphones.repository.BillRepository;
import com.example.utnphones.repository.CallFeeRangeRepository;
import com.example.utnphones.service.impl.CallFeeRangeServiceImpl;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CallFeeRangeServiceTest {

    @InjectMocks
    private CallFeeRangeServiceImpl callFeeRangeService;

    @Mock
    private CallFeeRangeRepository callFeeRangeRepository;

    @Test
    void getAllCallFeeRangeTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFee aCallFee = aCallFee();
        final List<CallFeeRange> aCallFeeRangeList = aCallFeeRangeList();

        Mockito.when(callFeeRangeRepository.findAllByCallFee(aCallFee)).thenReturn(aCallFeeRangeList);

        final List<CallFeeRange> response = callFeeRangeService.getAllRangesByCallFee(aCallFee);

        assertNotNull(response, "Should be not null");
        assertFalse(response.isEmpty(), "Should have content");
        assertEquals(aCallFeeRangeList, response);
    }

    @Test
    void getCallFeeRangeByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRange = aCallFeeRange();

        Mockito.when(callFeeRangeRepository.findById(1L)).thenReturn(Optional.ofNullable(aCallFeeRange));

        final CallFeeRange response = callFeeRangeService.getCallFeeRangeById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(aCallFeeRange, response);
    }

    @Test
    void getCallFeeRangeByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(callFeeRangeRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { callFeeRangeService.getCallFeeRangeById(impossibleId); });
    }

    @Test
    void saveNewRangeTest() throws EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRangeNoId = aCallFeeRange();
        final CallFeeRange aCallFeeRange = aCallFeeRange();
        aCallFeeRange.setCallFeeRangeId(1L);
        final List<CallFeeRange> ranges = new ArrayList<>();

        Mockito.when(callFeeRangeRepository.findAllByCallFeeAndStartAtIsBetweenOrEndAtIsBetween(
                aCallFeeRangeNoId.getCallFee(),
                aCallFeeRangeNoId.getStartAt(),
                aCallFeeRangeNoId.getEndAt()))
                .thenReturn(ranges);

        Mockito.when(callFeeRangeRepository.save(aCallFeeRangeNoId)).thenReturn(aCallFeeRange);

        final CallFeeRange response = callFeeRangeService.saveNewRange(aCallFeeRangeNoId);

        assertNotNull(response, "Should be not null");
        assertEquals(aCallFeeRange, response);
    }

    @Test
    void saveNewRangeFailedTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRangeNoId = aCallFeeRange();
        final List<CallFeeRange> ranges = aCallFeeRangeList();

        Mockito.when(callFeeRangeRepository.findAllByCallFeeAndStartAtIsBetweenOrEndAtIsBetween(
                        aCallFeeRangeNoId.getCallFee(),
                        aCallFeeRangeNoId.getStartAt(),
                        aCallFeeRangeNoId.getEndAt()))
                .thenReturn(ranges);

        assertThrows(EntityExitstExeption.class, () -> { callFeeRangeService.saveNewRange(aCallFeeRangeNoId); });
    }

    @Test
    void updateCallFeeRangeTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRange = aCallFeeRange();
        aCallFeeRange.setCallFeeRangeId(1L);
        final CallFeeRange aUpdatedRange = aCallFeeRange();
        aUpdatedRange.setCallFeeRangeId(1L);
        aUpdatedRange.setStartAt(LocalTime.now());
        aUpdatedRange.setEndAt(LocalTime.parse("23:00:00"));
        aUpdatedRange.setPrice(100D);

        Mockito.when(callFeeRangeRepository.findById(1L)).thenReturn(Optional.ofNullable(aCallFeeRange));
        Mockito.when(callFeeRangeRepository.save(aCallFeeRange)).thenReturn(aUpdatedRange);

        final CallFeeRange response = callFeeRangeService.updateCallFeeRange(1L, aUpdatedRange);

        assertNotNull(response, "Should be not null");
        assertEquals(aUpdatedRange, response);
    }

    @Test
    void updateCallFeeRangeNoChangeTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRange = aCallFeeRange();

        Mockito.when(callFeeRangeRepository.findById(1L)).thenReturn(Optional.ofNullable(aCallFeeRange));
        Mockito.when(callFeeRangeRepository.save(aCallFeeRange)).thenReturn(aCallFeeRange);

        final CallFeeRange response = callFeeRangeService.updateCallFeeRange(1L, aCallFeeRange);

        assertNotNull(response, "Should be not null");
        assertEquals(aCallFeeRange, response);
    }

    @Test
    void deleteCallFeeRangeTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final CallFeeRange aCallFeeRange = aCallFeeRange();
        aCallFeeRange.setCallFeeRangeId(1L);

        Mockito.when(callFeeRangeRepository.findById(1L)).thenReturn(Optional.of(aCallFeeRange));
        Mockito.doNothing().when(callFeeRangeRepository).delete(aCallFeeRange);

        callFeeRangeService.deleteCallFeeRange(1L);

        Mockito.verify(callFeeRangeRepository, Mockito.times(1)).delete(aCallFeeRange);
    }

}
