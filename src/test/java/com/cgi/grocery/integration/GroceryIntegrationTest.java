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

package com.cgi.grocery.integration;

import com.cgi.grocery.GroceryApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = GroceryApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@Slf4j
class GroceryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getGroceries() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                    .get("/groceries")
                    .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void searchGroceryByName() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/groceries/search")
                .contentType("application/json")
                .param("name", "Amla"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertNotNull(mvcResult.getResponse().getContentAsString());
    }

}
