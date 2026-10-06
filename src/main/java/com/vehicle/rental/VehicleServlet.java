package com.vehicle.rental;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/vehicles")
public class VehicleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String vehicleType =
                request.getParameter("vehicleType");

        String maxPriceParameter =
                request.getParameter("maxPrice");

        Double maxPrice = null;

        try {

            if (maxPriceParameter != null
                    && !maxPriceParameter.trim().isEmpty()) {

                maxPrice =
                        Double.parseDouble(maxPriceParameter);
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Please enter a valid maximum price.");

            maxPrice = null;
        }

        VehicleDAO dao = new VehicleDAO();

        List<Vehicle> vehicles =
                dao.searchAvailableVehicles(
                        vehicleType,
                        maxPrice);

        request.setAttribute(
                "vehicles",
                vehicles);

        request.setAttribute(
                "selectedType",
                vehicleType);

        request.setAttribute(
                "selectedMaxPrice",
                maxPriceParameter);

        request.getRequestDispatcher(
                "vehicles.jsp")
                .forward(request, response);
    }
}