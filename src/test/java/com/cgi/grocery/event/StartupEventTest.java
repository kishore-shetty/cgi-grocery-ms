package com.cgi.grocery.event;

import com.cgi.grocery.repository.GroceryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StartupEventTest {

    private StartupEvent subjectUnderTest;

    @Mock
    private GroceryRepository groceryRepository;

    @BeforeEach
    void setUp() {
        subjectUnderTest = new StartupEvent(groceryRepository,"test.xlsx");
    }

    @Test
    void readExcelAndStoreInDB() {
        Mockito
                .when(groceryRepository.saveAll(Mockito.any()))
                .thenReturn(Mockito.anyList());

        try{
            subjectUnderTest.readExcelAndStoreInDB();
        }catch (Exception ex){
            Assertions.fail();
        }
    }
}