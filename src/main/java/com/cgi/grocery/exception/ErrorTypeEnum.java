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

public enum ErrorTypeEnum {

    GVE_01("GVE_01", "Invalid grocery id"),
    GVE_02("GVE_02", "Invalid grocery name"),

    GE_01("GE_01","Grocery not found for provided ID"),

    GE_99("INTERNAL_SERVER_ERROR", "Please contact support team");

    String errorCode;

    String errorMessage;

    ErrorTypeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
