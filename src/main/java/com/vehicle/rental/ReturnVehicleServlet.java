package com.vehicle.rental;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/return")
public class ReturnVehicleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int rentalId = Integer.parseInt(
                request.getParameter("rentalId"));

        int vehicleId = Integer.parseInt(
                request.getParameter("vehicleId"));

        RentalDAO dao = new RentalDAO();

        if (dao.returnVehicle(rentalId, vehicleId)) {
            response.sendRedirect("my-rentals");
        } else {
            response.sendRedirect("my-rentals?error=Return failed");
        }
    }
}