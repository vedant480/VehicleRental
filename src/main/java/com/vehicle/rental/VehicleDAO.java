package com.vehicle.rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public List<Vehicle> getAllVehicles() {

        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM vehicles";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Vehicle vehicle = new Vehicle();

                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                vehicle.setBrand(rs.getString("brand"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setVehicleType(rs.getString("vehicle_type"));
                vehicle.setPricePerDay(rs.getDouble("price_per_day"));
                vehicle.setStatus(rs.getString("status"));

                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }


    public boolean addVehicle(Vehicle vehicle) {

        String sql = "INSERT INTO vehicles "
                + "(vehicle_number, brand, model, vehicle_type, price_per_day, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicle.getVehicleNumber());
            ps.setString(2, vehicle.getBrand());
            ps.setString(3, vehicle.getModel());
            ps.setString(4, vehicle.getVehicleType());
            ps.setDouble(5, vehicle.getPricePerDay());
            ps.setString(6, vehicle.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {

        String vehicleSql = "UPDATE vehicles SET "
                + "vehicle_number=?, brand=?, model=?, "
                + "vehicle_type=?, price_per_day=?, status=? "
                + "WHERE vehicle_id=?";

        String rentalSql = "UPDATE rentals SET status='COMPLETED' "
                + "WHERE vehicle_id=? AND status='ACTIVE'";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement psVehicle =
                        con.prepareStatement(vehicleSql);

                PreparedStatement psRental =
                        con.prepareStatement(rentalSql)
            ) {

                // Update vehicle
                psVehicle.setString(1, vehicle.getVehicleNumber());
                psVehicle.setString(2, vehicle.getBrand());
                psVehicle.setString(3, vehicle.getModel());
                psVehicle.setString(4, vehicle.getVehicleType());
                psVehicle.setDouble(5, vehicle.getPricePerDay());
                psVehicle.setString(6, vehicle.getStatus());
                psVehicle.setInt(7, vehicle.getVehicleId());

                int vehicleUpdated =
                        psVehicle.executeUpdate();


                // If admin sets vehicle to AVAILABLE,
                // complete the active customer rental.
                if ("AVAILABLE".equalsIgnoreCase(vehicle.getStatus())) {

                    psRental.setInt(1, vehicle.getVehicleId());

                    psRental.executeUpdate();
                }


                // Save both changes
                con.commit();


                return vehicleUpdated > 0;


            } catch (Exception e) {

                // Undo both changes if anything fails
                con.rollback();

                e.printStackTrace();

                return false;
            }


        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    
    public boolean deleteVehicle(int vehicleId) {

        String sql = "DELETE FROM vehicles WHERE vehicle_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}