package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.City;
import com.example.utnphones.model.Province;
import com.example.utnphones.repository.CityRepository;
import com.example.utnphones.service.impl.CityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.utnphones.utils.MockModels.aCity;
import static com.example.utnphones.utils.MockModels.aPageCity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CityServiceTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Mock
    private CityRepository cityRepository;

    @Test
    void getAllCitiesTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Page<City> aCityPage = aPageCity();
        final Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(cityRepository.findAll(pageable)).thenReturn(aCityPage);

        final Page<City> response = cityService.getAllCities(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(response, aCityPage);
    }

    @Test
    void getCityByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final City aCity = aCity();
        aCity.setCityId(1L);

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(aCity));

        final City response = cityService.getCityById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(1, response.getCityId(), "Id's should be equals");
    }

    @Test
    void getCityByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(cityRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { cityService.getCityById(impossibleId); } );
    }

    @Test
    void saveNewCityTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final City aCity = aCity();
        final City aCityResponse = aCity();
        aCityResponse.setCityId(1L);

        Mockito.when(cityRepository.save(aCity)).thenReturn(aCityResponse);

        final City response = cityService.saveNewCity(aCity);

        assertNotNull(response, "Should be not null");
        assertEquals(1, response.getCityId(), "Id's should be equals");
    }

}
