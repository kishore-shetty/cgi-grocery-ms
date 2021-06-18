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

package com.cgi.grocery.controller;

import com.cgi.grocery.exception.ErrorTypeEnum;
import com.cgi.grocery.exception.GroceryValidationException;
import com.cgi.grocery.response.GroceryResponse;
import com.cgi.grocery.service.GroceryService;
import com.cgi.grocery.service.ReportService;
import com.cgi.grocery.utils.GroceryUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GroceryControllerTest {

    private GroceryController subjectUnderTest;

    @Mock
    private GroceryService groceryService;

    @Mock
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        subjectUnderTest = new GroceryController(groceryService, reportService);
    }

    @Test
    void getGroceryList() {
        List<GroceryResponse> groceryResponseList = new ArrayList<>();
        groceryResponseList.add(getGroceryResponse("coke", 20.00, LocalDate.now()));
        groceryResponseList.add(getGroceryResponse("pepsi", 30.00, LocalDate.now()));
        groceryResponseList.add(getGroceryResponse("sprite", 40.00, LocalDate.now()));

        Page<GroceryResponse> pagedGroceryResponse = PageableExecutionUtils.getPage(groceryResponseList,
                GroceryUtils.getPageable(0, 10), () -> 3);
        Mockito
                .when(groceryService.getGroceryList(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(pagedGroceryResponse);
        Page<GroceryResponse> response = subjectUnderTest.getGroceryList(0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(3, response.getContent().size());
    }

    @Test
    void findGroceryById() {
        Mockito
                .when(groceryService.findGroceryById(Mockito.anyString()))
                .thenReturn(getGroceryResponse("coke", 20.00, LocalDate.now()));
        GroceryResponse response = subjectUnderTest.findGroceryById("ID101");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("coke", response.getName());
    }

    @Test
    void findGroceryById_GVE_01() {
        try {
            GroceryResponse response = subjectUnderTest.findGroceryById("");
            Assertions.fail();
        }catch (GroceryValidationException gve) {
            Assertions.assertNotNull(gve.getErrorCode());
            Assertions.assertEquals(ErrorTypeEnum.GVE_01.getErrorCode(), gve.getErrorCode());
        }
    }

    @Test
    void searchGroceriesByName() {

        List<GroceryResponse> groceryResponseList = new ArrayList<>();
        groceryResponseList.add(getGroceryResponse("coke", 20.00, LocalDate.now()));
        groceryResponseList.add(getGroceryResponse("coke", 30.00, LocalDate.now()));
        groceryResponseList.add(getGroceryResponse("coke", 40.00, LocalDate.now()));

        Page<GroceryResponse> pagedGroceryResponse = PageableExecutionUtils.getPage(groceryResponseList,
                GroceryUtils.getPageable(0, 10), () -> 3);

        Mockito
                .when(groceryService.searchGroceries(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(pagedGroceryResponse);

        Page<GroceryResponse> response = subjectUnderTest.searchGroceriesByName("coke", 0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(3, response.getTotalElements());
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(3, response.getContent().size());
    }

    @Test
    void searchGroceriesByName_GVE_02() {

        try {
            Page<GroceryResponse> response = subjectUnderTest.searchGroceriesByName("", 0, 10);
            Assertions.fail();
        }catch (GroceryValidationException gve) {
            Assertions.assertNotNull(gve.getErrorCode());
            Assertions.assertEquals(ErrorTypeEnum.GVE_02.getErrorCode(), gve.getErrorCode());

        }
    }

    private GroceryResponse getGroceryResponse(String name, double price, LocalDate date) {
        return new GroceryResponse(name, price, date);
    }
}