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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
public class ValidationUtils {

    private ValidationUtils() {
    }

    /**
     * Utility to validate name & price.
     * @param row
     * @return boolean
     */
    public static boolean isValidNameAndPrice(Row row){
        boolean isValid = true;
        if(row.getCell(1) == null || row.getCell(3) == null
                || (row.getCell(3) != null
                && row.getCell(3).getCellType() == CellType.STRING
                && row.getCell(3).getStringCellValue().equalsIgnoreCase("NULL"))){
            isValid = false;
        }
        return isValid;
    }

    /**
     * Utility to validate id.
     * If id is null or empty, GVE_01 error is thrown
     * @param id
     */
    public static void validateGroceryId(String id) {
        if(StringUtils.isBlank(id)){
            throw new GroceryValidationException(ErrorTypeEnum.GVE_01);
        }
    }

    /**
     * Utility to validate name.
     * If name is null or empty, GVE_02 error is thrown
     * @param name
     */
    public static void validateGroceryName(String name) {
        if(StringUtils.isBlank(name)){
            throw new GroceryValidationException(ErrorTypeEnum.GVE_02);
        }
    }
}
