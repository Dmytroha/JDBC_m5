package org.example.dbservicies;

import org.apache.log4j.BasicConfigurator;
import org.example.fileservicies.CsvFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.example.dbservicies.Database.DEFAULT_SCHEMA;

public class DatabasePopulateService {

    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulateService.class);

    public Connection getConnection() {
        return connection;
    }

    private final Connection connection;
    //private  PreparedStatement statement;
    private final CsvFileReader dataReader;
    public DatabasePopulateService(){
        connection = Database.getInstance().getConnection();
        dataReader = new CsvFileReader();
    }

    public void populateWorker() throws SQLException {
        HashMap<Integer, ArrayList<String>> hashMapWorker = dataReader.getDataFromFile("worker.csv");

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES (?, ?, ?, ?)")) {
            int rowCount = hashMapWorker.get(1).size(); // Number of rows
            logger.info("This is hashMapWorker ---> {}", hashMapWorker.toString());
            for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
                statement.setString(1, hashMapWorker.get(1).get(rowNumber));
                logger.info("This is hashMapWorker ---> {}", hashMapWorker.get(1).get(rowNumber));
                statement.setDate(2,
                        Date.valueOf(hashMapWorker.get(2).get(rowNumber)));
                logger.info("This is hashMapWorker ---> {}", Date.valueOf(hashMapWorker.get(2).get(rowNumber)));
                statement.setString(3, hashMapWorker.get(3).get(rowNumber));
                logger.info("This is hashMapWorker ---> {}", hashMapWorker.get(3).get(rowNumber));
                statement.setInt(4, Integer.parseInt(hashMapWorker.get(4).get(rowNumber)));
                logger.info("This is hashMapWorker ---> {}", Integer.parseInt(hashMapWorker.get(4).get(rowNumber)));
                statement.addBatch();

            }
            statement.executeBatch();
        }catch( SQLException e1){
            logger.info("populateWorker() Failed!!!");
            throw(e1);
        }
        logger.info("populateWorker()");

    }
    public void populateClient() throws SQLException {
        HashMap<Integer, ArrayList<String>> hashMapClient = dataReader.getDataFromFile("client.csv");

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO client (NAME) VALUES (?)")){
        int rowCount = hashMapClient.get(1).size(); // Number of rows
        logger.info(hashMapClient.toString());
        for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
            statement.setString(1, hashMapClient.get(1).get(rowNumber));
            statement.addBatch();
        }
        statement.executeBatch();
        } catch( SQLException e1){
            logger.info("populateClient() Failed!!!");
            throw(e1);
        }
        logger.info("populateClient()");
    }
    public void populateProject() throws SQLException {
        HashMap<Integer, ArrayList<String>> hashMapProject = dataReader.getDataFromFile("project.csv");

        try(PreparedStatement statement = connection.
                prepareStatement("INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES (?, ?, ?)")){
        int rowCount = hashMapProject.get(1).size(); // Number of rows
        logger.info(hashMapProject.toString());
        for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
            statement.setInt(1, Integer.parseInt(hashMapProject.get(1).get(rowNumber)));
            statement.setDate(2,
                    Date.valueOf(hashMapProject.get(2).get(rowNumber).trim()));
            statement.setDate(3,
                    Date.valueOf(hashMapProject.get(3).get(rowNumber).trim()));
            statement.addBatch();
        }
        statement.executeBatch();
        }catch( SQLException e1){
            logger.info("populateClient() Failed!!!");
            throw(e1);
        }
        logger.info("populateProject()");
    }
    public void populateProjectWorker() throws SQLException {
        logger.info("populateProjectWorker started" );
        HashMap<Integer, ArrayList<String>> hashMapProjectWorker = dataReader.getDataFromFile("project_worker.csv");

        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES (?, ?)")){
            int rowCount = hashMapProjectWorker.get(1).size(); // Number of rows

            logger.info(hashMapProjectWorker.toString());

            for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
                statement.setInt(1, Integer.parseInt(hashMapProjectWorker.get(1).get(rowNumber)));
               statement.setInt(2, Integer.parseInt(hashMapProjectWorker.get(2).get(rowNumber)));
               statement.addBatch();

            }
            statement.executeBatch();
        }catch( SQLException e1){
            logger.info("populateClient() Failed!!!");
            throw(e1);
        }
        logger.info("populateProjectWorker");
    }
    public void setDefaultSchema(){
        try{
            connection.setSchema(DEFAULT_SCHEMA);
            logger.info(" connection.setSchema(DEFAULT_SCHEME); executed successfully" );
        }catch (SQLException e1){
            logger.info(" connection.setSchema(DEFAULT_SCHEME); execute failure" );
        }
    }
    public void populateDatabase() throws SQLException {
        //work instead of main(...)
        setDefaultSchema();
        connection.setAutoCommit(false);
        try {
            populateWorker();
            populateClient();
            populateProject();
            populateProjectWorker();
        }
        catch(SQLException e1){
            logger.info("Populate database fail!!!");
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
            logger.info("Transaction AutoCommit(true)");
        }
    }
    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();

        DatabasePopulateService databasePopulateService = new DatabasePopulateService();
        databasePopulateService.setDefaultSchema();
        databasePopulateService.getConnection().setAutoCommit(false);
        try {
            databasePopulateService.populateDatabase();

        }catch(SQLException e1){
            logger.info("Populate database fail!!!");
            e1.printStackTrace();
           databasePopulateService.getConnection().rollback();
        }finally {
            databasePopulateService.getConnection().setAutoCommit(true);
        }

    }
}
