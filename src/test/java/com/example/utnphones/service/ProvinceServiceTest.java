package com.example.utnphones.service;

import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.Province;
import com.example.utnphones.repository.ProvinceRepository;
import com.example.utnphones.service.impl.ProvinceServiceImpl;
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

import java.util.Optional;

import static com.example.utnphones.utils.MockModels.aPageProvince;
import static com.example.utnphones.utils.MockModels.aProvince;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProvinceServiceTest {

    @InjectMocks
    private ProvinceServiceImpl provinceService;

    @Mock
    private ProvinceRepository provinceRepository;

    @Test
    void getAllProvincesTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Province> aPageProvince = aPageProvince();

        Mockito.when(provinceRepository.findAll(pageable)).thenReturn(aPageProvince);

        Page<Province> response = provinceService.getAllProvinces(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aPageProvince, response);
    }

    @Test
    void getProvinceByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Province aProvince = aProvince();

        Mockito.when(provinceRepository.findById(1L)).thenReturn(Optional.ofNullable(aProvince));

        Province response = provinceService.getProvinceById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(aProvince, response);
    }

    @Test
    void getProvinceByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(provinceRepository.findById(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { provinceService.getProvinceById(impossibleId); } );
    }

    @Test
    void saveNewProvinceTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Province aProvinceNoId = aProvince();
        aProvinceNoId.setProvinceId(null);
        final Province aProvince = aProvince();

        Mockito.when(provinceRepository.save(aProvinceNoId)).thenReturn(aProvince);

        Province response = provinceService.saveNewProvince(aProvinceNoId);

        assertNotNull(response, "Should be not null");
        assertEquals(aProvince, response);
    }
}
