package org.example.dbservicies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * This is Singleton class Database which establish connection to the
 * database specified by DB_URL and provide user with Connection instance.
 *  Example: Connection  conn = Database.getInstance().getConnection();
 *
 */

public class Database {

    private static final Logger loggerDatabase = LoggerFactory.getLogger(Database.class);
    private static final String DB_URL = "jdbc:h2:D:\\JavaDeveloperPart2\\H2-DB";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "tr9g!*Hps";
    public static final String DEFAULT_SCHEME = "DEV_MODULE_5";

    private static Database databaseInstance = new Database();
    private Connection connection;



    private Database(){

    }
    public static Database getInstance(){
        if (Objects.isNull(databaseInstance)) {
            databaseInstance = new Database();
        }
        return databaseInstance;

    }

    public Connection getConnection(){
        if (Objects.isNull(connection)) {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                loggerDatabase.info("Successfully connected to database {}", DB_URL);
                return connection;
            } catch (SQLException e1) {
                loggerDatabase.error("Database connection failed.");

            }
        }
        return connection;
    }



}
