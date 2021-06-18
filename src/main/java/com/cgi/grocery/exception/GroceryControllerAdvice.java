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

package com.cgi.grocery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GroceryControllerAdvice {

    @ExceptionHandler(GroceryValidationException.class)
    protected ResponseEntity<ErrorResponse> handleGroceryValidationException(GroceryValidationException groceryException) {
        ErrorResponse errorResponse = new ErrorResponse(groceryException.getErrorCode(), groceryException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GroceryException.class)
    protected ResponseEntity<ErrorResponse> handleGroceryException(GroceryException groceryException) {
        ErrorResponse errorResponse = new ErrorResponse(groceryException.getErrorCode(), groceryException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorTypeEnum.GE_99.getErrorCode(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
