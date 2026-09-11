package application;

import java.sql.Connection;
import database.DBConnection;

public class TestConnection {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("SQLite Connected Successfully!");
        } else {
            System.out.println("Connection Failed!");
        }
    }
}