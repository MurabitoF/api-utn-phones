package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.dto.CityRequestDto;
import com.example.utnphones.model.City;
import com.example.utnphones.model.Province;
import com.example.utnphones.service.CityService;
import com.example.utnphones.service.ProvinceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CityController.class)
class CityControllerTest extends AbstractTest {

    @MockBean
    private CityService cityService;

    @MockBean
    private ProvinceService provinceService;

    @Test
    void getAllCitiesTest() throws Exception{

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/cities/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCityByIdTest() throws Exception{

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/cities/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createCityTest() throws Exception{

        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .post("/api/cities/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(aCityNoIdJson()))
                .andExpect(status().isCreated());
    }


    public City aCity(){
        return City.builder()
                .cityId(1L)
                .cityName("Mar del Plata")
                .areaCode("223")
                .province(aProvince())
                .build();
    }

    public CityRequestDto aCityNoId(){
        return CityRequestDto.builder()
                .cityName("Mar del Plata")
                .areaCode("223")
                .provinceId(1L)
                .build();
    }

    private String aCityJson(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aCity());
    }
    private String aCityNoIdJson(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aCityNoId());
    }

    public Province aProvince(){
        return Province.builder().provinceId(1L).provinceName("Buenos Aires").build();
    }
    public List<City> aListCity(){
        List<City> list = new ArrayList<>();
        list.add(new City(1L,"Mar del Plata", "223", aProvince()));
        list.add(new City(2L, "Buenos Aires", "11", aProvince()));
        list.add(new City(3L, "Pinamar", "2254", aProvince()));
        list.add(new City(4L, "Bahia Blanca", "291", aProvince()));

        return list;
    }
}