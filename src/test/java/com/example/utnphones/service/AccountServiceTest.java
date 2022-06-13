package com.example.utnphones.service;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.NotFoundEntityException;
import com.example.utnphones.model.*;
import com.example.utnphones.repository.AccountRepository;
import com.example.utnphones.repository.ClientRepository;
import com.example.utnphones.repository.EmployeeRepository;
import com.example.utnphones.service.impl.AccountServiceImpl;
import com.example.utnphones.utils.MockModels;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.utnphones.utils.MockModels.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void getAllEmployeesTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0,10);
        final Page<Employee> aEmployeePage = aEmployeePage();

        Mockito.when(employeeRepository.findAllByDeleteAtNull(pageable)).thenReturn(aEmployeePage);

        final Page<Employee> response = accountService.getAllEmployees(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aEmployeePage, response);
    }

    @Test
    void getAllClientsTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Pageable pageable = PageRequest.of(0,10);
        final Page<Client> aClientPage = aClientPage();

        Mockito.when(clientRepository.findAllByDeleteAtNull(pageable)).thenReturn(aClientPage);

        final Page<Client> response = accountService.getAllClients(pageable);

        assertNotNull(response, "Should be not null");
        assertTrue(response.hasContent(), "Should have content");
        assertEquals(aClientPage, response);
    }

    @Test
    void getAccountByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Account aAccount = aClient();

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.ofNullable(aAccount));

        final Account response = accountService.getAccountById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(aAccount, response);
    }

    @Test
    void getAccountByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { accountService.getAccountById(impossibleId); });
    }

    @Test
    void getEmployeeByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Employee aEmployee = aEmployee();

        Mockito.when(employeeRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aEmployee));

        final Employee response = accountService.getEmployeeById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(aEmployee, response);
    }

    @Test
    void getEmployeeByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(employeeRepository.findByAccountIdAndDeleteAtNull(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { accountService.getEmployeeById(impossibleId); });
    }

    @Test
    void getClientByIdTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClient = aClient();

        Mockito.when(clientRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aClient));

        final Client response = accountService.getClientById(1L);

        assertNotNull(response, "Should be not null");
        assertEquals(aClient, response);
    }

    @Test
    void getClientByIdFailedTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(clientRepository.findByAccountIdAndDeleteAtNull(impossibleId)).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { accountService.getClientById(impossibleId); });
    }

    @Test
    void saveNewEmployee() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Employee aEmployeeNoId = aEmployee();
        final Employee aEmployee = aEmployee();
        aEmployee.setAccountId(1L);

        Mockito.when(employeeRepository.save(aEmployeeNoId)).thenReturn(aEmployee);

        final Account response = accountService.saveNewAccount(aEmployeeNoId);

        assertNotNull(response, "Should be not null");
        assertEquals(aEmployee, response);
    }

    @Test
    void saveNewClient() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClientNoId = aClient();
        final Client aClient = aClient();
        aClient.setAccountId(1L);

        Mockito.when(clientRepository.save(aClientNoId)).thenReturn(aClient);

        final Account response = accountService.saveNewAccount(aClientNoId);

        assertNotNull(response, "Should be not null");
        assertEquals(aClient, response);
    }

    @Test
    void updateEmployeeTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Employee aEmployee = aEmployee();
        aEmployee.setAccountId(1L);

        final City aCity = aCity();
        aCity.setCityName("Miramar");

        final Employee aUpdatedEmployee = aEmployee();
        aUpdatedEmployee.setAccountId(1L);
        aUpdatedEmployee.setFirstName("Juan");
        aUpdatedEmployee.setSurname("Perez");
        aUpdatedEmployee.setCity(aCity);
        aUpdatedEmployee.setDni("111111111");
        aEmployee.setEmployeeArea("Software Development");

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aEmployee));
        Mockito.when(employeeRepository.save(aEmployee)).thenReturn(aUpdatedEmployee);

        final Account response = accountService.updateAccount(1L, aUpdatedEmployee);

        assertNotNull(response, "Should be not null");
        assertEquals(aUpdatedEmployee, response);
    }

    @Test
    void updateEmployeeNoChangesTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Employee aEmployee = aEmployee();
        aEmployee.setAccountId(1L);

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aEmployee));
        Mockito.when(employeeRepository.save(aEmployee)).thenReturn(aEmployee);

        final Account response = accountService.updateAccount(1L, aEmployee);

        assertNotNull(response, "Should be not null");
        assertEquals(aEmployee, response);
    }

    @Test
    void updateEmptyEmployeeTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Employee aEmployee = aEmployee();
        aEmployee.setAccountId(1L);
        final Employee aEmptyEmployee = new Employee();

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aEmployee));
        Mockito.when(employeeRepository.save(aEmployee)).thenReturn(aEmployee);

        final Account response = accountService.updateAccount(1L, aEmptyEmployee);

        assertNotNull(response, "Should be not null");
        assertEquals(aEmployee, response);
    }

    @Test
    void updateClientTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClient = aClient();
        aClient.setAccountId(1L);

        final Client aUpdatedClient = aClient();
        aUpdatedClient.setPhoneNumber("2222222222");


        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aClient));
        Mockito.when(clientRepository.save(aClient)).thenReturn(aUpdatedClient);

        final Account response = accountService.updateAccount(1L, aUpdatedClient);

        assertNotNull(response, "Should be not null");
        assertEquals(aUpdatedClient, response);
    }

//    @Test
//    void updateClientFailedTest(){
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        final Client aClient = aClient();
//        aClient.setAccountId(1L);
//
//        final Client aUpdatedClient = aClient();
//        aUpdatedClient.setAccountId(1L);
//        aUpdatedClient.setPhoneNumber("2222222222");
//
//        Optional<Client> client = Optional.of(aClient);
//
//        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aClient));
//        Mockito.when(clientRepository.findByPhoneNumber(aUpdatedClient.getPhoneNumber())).thenReturn(client);
//
//        assertThrows(EntityExitstExeption.class, () -> { accountService.updateAccount(1L, aUpdatedClient); });
//    }

    @Test
    void updateClientNoChangesTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClient = aClient();
        aClient.setAccountId(1L);

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aClient));
        Mockito.when(clientRepository.save(aClient)).thenReturn(aClient);

        final Account response = accountService.updateAccount(1L, aClient);

        assertNotNull(response, "Should be not null");
        assertEquals(aClient, response);
    }

    @Test
    void updateEmptyClientTest() throws NotFoundEntityException, EntityExitstExeption {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClient = aClient();
        aClient.setAccountId(1L);

        final Client aEmptyClient = new Client();

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aClient));
        Mockito.when(clientRepository.save(aClient)).thenReturn(aClient);

        final Account response = accountService.updateAccount(1L, aEmptyClient);

        assertNotNull(response, "Should be not null");
        assertEquals(aClient, response);
    }

    @Test
    void deleteAccountTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Account aAccount = aClient();
        aAccount.setAccountId(1L);

        final Account aDeletedAccount = aClient();
        aDeletedAccount.setAccountId(1L);
        aDeletedAccount.setDeleteAt(LocalDateTime.now());

        Mockito.when(accountRepository.findByAccountIdAndDeleteAtNull(1L)).thenReturn(Optional.of(aAccount));
        Mockito.when(accountRepository.save(aAccount)).thenReturn(aDeletedAccount);

        accountService.deleteAccount(1L);

        Mockito.verify(accountRepository, Mockito.times(1)).save(aAccount);
    }

    @Test
    void getClientByPhoneNumberTest() throws NotFoundEntityException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Client aClient = aClient();

        Mockito.when(clientRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(aClient));

        final Client response = accountService.getClientByPhoneNumber("1234567890");

        assertNotNull(response, "Should be not null");
        assertEquals(aClient, response);
    }

    @Test
    void getClientByPhoneNumberFailedTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final Long impossibleId = -1L;

        Mockito.when(clientRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());

        assertThrows(NotFoundEntityException.class, () -> { accountService.getClientByPhoneNumber("1234567890"); });
    }
}
