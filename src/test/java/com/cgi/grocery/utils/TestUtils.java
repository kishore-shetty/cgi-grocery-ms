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

import com.cgi.grocery.constants.GroceryConstants;
import com.cgi.grocery.model.Grocery;
import com.cgi.grocery.response.GroceryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class TestUtils {

    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static XSSFSheet getXSSFSheet(ClassLoader classLoader) {
        String path = classLoader.getResource("test.xlsx").getPath();
        File groceryFile = new File(path);
        XSSFSheet sheet = null;
        try (XSSFWorkbook myWorkBook = new XSSFWorkbook(groceryFile)) {
            sheet = myWorkBook.getSheetAt(0);
        } catch (Exception ex) {
            log.error("TestUtils - getRow : Exception: {}", ex.getMessage());
        }
        return sheet;
    }

    public static List<Grocery> getGroceryList(ClassLoader classLoader) {
        String path = classLoader.getResource("grocery.json").getPath();
        File groceryFile = new File(path);
        List<Grocery> groceryList = new ArrayList<>();
        try {
            groceryList = objectMapper.readValue(groceryFile, new TypeReference<List<Grocery>>() {});
        } catch (Exception ex) {
            log.error("TestUtils - getGroceryList : Exception: {}", ex.getMessage());
        }
        return groceryList;
    }

    public static List<GroceryResponse> getGroceryResponseList(ClassLoader classLoader) {
        String path = classLoader.getResource("groceryResponse.json").getPath();
        File groceryFile = new File(path);
        List<GroceryResponse> groceryResponseList = new ArrayList<>();
        try {
            groceryResponseList = objectMapper.readValue(groceryFile, new TypeReference<List<GroceryResponse>>() {});
        } catch (Exception ex) {
            log.error("TestUtils - getGroceryList : Exception: {}", ex.getMessage());
        }
        return groceryResponseList;
    }

}
