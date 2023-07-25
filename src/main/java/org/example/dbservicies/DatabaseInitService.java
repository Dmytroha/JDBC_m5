package org.example.dbservicies;

import org.apache.log4j.BasicConfigurator;
import org.example.fileservicies.SQLFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.dbservicies.Database.DEFAULT_SCHEMA;


public class DatabaseInitService {
    public static final String SQL_EXECUTED_SUCCESSFULLY ="SQL statement: {} executed successfully";
    public static final String SQL_EXECUTED_FAILURE ="SQL statement: {} execute failure";

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitService.class);
    private final Connection connection;
   private final SQLFileReader sqlFileReader;


    public DatabaseInitService(){
        connection = Database.getInstance().getConnection();
        sqlFileReader=new SQLFileReader();
    }
    public void dropMySchema() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP SCHEMA "+ DEFAULT_SCHEMA +" CASCADE");
            logger.info("DROP SCHEMA  executed successfully" );
        }catch(SQLException e1){
            logger.info("DROP SCHEMA  execute failure!!!" );
        }
    }
    public void createMySchema() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA "+ DEFAULT_SCHEMA);
            logger.info("CREATE SCHEMA  executed successfully" );
        }catch(SQLException e1){
            logger.info("       Error in SQL statement -------------");
            logger.info("CREATE SCHEMA  execute failure" );
        }
    }


    public void createWorker(int nameLowerLengthLimit, int nameUpperLengthLimit,
                             int birthdayMoreThanYear, String[] levels, int minSalary, int maxSalary ) throws SQLException {

        sqlFileReader.setSqlFileName("create_worker.sql");
        String sqlString = sqlFileReader.getSqlStatements()[0];

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlString)){
            preparedStatement.setInt(1, nameLowerLengthLimit);
            preparedStatement.setInt(2,nameUpperLengthLimit);
            preparedStatement.setInt(3, birthdayMoreThanYear);
            preparedStatement.setString(4,levels[0]);
            preparedStatement.setString(5,levels[1]);
            preparedStatement.setString(6,levels[2]);
            preparedStatement.setString(7,levels[3]);
            preparedStatement.setInt(8, minSalary);
            preparedStatement.setInt(9,maxSalary);
            preparedStatement.execute();
            logger.info(SQL_EXECUTED_SUCCESSFULLY,sqlString );
        }catch(SQLException e1){
            logger.info(SQL_EXECUTED_FAILURE, sqlString);
            logger.info("Check sql string syntax  please");
        }


    }
    public void createClient(int nameLowerLengthLimit,int nameUpperLengthLimit) throws SQLException {
        sqlFileReader.setSqlFileName("create_client.sql");
        String sqlString = sqlFileReader.getSqlStatements()[0];

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlString)){
            preparedStatement.setInt(1, nameLowerLengthLimit);
            preparedStatement.setInt(2,nameUpperLengthLimit);
            preparedStatement.execute();
            logger.info(SQL_EXECUTED_SUCCESSFULLY,sqlString );
        }catch(SQLException e1){
            logger.info(SQL_EXECUTED_FAILURE, sqlString);
            logger.info("Check sql string syntax  please");
        }
    }
    public void createProject(){
        String sqlString;
        sqlFileReader.setSqlFileName("create_project.sql");
        sqlString=sqlFileReader.getSqlStatements()[0];
        try(Statement statement = connection.createStatement()) {
                statement.execute(sqlString);
                logger.info(SQL_EXECUTED_SUCCESSFULLY,sqlString );

        }catch(SQLException e1){
            logger.info(SQL_EXECUTED_FAILURE,sqlString );
        }

    }
    public void createProjectWorker(){
        String sqlString;
        sqlFileReader.setSqlFileName("create_project_worker.sql");
        sqlString=sqlFileReader.getSqlStatements()[0];
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlString);
            logger.info(SQL_EXECUTED_SUCCESSFULLY,sqlString );
        }catch(SQLException e1){
            logger.info(SQL_EXECUTED_FAILURE,sqlString );
        }

    }


    public void initDatabase() throws SQLException {
        dropMySchema();
        createMySchema();
        setDefaultSchema();
        createWorker(2,1000,1900,
                new String[]{"Trainee", "Junior", "Middle", "Senior"},100,1000);
        createClient(2,1000);
        createProject();
        createProjectWorker();

    }
    public void setDefaultSchema(){
        try{
            connection.setSchema(DEFAULT_SCHEMA);
            logger.info(" connection.setSchema(DEFAULT_SCHEME); executed successfully" );
        }catch (SQLException e1){
            logger.info(" connection.setSchema(DEFAULT_SCHEME); execute failure" );
        }
    }

    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();

        DatabaseInitService dbInitService = new DatabaseInitService();
        dbInitService.initDatabase();

    }
}
