package com.vehicle.rental;

import java.io.IOException;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/rent")
public class RentVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {

            int vehicleId = Integer.parseInt(
                    request.getParameter("vehicleId"));

            Date startDate = Date.valueOf(
                    request.getParameter("startDate"));

            Date endDate = Date.valueOf(
                    request.getParameter("endDate"));

            // Calculate number of rental days
            long days = ChronoUnit.DAYS.between(
                    startDate.toLocalDate(),
                    endDate.toLocalDate());

            // At least 1 day
            if (days <= 0) {
                days = 1;
            }

            // Get vehicle details
            VehicleDAO vehicleDAO = new VehicleDAO();

            Vehicle selectedVehicle = null;

            for (Vehicle v : vehicleDAO.getAllVehicles()) {

                if (v.getVehicleId() == vehicleId) {
                    selectedVehicle = v;
                    break;
                }
            }

            if (selectedVehicle == null) {
                response.sendRedirect(
                        "vehicles?error=Vehicle not found");
                return;
            }

            // Check vehicle availability
            if (!"AVAILABLE".equalsIgnoreCase(
                    selectedVehicle.getStatus())) {

                response.sendRedirect(
                        "vehicles?error=Vehicle is not available");
                return;
            }

            // Calculate total amount
            double totalAmount =
                    days * selectedVehicle.getPricePerDay();

            Rental rental = new Rental();

            rental.setUserId(user.getUserId());
            rental.setVehicleId(vehicleId);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setTotalAmount(totalAmount);
            rental.setStatus("ACTIVE");

            RentalDAO dao = new RentalDAO();

            if (dao.rentVehicle(rental)) {

                response.sendRedirect("my-rentals");

            } else {

                response.sendRedirect(
                        "vehicles?error=Rental failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "vehicles?error=Invalid rental details");
        }
    }
}