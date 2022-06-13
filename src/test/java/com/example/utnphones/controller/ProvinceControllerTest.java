package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.model.Province;
import com.example.utnphones.service.ProvinceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProvinceController.class)
class ProvinceControllerTest extends AbstractTest {

    @MockBean
    private ProvinceService provinceService;

    @Test
    void getAllProvincesTest() throws Exception {

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/provinces/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProvinceByIdTest() throws Exception {

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/provinces/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewProvinceTest() throws Exception {

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/provinces/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonProvince()))
                .andExpect(status().isCreated());
    }

    private Province aProvince() {
        return Province.builder()
                .provinceName("Provincia")
                .build();
    }

    private String aJsonProvince(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return prettyGson.toJson(aProvince());
    }
}