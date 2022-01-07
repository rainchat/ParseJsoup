package com.company.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MySql {
    private static MySql instance = new MySql();
    private Connection databaseConnection;
    private String createTableName;

    public static MySql getInstance() {
        return instance;
    }



    public boolean setup() {

        createTableName = "create table if not exists SiteDate (nameResourse varchar(100), author varchar(100), version varchar(100), description varchar(100),"
                + " downloads int, views int, firstRelease varchar(100), lastUpdate varchar(100), rating double, siteUrl varchar(100)";

        long databaseStartTime = System.currentTimeMillis();
        setupDatabase();
        if (getDatabaseConnection() != null){
            System.out.println("Communication to the database was successful. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
        }
        else {
            System.out.println("Communication to the database failed. (Took " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
            return false;
        }
        return true;
    }

    private void setupDatabase() {
        if (getDatabaseConnection() != null) return;

        /*
        Connect
         */
        try {
            Statement statement;

            Class.forName("com.mysql.jdbc.Driver");

            String databaseName = "monitoring_leaks_db";
            String host = "localhost";
            String port = "3306";
            String username = "root";
            String password = "";

            setDatabaseConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password));

            statement = getDatabaseConnection().createStatement();
            statement.executeUpdate("create database if not exists " + databaseName);
            statement.close();
            getDatabaseConnection().close();

            setDatabaseConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password));


            statement = getDatabaseConnection().createStatement();
            statement.executeUpdate(createTableName);



            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            //log(Level.WARNING, "There was an issue involving the MySQL connection.");
        }
    }


    private void setDatabaseConnection(Connection connection) {
        this.databaseConnection = connection;
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }
}
