package com.cgi.grocery.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GroceryControllerAdviceTest {

    private GroceryControllerAdvice subjectUnderTest;

    @BeforeEach
    void setUp() {
        subjectUnderTest = new GroceryControllerAdvice();
    }

    @Test
    void handleGroceryValidationException() {
        ResponseEntity<ErrorResponse> responseEntity = subjectUnderTest.handleGroceryValidationException(new GroceryValidationException(ErrorTypeEnum.GVE_01));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        ErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertEquals(ErrorTypeEnum.GVE_01.getErrorCode(), errorResponse.getErrorCode());
    }

    @Test
    void handleGroceryException() {
        ResponseEntity<ErrorResponse> responseEntity = subjectUnderTest.handleGroceryException(new GroceryException(ErrorTypeEnum.GE_01));
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        ErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertEquals(ErrorTypeEnum.GE_01.getErrorCode(), errorResponse.getErrorCode());
    }

    @Test
    void handleGenericException() {
        ResponseEntity<ErrorResponse> responseEntity = subjectUnderTest.handleGenericException(new Exception());
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        ErrorResponse errorResponse = responseEntity.getBody();
        Assertions.assertEquals(ErrorTypeEnum.GE_99.getErrorCode(), errorResponse.getErrorCode());
    }
}