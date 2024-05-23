package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    private static String user_id;
    public  static String url="jdbc:mysql://localhost:3307/camp";
    public  static String user="root";
    public static String password ="123456";

    public static ResultSet getFromDB(String qry) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con= DriverManager.getConnection(url,user,password);
            statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(qry);
            if (!rs.next()) {
                return null;
            }else{
                rs.beforeFirst();
                return rs;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addToDB(String sql) {
        Connection con = null;
        Statement statement = null;
        try{
            con= DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteFromDB(String sql) {
        Connection con = null;
        Statement statement = null;
        try{
            con= DriverManager.getConnection(url,user,password);
            statement = con.createStatement();
            statement.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static void setUser_id(String user_id) {
        DBUtils.user_id = user_id;
    }

    public static String getUser_id() {
        return user_id;
    }
}
