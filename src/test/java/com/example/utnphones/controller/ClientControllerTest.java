package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.model.Client;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.BillService;
import com.example.utnphones.service.CallService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.utnphones.utils.MockModels.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ClientController.class)
class ClientControllerTest extends AbstractTest {
    @MockBean
    private AccountService accountService;
    @MockBean
    private BillService billService;
    @MockBean
    private CallService callService;


    void getClientByIdTest() throws Exception {
        Client aClient = aClient();
        aClient.setAccountId(1L);

        Mockito.when(accountService.getClientById(1L)).thenReturn(aClient);

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    void getClientByIdFailedTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


}
