package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.dto.CallRequestDto;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.CallService;
import com.example.utnphones.utils.LocalDateTimeDeserializer;
import com.example.utnphones.utils.LocalDateTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CallController.class)
class CallControllerTest extends AbstractTest {

    @MockBean
    private CallService callService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    void getAllCallsTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/calls/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCallsByPhoneNumberTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/calls/clients/2234556666?from=2022-06-12 00:00:00&until=2022-06-13 23:59:59")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCallByIdTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/calls/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewCall() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .post("/api/calls/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aCallJson()))
                .andExpect(status().isCreated());
    }

    private CallRequestDto aCall() {
        return CallRequestDto.builder()
                .origin("2234556666")
                .destination("2234556667")
                .datetime("10-06-2022 13:00:22")
                .duration(60)
                .build();
    }

    private String aCallJson() {
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
        return prettyGson.toJson(aCall());
    }
}
