package com.vehicle.rental;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("JDBC connection test successful!");
        } else {
            System.out.println("JDBC connection failed!");
        }
    }
}
