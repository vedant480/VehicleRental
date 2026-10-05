package com.vehicle.rental;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/update-vehicle")
public class UpdateVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check whether user is logged in
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");

            return;
        }


        try {

            // Get vehicle ID
            int vehicleId = Integer.parseInt(
                    request.getParameter("vehicleId")
            );


            // Get vehicle details
            String vehicleNumber =
                    request.getParameter("vehicleNumber");

            String brand =
                    request.getParameter("brand");

            String model =
                    request.getParameter("model");

            String vehicleType =
                    request.getParameter("vehicleType");

            double pricePerDay =
                    Double.parseDouble(
                            request.getParameter("pricePerDay")
                    );

            String status =
                    request.getParameter("status");


            // Create Vehicle object
            Vehicle vehicle = new Vehicle();

            vehicle.setVehicleId(vehicleId);

            vehicle.setVehicleNumber(vehicleNumber);

            vehicle.setBrand(brand);

            vehicle.setModel(model);

            vehicle.setVehicleType(vehicleType);

            vehicle.setPricePerDay(pricePerDay);

            vehicle.setStatus(status);


            // Update database
            VehicleDAO dao = new VehicleDAO();

            boolean updated =
                    dao.updateVehicle(vehicle);


            if (updated) {

                /*
                 * VehicleDAO.updateVehicle()
                 *
                 * updates the vehicles table.
                 *
                 * If status is AVAILABLE,
                 * it also changes the ACTIVE
                 * rental for this vehicle to COMPLETED.
                 */

                response.sendRedirect(
                        "manage-vehicles.jsp"
                );

            } else {

                response.sendRedirect(
                        "manage-vehicles.jsp?error=Failed to update vehicle"
                );
            }


        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "manage-vehicles.jsp?error=Invalid vehicle data"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "manage-vehicles.jsp?error=Something went wrong"
            );
        }
    }
}