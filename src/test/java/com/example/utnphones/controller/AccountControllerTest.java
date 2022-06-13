package com.example.utnphones.controller;

import com.example.utnphones.AbstractTest;
import com.example.utnphones.dto.ClientRequestDto;
import com.example.utnphones.dto.EmployeeRequestDto;
import com.example.utnphones.service.AccountService;
import com.example.utnphones.service.CityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccountController.class)
class AccountControllerTest extends AbstractTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private CityService cityService;

    @Test
    void getAllEmployeesTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/api/accounts/employees/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeByIdTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/accounts/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllClientTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/accounts/clients/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getClientByIdTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                        .get("/api/accounts/clients/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveAccountTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/accounts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonEmployee()))
                .andExpect(status().isCreated());
    }

    @Test
    void updateAccountTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .put("/api/accounts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonClient(aClientDto())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAccountTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .delete("/api/accounts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void saveAccountErrorTest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/api/accounts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aJsonErrorAccount()))
                .andExpect(status().isBadRequest());
    }

    private ClientRequestDto aClientDto(){
        return ClientRequestDto.builder()
                .firstName("Franco")
                .surname("Murabito")
                .dni("12345678")
                .cityId(1L)
                .userId(1L)
                .phoneNumber("1234567890")
                .build();
    }

    private EmployeeRequestDto aEmployeeDto(){
        return EmployeeRequestDto.builder()
                .firstName("Franco")
                .surname("Murabito")
                .dni("12356678")
                .cityId(1L)
                .userId(2L)
                .area("Software Development")
                .build();
    }

    private String aJsonClient(ClientRequestDto clientRequest){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonElement element = prettyGson.toJsonTree(clientRequest);
        element.getAsJsonObject().addProperty("@type", "clientDto");

        return prettyGson.toJson(element);
    }

    private String aJsonEmployee(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        JsonElement element = prettyGson.toJsonTree(aEmployeeDto());
        element.getAsJsonObject().addProperty("@type", "employeeDto");

        return prettyGson.toJson(element);
    }

    private String aJsonErrorAccount(){
        final Gson prettyGson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return prettyGson.toJson(aClientDto());
    }
}
