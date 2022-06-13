package com.example.utnphones.utils;

import com.example.utnphones.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MockModels {
    public static Province aProvince(){
        return Province.builder().provinceId(1L).provinceName("Buenos Aires").build();
    }

    public static Page<Province> aPageProvince(){
        List<Province> list = new ArrayList<>();
        list.add(aProvince());
        list.add(aProvince());
        list.add(aProvince());
        list.add(aProvince());

        return new PageImpl<Province>(list);
    }

    public static City aCity(){
        return City.builder()
                .cityName("Mar del Plata")
                .areaCode("223")
                .province(aProvince())
                .build();
    }

    public static Page<City> aPageCity(){
        List<City> list = new ArrayList<>();
        list.add(new City(1L, "Mar del Plata", "223", aProvince()));
        list.add(new City(2L, "Buenos Aires", "11", aProvince()));
        list.add(new City(3L, "Pinamar", "2254", aProvince()));
        list.add(new City(4L, "Bahia Blanca", "291", aProvince()));

        return new PageImpl<City>(list);
    }

    public static User aUser(){
        return User.builder()
                .username("user")
                .build();
    }

    public static Page<User> aUserPage() {
        List<User> list = new ArrayList<>();

        list.add(aUser());
        list.add(aUser());
        list.add(aUser());

        return new PageImpl<User>(list);
    }

    public static Client aClient(){
        return Client.builder()
                .firstName("Franco")
                .surname("Murabito")
                .dni("11231232")
                .city(aCity())
                .phoneNumber("1234567899")
                .build();
    }

    public static Page<Client> aClientPage() {
        List<Client> list = new ArrayList<>();

        list.add(aClient());
        list.add(aClient());
        list.add(aClient());
        list.add(aClient());

        return new PageImpl<Client>(list);
    }

    public static Bill aBill() {
        return new Bill();
    }

    public static Page<Bill> aBillPage() {
        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());


        return new PageImpl<Bill>(list);
    }

    public static CallFee aCallFee() {
        return CallFee.builder()
                .cityOrigin(aCity())
                .cityDestination(aCity())
                .build();
    }

    public static Page<CallFee> aCallFeePage() {
        List<CallFee> list = new ArrayList<>();
        list.add(aCallFee());
        list.add(aCallFee());
        list.add(aCallFee());
        list.add(aCallFee());

        return new PageImpl<CallFee>(list);
    }

    public static CallFeeRange aCallFeeRange() {
        return CallFeeRange.builder()
                .startAt(LocalTime.NOON)
                .endAt(LocalTime.MIDNIGHT)
                .price(10D)
                .callFee(aCallFee())
                .build();
    }

    public static List<CallFeeRange> aCallFeeRangeList() {
        List<CallFeeRange> list = new ArrayList<>();

        list.add(aCallFeeRange());
        list.add(aCallFeeRange());
        list.add(aCallFeeRange());
        list.add(aCallFeeRange());

        return list;
    }

    public static Employee aEmployee() {
        return Employee.builder()
                .firstName("Franco")
                .surname("Murabito")
                .city(aCity())
                .dni("12345678")
                .employeeArea("Area")
                .build();
    }

    public static Page<Employee> aEmployeePage() {
        List<Employee> list = new ArrayList<>();

        list.add(aEmployee());
        list.add(aEmployee());
        list.add(aEmployee());
        list.add(aEmployee());

        return new PageImpl<Employee>(list);
    }

    public static Call aCall() {
        return Call.builder()
                .phoneOrigin(aClient())
                .phoneDestination(aClient())
                .callDate(LocalDateTime.now())
                .duration(100)
                .build();
    }

    public static Page<Call> aCallPage() {
        List<Call> list = new ArrayList<>();

        list.add(aCall());
        list.add(aCall());
        list.add(aCall());
        list.add(aCall());

        return new PageImpl<Call>(list);
    }
}
