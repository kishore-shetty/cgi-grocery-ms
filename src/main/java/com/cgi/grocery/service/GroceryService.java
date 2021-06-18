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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    /**
     * This service returns paginated GroceryResponse
     * @param pageNo
     * @param pageSize
     * @return Page<GroceryResponse>
     */
    public Page<GroceryResponse> getGroceryList(Integer pageNo, Integer pageSize) {
        Pageable pageable = GroceryUtils.getPageable(pageNo, pageSize);

        Page<Grocery> groceries = groceryRepository.findAll(pageable);
        long totalCount = groceries.getTotalElements();

        List<GroceryResponse> groceryResponseList = groceries
                .stream()
                .map(GroceryResponse::new)
                .collect(Collectors.toList());

        return GroceryUtils.getPaginatedGroceryResponse(groceryResponseList, totalCount, pageable);
    }

    /**
     * This service return paginated GroceryResponse for particular groceryItem
     * @param name
     * @param pageNo
     * @param pageSize
     * @return Page<GroceryResponse>
     */
    public Page<GroceryResponse> searchGroceries(String name, Integer pageNo, Integer pageSize) {
        Pageable pageable = GroceryUtils.getPageable(pageNo, pageSize);

        Page<Grocery> groceryList = groceryRepository.findByName(name, pageable);
        long totalCount = groceryList.getTotalElements();

        List<GroceryResponse> groceryResponseList = groceryList
                .stream()
                .map(GroceryResponse::new)
                .collect(Collectors.toList());

        return GroceryUtils.getPaginatedGroceryResponse(groceryResponseList, totalCount, pageable);
    }

    /**
     * This service is fetch GroceryResponse for particular id
     * @param id
     * @return GroceryResponse
     */
    public GroceryResponse findGroceryById(String id) {

        Optional<Grocery> optionalGrocery = groceryRepository.findById(id);
        if(!optionalGrocery.isPresent()){
            throw new GroceryException(ErrorTypeEnum.GE_01);
        }
        return new GroceryResponse(optionalGrocery.get());
    }
}
