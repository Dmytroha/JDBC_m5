package org.example.fileservicies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CsvFileReader {
    private static final String PATH_TO_CSV_FILES = "src\\main\\resources\\data4tables\\";

    public HashMap<Integer, ArrayList<String>> getDataFromFile(String fileName) {

        String fullPath = PATH_TO_CSV_FILES + fileName;
        String[] tableRowData; //String array of table row
        HashMap<Integer, ArrayList<String>> tableAsMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fullPath))) {
            String line;
            //read lines from file while exist
            boolean isFirstRead = true;
            while (!Objects.isNull(line = br.readLine())) {
                //split line to column data as String
                tableRowData = (line.trim()).split(",");
                if (isFirstRead) {
                    //add row to table
                    for (int i = 1; i <= tableRowData.length; i++) {
                        tableAsMap.put(i,new ArrayList<>() );
                    }
                    isFirstRead = false;
                }
                for (int i = 1; i <= tableRowData.length; i++) {
                    tableAsMap.get(i).add(tableRowData[i-1]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tableAsMap;
    }
}
