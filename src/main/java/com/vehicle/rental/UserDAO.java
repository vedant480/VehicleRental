package com.vehicle.rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            System.out.println("REGISTRATION ERROR:");
            e.printStackTrace();

            return false;
        }
    }


    public User loginUser(String email, String password) {

        String sql = "SELECT * FROM users "
                   + "WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(
                        rs.getInt("user_id"));

                user.setName(
                        rs.getString("name"));

                user.setEmail(
                        rs.getString("email"));

                user.setPassword(
                        rs.getString("password"));

                user.setRole(
                        rs.getString("role"));

                return user;
            }

        } catch (Exception e) {

            System.out.println("LOGIN ERROR:");
            e.printStackTrace();
        }

        return null;
    }
}