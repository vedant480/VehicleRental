package com.vehicle.rental;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/my-rentals")
public class MyRentalsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        RentalDAO dao = new RentalDAO();

        List<Rental> rentals =
                dao.getUserRentals(user.getUserId());

        request.setAttribute("rentals", rentals);

        request.getRequestDispatcher("my-rentals.jsp")
               .forward(request, response);
    }
}