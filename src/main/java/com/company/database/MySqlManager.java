package com.company.database;

import com.company.objects.LeakInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlManager {

    public static void createPlayer(LeakInfo leakInfo, MySql mySql) {
        try {


            PreparedStatement insert = mySql.getDatabaseConnection()
                    .prepareStatement("INSERT INTO " + "SiteDate"
                            + " (nameResourse,author,version,description,downloads,views,firstRelease,lastUpdate,rating,siteUrl) VALUES (?,?,?,?,?,?,?,?,?,?)");
            insert.setString(1, leakInfo.getNameResource());
            insert.setString(2, leakInfo.getAuthor());
            insert.setInt(3, leakInfo.getViews());
            insert.setString(4, leakInfo.getDescription());
            insert.setInt(5, leakInfo.getDownloads());
            insert.setInt(6, leakInfo.getViews());
            //insert.setDate(6, leakInfo.getFirstRelease());
            //insert.setDate(6, leakInfo.getLastUpdate());
            insert.setDouble(6, leakInfo.getRating());
            insert.setString(6, leakInfo.getSiteUrl());
            insert.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
