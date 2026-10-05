<%@ page import="java.util.List" %>
<%@ page import="com.vehicle.rental.Vehicle" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Vehicles</title>

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

        .rent-btn {
            background-color: #28a745;
            color: white;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 4px;
        }

        .unavailable {
            color: red;
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

    <h2>Available Vehicles</h2>

    <%
        String error = request.getParameter("error");

        if (error != null) {
    %>

        <p style="color:red; text-align:center;">
            <%= error %>
        </p>

    <%
        }

        List<Vehicle> vehicles =
                (List<Vehicle>) request.getAttribute("vehicles");
    %>

    <table>

        <tr>
            <th>ID</th>
            <th>Vehicle Number</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Type</th>
            <th>Price / Day</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <%
            if (vehicles != null) {

                for (Vehicle vehicle : vehicles) {
        %>

        <tr>

            <td><%= vehicle.getVehicleId() %></td>

            <td><%= vehicle.getVehicleNumber() %></td>

            <td><%= vehicle.getBrand() %></td>

            <td><%= vehicle.getModel() %></td>

            <td><%= vehicle.getVehicleType() %></td>

            <td>₹ <%= vehicle.getPricePerDay() %></td>

            <td><%= vehicle.getStatus() %></td>

            <td>

                <%
                    if ("AVAILABLE".equals(vehicle.getStatus())) {
                %>

                    <a class="rent-btn"
                       href="rent.jsp?vehicleId=<%= vehicle.getVehicleId() %>&price=<%= vehicle.getPricePerDay() %>">
                        Rent
                    </a>

                <%
                    } else {
                %>

                    <span class="unavailable">
                        Not Available
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