package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.BillService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BillController.class)
class BillControllerTest extends AbstractTest {

    @MockBean
    private BillService billService;

    @MockBean
    private AccountService accountService;

    @Test
    void getAllBillsTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/bills/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBillById() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/bills/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBillsByClientId() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/bills/clients/1?from=2022-06-12 00:00:00&until=2022-06-13 00:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
