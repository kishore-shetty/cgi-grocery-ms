package com.cgi.grocery.service;

import com.cgi.grocery.repository.GroceryRepository;
import com.cgi.grocery.response.ReportResponse;
import com.cgi.grocery.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    private ReportService subjectUnderTest;

    @Mock
    private GroceryRepository groceryRepository;

    @BeforeEach
    void setUp() {
        subjectUnderTest = new ReportService(groceryRepository);
    }

    @Test
    void getGroceryTrendReport() {
        Mockito
                .when(groceryRepository.findByName(Mockito.anyString()))
                .thenReturn(TestUtils.getGroceryList(this.getClass().getClassLoader()));
        ReportResponse response = subjectUnderTest.getGroceryTrendReport("Coke");
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getPriceTrend());
        Assertions.assertNotNull(response.getPriceTrend().get("Coke"));
    }

    @Test
    void getGroceryTrendReport_EmptyName() {
        Mockito
                .when(groceryRepository.findAll())
                .thenReturn(TestUtils.getGroceryList(this.getClass().getClassLoader()));
        ReportResponse response = subjectUnderTest.getGroceryTrendReport(null);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getPriceTrend());
        Assertions.assertNotNull(response.getPriceTrend().get("Coke"));
    }
}