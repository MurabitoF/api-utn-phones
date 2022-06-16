package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Bill;
import com.example.utnphones.model.Client;
import com.example.utnphones.repository.BillRepository;
import com.example.utnphones.service.impl.BillServiceImpl;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BillServiceTest {

    @InjectMocks
    private BillServiceImpl billService;

    @Mock
    private BillRepository billRepository;

    @Test
    void getAllBillsTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Bill> aBillPage = aBillPage();

        Mockito.when(billRepository.findAll(pageable)).thenReturn(aBillPage);

        Page<Bill> response = billService.getAllBills(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aBillPage, response);
    }

    @Test
    void getBillsByClientIdTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Bill> aBillPage = aBillPage();
        final Client aClient = aClient();
        final LocalDateTime aLocalDateTime = LocalDateTime.now();

        Mockito.when(billRepository.findAllByClientAndBillDateBetween(pageable, aClient, aLocalDateTime, aLocalDateTime)).thenReturn(aBillPage);

        Page<Bill> response = billService.getBillsByClient(pageable, aClient, aLocalDateTime, aLocalDateTime);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aBillPage, response);
    }

    @Test
    void getBillByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Bill aBill = aBill();
        aBill.setBillId(1L);

        Mockito.when(billRepository.findById(1L)).thenReturn(Optional.of(aBill));

        Bill response = billService.getBillById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(1L, response.getBillId());
    }

    @Test
    void getBillByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(billRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { billService.getBillById(impossibleId); });
    }

}
