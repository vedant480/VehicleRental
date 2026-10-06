package com.vehicle.rental;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/confirm-return")
public class ConfirmReturnServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
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

        // Only ADMIN can confirm physical return
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect("vehicles");
            return;
        }

        try {

            int rentalId =
                    Integer.parseInt(
                            request.getParameter("rentalId"));

            RentalDAO dao =
                    new RentalDAO();

            boolean confirmed =
                    dao.confirmReturn(rentalId);

            if (confirmed) {

                response.sendRedirect(
                        "admin-rentals?message=Vehicle return confirmed");

            } else {

                response.sendRedirect(
                        "admin-rentals?error=Return confirmation failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "admin-rentals?error=Invalid rental");
        }
    }
}