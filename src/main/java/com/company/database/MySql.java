package com.company.database;

import com.company.objects.LeakInfo;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class MySql {
    private static MySql instance = new MySql();
    private Connection databaseConnection;
    private String createTableName;

    public static MySql getInstance() {
        return instance;
    }

    public boolean setup() {

        createTableName = "create table if not exists SiteDate (resourceID varchar(100) PRIMARY KEY,nameResource varchar(1000), author varchar(1000), version varchar(1000), description varchar(3000),"
                + " downloads int, views int, firstRelease date, lastUpdate date, rating varchar(100), siteUrl varchar(1000))";

        long databaseStartTime = System.currentTimeMillis();
        setupDatabase();
        if (getDatabaseConnection() != null){
            System.out.println("Подключение прошло успешно. (Затрачено " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - databaseStartTime) + " сек)");
        }
        else {
            System.out.println("Не удалось подключиться. (Затрачено " + (System.currentTimeMillis() - databaseStartTime) + "ms)");
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



            System.out.println("[Database] ( Connected ) ( MySQL )");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String databaseName = "monitoring_leaks_db";
            String host = "localhost";
            String port = "3306";
            String username = "root";
            String password = "";

            setDatabaseConnection(DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" +
                            port + "/" +
                            databaseName + "?",
                    username,
                    password));

            statement = getDatabaseConnection().createStatement();
            statement.executeUpdate(createTableName);



            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            //log(Level.WARNING, "There was an issue involving the MySQL connection.");
        }
    }

    public static void addLeakInfo(LeakInfo leakInfo, MySql mySql) {
        try {


            PreparedStatement insert = mySql.getDatabaseConnection()
                    .prepareStatement("INSERT INTO " + "SiteDate"
                            + " (nameResource,author,version,description,downloads,views,firstRelease,lastUpdate,rating,siteUrl,resourceID) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?) " +
                            "ON DUPLICATE KEY UPDATE nameResource=VALUES(nameResource),author=VALUES(author),version=VALUES(version)," +
                            "description=VALUES(description),downloads=VALUES(downloads),views=VALUES(views),firstRelease=VALUES(firstRelease)," +
                            "lastUpdate=VALUES(lastUpdate),rating=VALUES(rating),siteUrl=VALUES(siteUrl)");
            insert.setString(1, leakInfo.getNameResource());
            insert.setString(2, leakInfo.getAuthor());
            insert.setString(3, leakInfo.getVersion());
            insert.setString(4, leakInfo.getDescription());
            insert.setInt(5, leakInfo.getDownloads());
            insert.setInt(6, leakInfo.getViews());
            insert.setDate(7, new java.sql.Date(leakInfo.getFirstRelease().getTime()));
            insert.setDate(8, new java.sql.Date(leakInfo.getLastUpdate().getTime()));
            insert.setString(9, leakInfo.getRating());
            insert.setString(10, leakInfo.getSiteUrl());
            insert.setString(11, leakInfo.getResourceID());
            insert.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setDatabaseConnection(Connection connection) {
        this.databaseConnection = connection;
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }
}
