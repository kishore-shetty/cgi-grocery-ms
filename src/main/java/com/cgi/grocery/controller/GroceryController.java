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

import com.cgi.grocery.exception.GroceryException;
import com.cgi.grocery.response.GroceryResponse;
import com.cgi.grocery.response.ReportResponse;
import com.cgi.grocery.service.GroceryService;
import com.cgi.grocery.service.ReportService;
import com.cgi.grocery.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GroceryController {

    private final GroceryService groceryService;

    private final ReportService reportService;

    public GroceryController(GroceryService groceryService, ReportService reportService) {
        this.groceryService = groceryService;
        this.reportService = reportService;
    }

    /**
     * This returns list of groceries sorted by name, max price & max priced date
     * @param pageNo
     * @param pageSize
     * @return Page<GroceryResponse>
     * @throws GroceryException
     */
    @GetMapping(value = "/groceries", produces= MediaType.APPLICATION_JSON_VALUE)
    public Page<GroceryResponse> getGroceryList(@RequestParam(defaultValue = "0") int pageNo,
                                                @RequestParam(defaultValue = "20") int pageSize) throws GroceryException {
        log.info("IN: GroceryController - getGroceryList");

        Page<GroceryResponse> pagedGroceryResponse = groceryService.getGroceryList(pageNo, pageSize);

        log.info("OUT: GroceryController - getGroceryList");
        return pagedGroceryResponse;
    }

    /**
     * This returns grocery for particular id
     * @param id
     * @return GroceryResponse
     * @throws GroceryException
     */
    @GetMapping(value = "/groceries/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public GroceryResponse findGroceryById(@PathVariable(required = true) String id) throws GroceryException {
        log.info("IN: GroceryController - findGroceryById with id: {}", id);

        ValidationUtils.validateGroceryId(id);
        GroceryResponse groceryListResponse = groceryService.findGroceryById(id);

        log.info("OUT: GroceryController - findGroceryById");
        return groceryListResponse;
    }

    /**
     * Endpoint returns list of groceries with price and date for particular item.
     * @param name
     * @param pageNo
     * @param pageSize
     * @return Page<GroceryResponse>
     * @throws GroceryException
     */
    @GetMapping(value = "/groceries/search", produces= MediaType.APPLICATION_JSON_VALUE)
    public Page<GroceryResponse> searchGroceriesByName(@RequestParam(required = true) String name,
                                                     @RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "20") int pageSize) throws GroceryException {
        log.info("IN: GroceryController - searchGroceries with name: {}", name);

        ValidationUtils.validateGroceryName(name);
        Page<GroceryResponse> pagedGroceryResponse = groceryService.searchGroceries(name, pageNo, pageSize);

        log.info("OUT: GroceryController - searchGroceries");
        return pagedGroceryResponse;
    }

    /**
     * this returns report for each Item Name, showing how their price trended
     * @return ReportResponse
     * @throws GroceryException
     */
    @GetMapping(value = "/groceries/report", produces= MediaType.APPLICATION_JSON_VALUE)
    public ReportResponse getGroceryTrendReport(@RequestParam(required = false) String name) throws GroceryException {
        log.info("IN: GroceryController - getGroceryTrendReport");

        ReportResponse reportResponse = reportService.getGroceryTrendReport(name);

        log.info("OUT: GroceryController - getGroceryTrendReport");
        return reportResponse;
    }

}
