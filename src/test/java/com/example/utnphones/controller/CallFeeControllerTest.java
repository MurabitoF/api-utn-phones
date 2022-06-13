package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.dto.CallFeeRangeRequestDto;
import com.example.utnphones.dto.CallFeeRequestDto;
import com.example.utnphones.service.CallFeeRangeService;
import com.example.utnphones.service.CallFeeService;
import com.example.utnphones.service.CityService;
import com.example.utnphones.utils.LocalTimeDeserializer;
import com.example.utnphones.utils.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CallFeeController.class)
class CallFeeControllerTest extends AbstractTest {

    @MockBean
    private CallFeeService callFeeService;
    @MockBean
    private CallFeeRangeService callFeeRangeService;
    @MockBean
    private CityService cityService;

    @Test
    void getAllCallsFeesTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/callsFees/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCallFeeByIdTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/callsFees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCallFeeRangesTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/callsFees/1/ranges/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveCallFeeTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/callsFees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonCallFee()))
                .andExpect(status().isCreated());
    }

    @Test
    void saveCallFeeRangeTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/callsFees/1/ranges/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonCallFeeRange()))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCallFeeRangeTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .put("/api/callsFees/1/ranges/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonCallFeeRange()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCallFeeRangeTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .delete("/api/callsFees/1/ranges/1/"))
                .andExpect(status().isNoContent());
    }

    private CallFeeRequestDto aCallFeeRequest() {
        return CallFeeRequestDto.builder()
                .cityOrigin(1L)
                .cityDestination(1L)
                .startAt("00:00:00")
                .endAt("12:00:00")
                .price(10D)
                .build();
    }

    private CallFeeRangeRequestDto callFeeRangeRequest() {
        return CallFeeRangeRequestDto.builder()
                .startAt("00:00:00")
                .endAt("12:00:00")
                .price(12D)
                .build();
    }

    private String aJsonCallFee() {
        final Gson prettyGson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aCallFeeRequest());
    }

    private String aJsonCallFeeRange() {
        final Gson prettyGson = new GsonBuilder()
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(callFeeRangeRequest());
    }
}
