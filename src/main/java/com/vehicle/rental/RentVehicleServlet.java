package com.vehicle.rental;

import java.io.IOException;
import java.sql.Date;

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

        int vehicleId = Integer.parseInt(
                request.getParameter("vehicleId"));

        Date startDate = Date.valueOf(
                request.getParameter("startDate"));

        Date endDate = Date.valueOf(
                request.getParameter("endDate"));

        double totalAmount = Double.parseDouble(
                request.getParameter("totalAmount"));

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
            response.sendRedirect("vehicles?error=Rental failed");
        }
    }
}