package org.example;

import org.apache.log4j.BasicConfigurator;
import org.example.dbservicies.DatabaseInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main {
    private static final Logger loggerMain = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();
        /* init database */
        loggerMain.info("Init database");
        DatabaseInitService dbInitService = new DatabaseInitService();
        dbInitService.executeInitDbSql();
        /* populate database (fill tables with data)*/


    }
}