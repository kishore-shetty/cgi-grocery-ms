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

package com.cgi.grocery.service;

import com.cgi.grocery.exception.ErrorTypeEnum;
import com.cgi.grocery.exception.GroceryException;
import com.cgi.grocery.model.Grocery;
import com.cgi.grocery.repository.GroceryRepository;
import com.cgi.grocery.response.GroceryResponse;
import com.cgi.grocery.utils.GroceryUtils;
import com.cgi.grocery.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GroceryServiceTest {

    private GroceryService subjectUnderTest;

    @Mock
    private GroceryRepository groceryRepository;

    @BeforeEach
    void setUp() {
        subjectUnderTest = new GroceryService(groceryRepository);
    }

    @Test
    void getGroceryList() {
        List<Grocery> groceryList = TestUtils.getGroceryList(this.getClass().getClassLoader());
        Page<Grocery> pagedGrocery = PageableExecutionUtils.getPage(groceryList,
                GroceryUtils.getPageable(0, 10), () -> groceryList.size());
        Mockito
                .when(groceryRepository.findAll((Pageable) Mockito.any()))
                .thenReturn(pagedGrocery);

        Page<GroceryResponse> response = subjectUnderTest.getGroceryList(0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(5, response.getTotalElements());
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(5, response.getContent().size());

    }

    @Test
    void searchGroceries() {
        List<Grocery> groceryList = TestUtils.getGroceryList(this.getClass().getClassLoader());
        Page<Grocery> pagedGrocery = PageableExecutionUtils.getPage(groceryList,
                GroceryUtils.getPageable(0, 10), () -> groceryList.size());
        Mockito
                .when(groceryRepository.findByName(Mockito.anyString(), (Pageable) Mockito.any()))
                .thenReturn(pagedGrocery);

        Page<GroceryResponse> response = subjectUnderTest.searchGroceries("coke", 0, 10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(5, response.getTotalElements());
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(5, response.getContent().size());
    }

    @Test
    void findGroceryById_GE_01() {
        Mockito
                .when(groceryRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());
        try{
            subjectUnderTest.findGroceryById("ID1001");
        }catch (GroceryException ge){
            Assertions.assertEquals(ErrorTypeEnum.GE_01.getErrorCode(), ge.getErrorCode());
        }
    }

    @Test
    void findGroceryById() {
        Mockito
                .when(groceryRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(getGrocery()));

        GroceryResponse response = subjectUnderTest.findGroceryById("ID1001");
        Assertions.assertNotNull(response);
        Assertions.assertEquals("coke", response.getName());
    }

    private Grocery getGrocery() {
        Grocery grocery = new Grocery();
        grocery.setName("coke");
        grocery.setDate(LocalDate.now());
        grocery.setPrice(20.00);
        return grocery;
    }

}