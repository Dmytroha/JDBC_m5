package org.example.dbservicies;

import org.apache.log4j.BasicConfigurator;
import org.example.fileservicies.SQLFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePopulateService {

    private static final Logger logger = LoggerFactory.getLogger(DatabasePopulateService.class);
    private  Connection conn;
    private  String[] sqlStatementList;
    public DatabasePopulateService(){
        conn = Database.getInstance().getConnection();
        sqlStatementList=(new SQLFileReader("populate_db.sql")).getSqlStatements();

    }

    public void executePopulateDb(){
        try(Statement stmt = conn.createStatement()) {
            for(String sqlString: sqlStatementList) {
                stmt.execute(sqlString);
                logger.info("SQL statement: {} \n executed successfully",sqlString );
            }

        }catch(SQLException e1){
            e1.printStackTrace();
        }finally {
            try{
            logger.info("Close connection");
            conn.close();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info("this is DatabasePopulateService, Baby!");
        DatabasePopulateService databasePopulateService = new DatabasePopulateService();
        databasePopulateService.executePopulateDb();
        logger.info("executed successfully");
    }
}
