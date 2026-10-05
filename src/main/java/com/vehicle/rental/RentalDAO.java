package com.vehicle.rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    public boolean rentVehicle(Rental rental) {

        String rentalSql = "INSERT INTO rentals "
                + "(user_id, vehicle_id, start_date, end_date, total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String vehicleSql = "UPDATE vehicles SET status = 'RENTED' "
                + "WHERE vehicle_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(rentalSql);
                 PreparedStatement ps2 = con.prepareStatement(vehicleSql)) {

                ps1.setInt(1, rental.getUserId());
                ps1.setInt(2, rental.getVehicleId());
                ps1.setDate(3, rental.getStartDate());
                ps1.setDate(4, rental.getEndDate());
                ps1.setDouble(5, rental.getTotalAmount());
                ps1.setString(6, "ACTIVE");

                ps1.executeUpdate();

                ps2.setInt(1, rental.getVehicleId());
                ps2.executeUpdate();

                con.commit();

                return true;

            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean returnVehicle(int rentalId, int vehicleId) {

        String rentalSql = "UPDATE rentals SET status = 'COMPLETED' "
                + "WHERE rental_id = ?";

        String vehicleSql = "UPDATE vehicles SET status = 'AVAILABLE' "
                + "WHERE vehicle_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(rentalSql);
                 PreparedStatement ps2 = con.prepareStatement(vehicleSql)) {

                ps1.setInt(1, rentalId);
                ps1.executeUpdate();

                ps2.setInt(1, vehicleId);
                ps2.executeUpdate();

                con.commit();

                return true;

            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Rental> getUserRentals(int userId) {

        List<Rental> rentals = new ArrayList<>();

        String sql = "SELECT * FROM rentals WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Rental rental = new Rental();

                rental.setRentalId(rs.getInt("rental_id"));
                rental.setUserId(rs.getInt("user_id"));
                rental.setVehicleId(rs.getInt("vehicle_id"));
                rental.setStartDate(rs.getDate("start_date"));
                rental.setEndDate(rs.getDate("end_date"));
                rental.setTotalAmount(rs.getDouble("total_amount"));
                rental.setStatus(rs.getString("status"));

                rentals.add(rental);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentals;
    }
}