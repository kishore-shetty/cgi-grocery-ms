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

package com.cgi.grocery.event;

import com.cgi.grocery.constants.GroceryConstants;
import com.cgi.grocery.model.Grocery;
import com.cgi.grocery.repository.GroceryRepository;
import com.cgi.grocery.utils.GroceryUtils;
import com.cgi.grocery.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class StartupEvent {

    @Value("${grocery.file:vegetables.xlsx}")
    private String fileName;

    private final GroceryRepository groceryRepository;

    public StartupEvent(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    /**
     * Once the application starts up, this method is called.
     * This will read data from excel and save it to embedded mongo.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void readExcelAndStoreInDB() {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("IN: StartupEvent - readExcelAndStoreInDB. : {}", startTime);
        List<Grocery> groceryList = new ArrayList<>();

        String path = this.getClass().getClassLoader().getResource(fileName).getPath();
        File groceryFile = new File(path);

        try(XSSFWorkbook myWorkBook = new XSSFWorkbook(groceryFile)) {

            XSSFSheet firstSheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
            int totalBatch = (firstSheet.getLastRowNum()-1) / GroceryConstants.BATCH_SIZE;
            int currentBatch = 1;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                Grocery grocery = getGrocery(row);
                if (grocery != null) {
                    groceryList.add(grocery);
                }
                if (row.getRowNum() == GroceryConstants.BATCH_SIZE * currentBatch
                        && currentBatch <= totalBatch){
                    groceryRepository.saveAll(groceryList);
                    groceryList.clear();
                    currentBatch = currentBatch + 1;
                }
            }

            groceryRepository.saveAll(groceryList);

        } catch (Exception ex) {
            log.error("StartupEvent - readExcelAndStoreInDB : Exception: {}", ex.getMessage());
        }
        log.info("OUT: StartupEvent - readExcelAndStoreInDB.: {}",startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS));
    }

    private Grocery getGrocery(Row row) {
        Grocery grocery = null;
        if(ValidationUtils.isValidNameAndPrice(row)) {
            grocery = new Grocery();
            grocery.setDateSk(NumberToTextConverter.toText(row.getCell(0).getNumericCellValue()));
            grocery.setName(row.getCell(1).getStringCellValue());
            grocery.setDate(getDate(row.getCell(2)));
            grocery.setPrice(getPrice(row.getCell(3)));
        }
        return grocery;
    }

    private Double getPrice(Cell cell) {
        Double price;
        if(cell.getCellType() == CellType.NUMERIC){
            price = cell.getNumericCellValue();
        }else{
            price = new Double(cell.getStringCellValue());
        }

        return price;
    }

    private LocalDate getDate(Cell cell) {
        LocalDate localDate;
        if(cell.getCellType() == CellType.NUMERIC){
            localDate = cell.getLocalDateTimeCellValue().toLocalDate();
        }else{
            localDate = GroceryUtils.convertToLocalDate(cell.getStringCellValue());
        }

        return localDate;
    }

}
