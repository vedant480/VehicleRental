package com.vehicle.rental;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add-vehicle")
public class AddVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String vehicleNumber = request.getParameter("vehicleNumber");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String vehicleType = request.getParameter("vehicleType");
        double pricePerDay =
                Double.parseDouble(request.getParameter("pricePerDay"));

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setVehicleType(vehicleType);
        vehicle.setPricePerDay(pricePerDay);
        vehicle.setStatus("AVAILABLE");

        VehicleDAO dao = new VehicleDAO();

        if (dao.addVehicle(vehicle)) {
            response.sendRedirect("manage-vehicles.jsp");
        } else {
            response.sendRedirect(
                    "manage-vehicles.jsp?error=Failed to add vehicle");
        }
    }
}