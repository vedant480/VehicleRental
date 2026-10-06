package com.vehicle.rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    // =========================================================
    // RENT VEHICLE
    // =========================================================

    public boolean rentVehicle(Rental rental) {

        String checkSql =
                "SELECT status FROM vehicles "
                + "WHERE vehicle_id = ?";

        String rentalSql =
                "INSERT INTO rentals "
                + "(user_id, vehicle_id, start_date, end_date, "
                + "total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String vehicleSql =
                "UPDATE vehicles "
                + "SET status = 'RENTED' "
                + "WHERE vehicle_id = ? "
                + "AND status = 'AVAILABLE'";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement checkPs =
                        con.prepareStatement(checkSql);

                PreparedStatement rentalPs =
                        con.prepareStatement(rentalSql);

                PreparedStatement vehiclePs =
                        con.prepareStatement(vehicleSql)
            ) {

                // Check vehicle availability
                checkPs.setInt(1, rental.getVehicleId());

                ResultSet rs = checkPs.executeQuery();

                if (!rs.next()
                        || !"AVAILABLE".equalsIgnoreCase(
                                rs.getString("status"))) {

                    con.rollback();
                    return false;
                }

                // Insert rental
                rentalPs.setInt(1, rental.getUserId());
                rentalPs.setInt(2, rental.getVehicleId());
                rentalPs.setDate(3, rental.getStartDate());
                rentalPs.setDate(4, rental.getEndDate());
                rentalPs.setDouble(5, rental.getTotalAmount());
                rentalPs.setString(6, "ACTIVE");

                rentalPs.executeUpdate();

                // Mark vehicle rented
                vehiclePs.setInt(1, rental.getVehicleId());

                int vehicleUpdated =
                        vehiclePs.executeUpdate();

                if (vehicleUpdated == 0) {
                    con.rollback();
                    return false;
                }

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


    // =========================================================
    // CUSTOMER REQUESTS RETURN
    // =========================================================

    public boolean requestReturn(int rentalId, int userId) {

        String sql =
                "UPDATE rentals "
                + "SET status = 'RETURN_REQUESTED' "
                + "WHERE rental_id = ? "
                + "AND user_id = ? "
                + "AND status = 'ACTIVE'";

        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, rentalId);
            ps.setInt(2, userId);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    // =========================================================
    // ADMIN CONFIRMS PHYSICAL RETURN
    // =========================================================

    public boolean confirmReturn(int rentalId) {

        String rentalSql =
                "UPDATE rentals "
                + "SET status = 'COMPLETED' "
                + "WHERE rental_id = ? "
                + "AND status = 'RETURN_REQUESTED'";

        String vehicleSql =
                "UPDATE vehicles "
                + "SET status = 'AVAILABLE' "
                + "WHERE vehicle_id = ? "
                + "AND status = 'RENTED'";

        String vehicleIdSql =
                "SELECT vehicle_id "
                + "FROM rentals "
                + "WHERE rental_id = ? "
                + "AND status = 'RETURN_REQUESTED'";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement vehicleIdPs =
                        con.prepareStatement(vehicleIdSql);

                PreparedStatement rentalPs =
                        con.prepareStatement(rentalSql);

                PreparedStatement vehiclePs =
                        con.prepareStatement(vehicleSql)
            ) {

                // Find the vehicle belonging to this rental
                vehicleIdPs.setInt(1, rentalId);

                ResultSet rs =
                        vehicleIdPs.executeQuery();

                if (!rs.next()) {
                    con.rollback();
                    return false;
                }

                int vehicleId =
                        rs.getInt("vehicle_id");


                // Complete rental
                rentalPs.setInt(1, rentalId);

                int rentalUpdated =
                        rentalPs.executeUpdate();

                if (rentalUpdated == 0) {
                    con.rollback();
                    return false;
                }


                // Make vehicle available again
                vehiclePs.setInt(1, vehicleId);

                int vehicleUpdated =
                        vehiclePs.executeUpdate();

                if (vehicleUpdated == 0) {
                    con.rollback();
                    return false;
                }


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


    // =========================================================
    // GET USER RENTALS
    // =========================================================

    public List<Rental> getUserRentals(int userId) {

        List<Rental> rentals =
                new ArrayList<>();

        String sql =
                "SELECT r.*, "
                + "v.vehicle_number, "
                + "v.brand, "
                + "v.model "
                + "FROM rentals r "
                + "JOIN vehicles v "
                + "ON r.vehicle_id = v.vehicle_id "
                + "WHERE r.user_id = ? "
                + "ORDER BY r.rental_id DESC";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Rental rental =
                        new Rental();

                rental.setRentalId(
                        rs.getInt("rental_id"));

                rental.setUserId(
                        rs.getInt("user_id"));

                rental.setVehicleId(
                        rs.getInt("vehicle_id"));

                rental.setStartDate(
                        rs.getDate("start_date"));

                rental.setEndDate(
                        rs.getDate("end_date"));

                rental.setTotalAmount(
                        rs.getDouble("total_amount"));

                rental.setStatus(
                        rs.getString("status"));

                rental.setVehicleNumber(
                        rs.getString("vehicle_number"));

                rental.setVehicleBrand(
                        rs.getString("brand"));

                rental.setVehicleModel(
                        rs.getString("model"));

                rentals.add(rental);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rentals;
    }


    // =========================================================
    // GET ALL RENTALS FOR ADMIN
    // =========================================================

    public List<Rental> getAllRentals() {

        List<Rental> rentals =
                new ArrayList<>();

        String sql =
                "SELECT r.*, "
                + "u.name AS user_name, "
                + "u.email AS user_email, "
                + "v.vehicle_number, "
                + "v.brand, "
                + "v.model "
                + "FROM rentals r "
                + "JOIN users u "
                + "ON r.user_id = u.user_id "
                + "JOIN vehicles v "
                + "ON r.vehicle_id = v.vehicle_id "
                + "ORDER BY r.rental_id DESC";

        try (
            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {

            while (rs.next()) {

                Rental rental =
                        new Rental();

                rental.setRentalId(
                        rs.getInt("rental_id"));

                rental.setUserId(
                        rs.getInt("user_id"));

                rental.setVehicleId(
                        rs.getInt("vehicle_id"));

                rental.setStartDate(
                        rs.getDate("start_date"));

                rental.setEndDate(
                        rs.getDate("end_date"));

                rental.setTotalAmount(
                        rs.getDouble("total_amount"));

                rental.setStatus(
                        rs.getString("status"));

                rental.setUserName(
                        rs.getString("user_name"));

                rental.setUserEmail(
                        rs.getString("user_email"));

                rental.setVehicleNumber(
                        rs.getString("vehicle_number"));

                rental.setVehicleBrand(
                        rs.getString("brand"));

                rental.setVehicleModel(
                        rs.getString("model"));

                rentals.add(rental);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rentals;
    }
}