/*
 *  /*
 *  * Copyright (c) 2021 /  Kishore B Shetty
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *          http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.cgi.grocery.utils;

import com.cgi.grocery.exception.ErrorTypeEnum;
import com.cgi.grocery.exception.GroceryValidationException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidationUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void isValidNameAndPrice() {

        XSSFSheet xssfSheet = TestUtils.getXSSFSheet(this.getClass().getClassLoader());
        Row row = xssfSheet.getRow(0);
        boolean isValid = ValidationUtils.isValidNameAndPrice(row);
        Assertions.assertFalse(isValid);

        row = xssfSheet.getRow(1);
        isValid = ValidationUtils.isValidNameAndPrice(row);
        Assertions.assertFalse(isValid);

        row = xssfSheet.getRow(2);
        isValid = ValidationUtils.isValidNameAndPrice(row);
        Assertions.assertFalse(isValid);

        row = xssfSheet.getRow(3);
        isValid = ValidationUtils.isValidNameAndPrice(row);
        Assertions.assertFalse(isValid);

        row= xssfSheet.getRow(4);
        isValid = ValidationUtils.isValidNameAndPrice(row);
        Assertions.assertTrue(isValid);

    }

    @Test
    void validateGroceryId() {
        try {
            ValidationUtils.validateGroceryId("ID123");
        }catch (GroceryValidationException gve){
            Assertions.fail();
        }
    }

    @Test
    void validateGroceryId_GVE_01() {
        try {
            ValidationUtils.validateGroceryId("");
        }catch (GroceryValidationException gve){
            Assertions.assertNotNull(gve.getErrorCode());
            Assertions.assertEquals(ErrorTypeEnum.GVE_01.getErrorCode(), gve.getErrorCode());
        }
    }

    @Test
    void validateGroceryName() {
        try {
            ValidationUtils.validateGroceryName("coke");
        }catch (GroceryValidationException gve){
            Assertions.fail();
        }
    }

    @Test
    void validateGroceryName_GVE_02() {
        try {
            ValidationUtils.validateGroceryName("");
        }catch (GroceryValidationException gve){
            Assertions.assertNotNull(gve.getErrorCode());
            Assertions.assertEquals(ErrorTypeEnum.GVE_02.getErrorCode(), gve.getErrorCode());
        }
    }
}