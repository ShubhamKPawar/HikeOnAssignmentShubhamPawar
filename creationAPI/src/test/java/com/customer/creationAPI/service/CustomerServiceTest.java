package com.customer.creationAPI.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customer.creationAPI.model.Customer;
import com.customer.creationAPI.model.CustomerGroup;
import com.customer.creationAPI.model.CustomerOccupation;
import com.customer.creationAPI.repositoryIntf.CustomerRepositoryIntf;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryIntf customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void saveCustomer_ValidCustomer_Success() throws Exception {
        // Arrange
        Customer validCustomer = createValidCustomer();
        when(customerRepository.existsByEmail(validCustomer.getEmail())).thenReturn(false);
        when(customerRepository.save(validCustomer)).thenReturn(validCustomer);

        // Act
        Customer savedCustomer = customerService.save(validCustomer);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals(validCustomer, savedCustomer);
    }

    @Test
    void saveCustomer_DuplicateEmail_ExceptionThrown() {
        // Arrange
        Customer duplicateCustomer = createValidCustomer();
        when(customerRepository.existsByEmail(duplicateCustomer.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.save(duplicateCustomer));
    }

    @Test
    void saveCustomer_UnderageCustomer_ExceptionThrown() {
        // Arrange
        Customer underageCustomer = createValidCustomer();
        underageCustomer.setDob(new Date()); // Set the date to make the customer underage

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.save(underageCustomer));
    }

    @Test
    void saveCustomer_ExceptionDuringSave_ExceptionThrown() {
        // Arrange
        Customer validCustomer = createValidCustomer();
        when(customerRepository.existsByEmail(validCustomer.getEmail())).thenReturn(false);
        when(customerRepository.save(validCustomer)).thenThrow(new RuntimeException("Simulated error during save"));

        // Act & Assert
        assertThrows(Exception.class, () -> customerService.save(validCustomer));
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }

    private Customer createValidCustomer() {
        return new Customer("Shubham Pawar", "shubhpawar123@gmail.com", parseDateString("2001-05-18"), CustomerOccupation.developer, CustomerGroup.developer);
    }
}
