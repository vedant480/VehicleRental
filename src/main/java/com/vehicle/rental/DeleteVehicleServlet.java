package com.vehicle.rental;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/delete-vehicle")
public class DeleteVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int vehicleId =
                Integer.parseInt(request.getParameter("vehicleId"));

        VehicleDAO dao = new VehicleDAO();

        if (dao.deleteVehicle(vehicleId)) {
            response.sendRedirect("manage-vehicles.jsp");
        } else {
            response.sendRedirect(
                    "manage-vehicles.jsp?error=Failed to delete vehicle");
        }
    }
}