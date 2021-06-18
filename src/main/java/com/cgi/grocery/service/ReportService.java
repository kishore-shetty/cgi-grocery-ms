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

import com.cgi.grocery.model.Grocery;
import com.cgi.grocery.repository.GroceryRepository;
import com.cgi.grocery.response.ReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportService {

    private final GroceryRepository groceryRepository;

    public ReportService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    public ReportResponse getGroceryTrendReport(String name) {
        List<Grocery> groceries = getGroceries(name);

        Map<String, List<ReportResponse.PriceTrend>> priceTrendMap = new HashMap<>();
        for(Grocery grocery : groceries){

            List<ReportResponse.PriceTrend> priceTrendList;
            if(priceTrendMap.containsKey(grocery.getName())){
                priceTrendList = priceTrendMap.get(grocery.getName());
            }else{
                priceTrendList = new ArrayList<>();
            }
            priceTrendList.add(new ReportResponse.PriceTrend(grocery.getPrice(),grocery.getDate()));
            priceTrendMap.put(grocery.getName(), priceTrendList);
        }

        ReportResponse reportResponse = new ReportResponse();
        reportResponse.getPriceTrend().putAll(priceTrendMap);
        return reportResponse;
    }

    private List<Grocery> getGroceries(String name) {
        List<Grocery> groceries;
        if(StringUtils.isBlank(name)){
            groceries = groceryRepository.findAll();
        }else{
            groceries = groceryRepository.findByName(name);
        }
        return groceries;
    }
}
