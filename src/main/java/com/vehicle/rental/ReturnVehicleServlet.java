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

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("user") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User user =
                (User) session.getAttribute("user");

        try {

            int rentalId =
                    Integer.parseInt(
                            request.getParameter("rentalId"));

            RentalDAO dao =
                    new RentalDAO();

            boolean requested =
                    dao.requestReturn(
                            rentalId,
                            user.getUserId());

            if (requested) {

                response.sendRedirect(
                        "my-rentals?message=Return request sent to admin");

            } else {

                response.sendRedirect(
                        "my-rentals?error=Return request failed");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    "my-rentals?error=Invalid rental");
        }
    }
}