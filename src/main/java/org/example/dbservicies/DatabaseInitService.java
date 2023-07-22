package org.example.dbservicies;

import org.apache.log4j.BasicConfigurator;
import org.example.fileservicies.SQLFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.dbservicies.Database.DEFAULT_SCHEME;


public class DatabaseInitService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitService.class);
    private  Connection connection;
   private final SQLFileReader sqlFileReader;


    public DatabaseInitService(){
        connection = Database.getInstance().getConnection();
        sqlFileReader=new SQLFileReader();

    }
    public void dropMySchema() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP SCHEMA "+ DEFAULT_SCHEME +" CASCADE");
            logger.info("DROP SCHEMA  executed successfully" );
        }catch(SQLException e1){
            logger.info("DROP SCHEMA  execute failure!!!" );
        }
    }
    public void createMySchema() throws SQLException {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE SCHEMA "+ DEFAULT_SCHEME);
            logger.info("CREATE SCHEMA  executed successfully" );
        }catch(SQLException e1){
            logger.info("       Error in SQL statement -------------");
            logger.info("CREATE SCHEMA  execute failure" );
        }

        try{
            connection.setSchema(DEFAULT_SCHEME);
        }catch (SQLException e1){
            logger.info("connection.setSchema(DEFAULT_SCHEME); execute failure" );
        }
    }


    public void createWorker(int nameLowerLengthLimit, int nameUpperLengthLimit,
                             int birthdayMoreThanYear, String[] levels, int minSalary, int maxSalary ) throws SQLException {

        sqlFileReader.setSqlFileName("create_worker.sql");
        String sqlString = sqlFileReader.getSqlStatements()[0];

        try{
            connection.setSchema(DEFAULT_SCHEME);
            logger.info(" connection.setSchema(DEFAULT_SCHEME); executed successfully" );
        }catch (SQLException e1){
            logger.info(" connection.setSchema(DEFAULT_SCHEME); execute failure" );

        }

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
            logger.info("Prepared SQL statement executed successfully" );
        }catch(SQLException e1){
            logger.info("Prepared SQL statement execute failure" );
            logger.info("Check sql string syntax \" {}\", please",sqlString);
        }


    }

    public void executeInitDbSql() throws SQLException {
        String[] sqlStatementList;
        sqlFileReader.setSqlFileName("init_db.sql");
        sqlStatementList=sqlFileReader.getSqlStatements();

        try(Statement stmt = connection.createStatement()) {

            for(String sqlString: sqlStatementList) {
                stmt.execute(sqlString);
                 logger.info("SQL statement: {} \n executed successfully",sqlString );
            }

        }catch(SQLException e1){
            e1.printStackTrace();
        }finally {
            logger.info("Close connection");
            connection.close();
        }


    }
    /* unnecessary main function*/
    public static void main(String[] args) throws SQLException {
        // configure logger
        BasicConfigurator.configure();
        DatabaseInitService dbInitService = new DatabaseInitService();
        dbInitService.dropMySchema();
        dbInitService.createMySchema();
        dbInitService.createWorker(2,1000,1900,
                    new String[]{"Trainee", "Junior", "Middle", "Senior"},100,1000);

    }
}
