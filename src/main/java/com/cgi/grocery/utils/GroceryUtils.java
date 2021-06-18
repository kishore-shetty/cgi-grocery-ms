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
import com.cgi.grocery.response.GroceryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GroceryUtils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    static DateTimeFormatter hyphenFormatter = DateTimeFormatter.ofPattern("d-MM-yyyy");

    private GroceryUtils() {
    }

    /**
     * This utility is to convert date in string format to LocalDate format
     * @param dateString
     * @return LocalDate
     */
    public static LocalDate convertToLocalDate(String dateString){
        LocalDate localDate;
        if(dateString.contains(GroceryConstants.HYPHEN)){
            localDate = LocalDate.parse(dateString, hyphenFormatter);
        }else{
            localDate =LocalDate.parse(dateString, formatter);
        }

        return localDate;
    }

    /** This utility is to return Pageable for given pageNo & pageSize.
     * Sorting by name, price & date
     * @param pageNo
     * @param pageSize
     * @return Pageable
     */
    public static Pageable getPageable(Integer pageNo, Integer pageSize){
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "price"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "date"));
        return PageRequest.of(pageNo, pageSize, Sort.by(orders));
    }

    /**
     * This utility is to return paginated GroceryResponse
     * @param groceryResponseList
     * @param totalCount
     * @param pageable
     * @return Page<GroceryResponse>
     */
    public static Page<GroceryResponse> getPaginatedGroceryResponse(List<GroceryResponse> groceryResponseList, long totalCount, Pageable pageable){
        return PageableExecutionUtils.getPage(groceryResponseList, pageable, () -> totalCount);
    }

}
