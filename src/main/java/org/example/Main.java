package org.example;

import org.apache.log4j.BasicConfigurator;
import org.example.dbservicies.DatabaseInitService;
import org.example.dbservicies.DatabasePopulateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main {
    private static final Logger loggerMain = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();

        /* init database */
        loggerMain.info("--------Init database---------");
        DatabaseInitService dbInitService = new DatabaseInitService();
        dbInitService.initDatabase();
        loggerMain.info("-----------Populate database------------");

        DatabasePopulateService databasePopulateService = new DatabasePopulateService();
        databasePopulateService.setDefaultSchema();
        databasePopulateService.populateDatabase();
    }
}