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

import com.cgi.grocery.response.GroceryResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

class GroceryUtilsTest {

    @Test
    void convertToLocalDate() {

        LocalDate localDate = GroceryUtils.convertToLocalDate("10/01/2021");
        Assertions.assertNotNull(localDate);
        Assertions.assertEquals(10, localDate.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, localDate.getMonth());
    }

    @Test
    void convertToLocalDate_WithHyphen() {
        LocalDate localDate = GroceryUtils.convertToLocalDate("10-01-2021");
        Assertions.assertNotNull(localDate);
        Assertions.assertEquals(10, localDate.getDayOfMonth());
        Assertions.assertEquals(Month.JANUARY, localDate.getMonth());
    }

    @Test
    void getPageable() {
        Pageable pageable = GroceryUtils.getPageable(0, 10);
        Assertions.assertNotNull(pageable);
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(10, pageable.getPageSize());
    }

    @Test
    void getPaginatedGroceryResponse() {
        List<GroceryResponse> groceryResponseList = TestUtils.getGroceryResponseList(this.getClass().getClassLoader());
        Page<GroceryResponse> response = GroceryUtils.getPaginatedGroceryResponse(groceryResponseList, groceryResponseList.size(),
                GroceryUtils.getPageable(0, 10));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(5, response.getTotalElements());
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(5, response.getContent().size());
    }
}