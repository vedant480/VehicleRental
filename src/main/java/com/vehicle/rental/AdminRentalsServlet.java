package com.vehicle.rental;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin-rentals")
public class AdminRentalsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User user =
                (User) session.getAttribute("user");

        // Only ADMIN can access this page
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("vehicles");
            return;
        }

        RentalDAO dao =
                new RentalDAO();

        List<Rental> rentals =
                dao.getAllRentals();

        request.setAttribute(
                "rentals",
                rentals);

        request.getRequestDispatcher(
                "admin-rentals.jsp")
                .forward(request, response);
    }
}