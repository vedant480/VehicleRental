<%@ page import="java.util.List" %>
<%@ page import="com.vehicle.rental.Rental" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Rentals</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
        }

        .navbar {
            background-color: #222;
            padding: 15px;
        }

        .navbar a {
            color: white;
            text-decoration: none;
            margin-right: 20px;
        }

        .container {
            width: 90%;
            margin: 30px auto;
        }

        h2 {
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        .return-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 15px;
            cursor: pointer;
            border-radius: 4px;
        }

        .completed {
            color: green;
            font-weight: bold;
        }
    </style>
</head>

<body>

<div class="navbar">

    <a href="vehicles">Vehicles</a>
    <a href="my-rentals">My Rentals</a>
    <a href="logout">Logout</a>

</div>

<div class="container">

    <h2>My Rental History</h2>

    <%
        String error = request.getParameter("error");

        if (error != null) {
    %>

        <p style="color:red; text-align:center;">
            <%= error %>
        </p>

    <%
        }

        List<Rental> rentals =
                (List<Rental>) request.getAttribute("rentals");
    %>

    <table>

        <tr>
            <th>Rental ID</th>
            <th>Vehicle ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            if (rentals != null) {

                for (Rental rental : rentals) {
        %>

        <tr>

            <td><%= rental.getRentalId() %></td>

            <td><%= rental.getVehicleId() %></td>

            <td><%= rental.getStartDate() %></td>

            <td><%= rental.getEndDate() %></td>

            <td>₹ <%= rental.getTotalAmount() %></td>

            <td><%= rental.getStatus() %></td>

            <td>

                <%
                    if ("ACTIVE".equals(rental.getStatus())) {
                %>

                <form action="return" method="post">

                    <input type="hidden"
                           name="rentalId"
                           value="<%= rental.getRentalId() %>">

                    <input type="hidden"
                           name="vehicleId"
                           value="<%= rental.getVehicleId() %>">

                    <button class="return-btn"
                            type="submit">
                        Return
                    </button>

                </form>

                <%
                    } else {
                %>

                    <span class="completed">
                        Completed
                    </span>

                <%
                    }
                %>

            </td>

        </tr>

        <%
                }
            }
        %>

    </table>

</div>

</body>
</html>