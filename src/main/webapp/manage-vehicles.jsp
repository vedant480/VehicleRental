<%@ page import="java.util.List" %>
<%@ page import="com.vehicle.rental.Vehicle" %>
<%@ page import="com.vehicle.rental.VehicleDAO" %>

<%
    // Check whether user is logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    VehicleDAO dao = new VehicleDAO();
    List<Vehicle> vehicles = dao.getAllVehicles();

    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Manage Vehicles</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #222;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .container {
            width: 90%;
            margin: 30px auto;
        }

        .card {
            background: white;
            padding: 25px;
            margin-bottom: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        h2 {
            margin-top: 0;
        }

        .form-row {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 15px;
        }

        input,
        select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        input {
            flex: 1;
            min-width: 150px;
        }

        select {
            min-width: 150px;
        }

        button {
            padding: 10px 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .add-btn {
            background-color: #28a745;
            color: white;
        }

        .edit-btn {
            background-color: #007bff;
            color: white;
            text-decoration: none;
            padding: 8px 14px;
            border-radius: 5px;
        }

        .delete-btn {
            background-color: #dc3545;
            color: white;
        }

        .back-btn {
            background-color: #555;
            color: white;
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 5px;
            display: inline-block;
            margin-bottom: 20px;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th,
        td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #343a40;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f8f8f8;
        }

        .available {
            color: green;
            font-weight: bold;
        }

        .rented {
            color: red;
            font-weight: bold;
        }

        .actions {
            display: flex;
            justify-content: center;
            gap: 8px;
        }

    </style>

</head>

<body>

<div class="header">
    <h1>Manage Vehicles</h1>
</div>

<div class="container">

    <a href="admin-dashboard.jsp" class="back-btn">
        Back to Dashboard
    </a>

    <% if (error != null) { %>

        <div class="error">
            <%= error %>
        </div>

    <% } %>


    <!-- ADD VEHICLE -->

    <div class="card">

        <h2>Add New Vehicle</h2>

        <form action="add-vehicle" method="post">

            <div class="form-row">

                <input
                    type="text"
                    name="vehicleNumber"
                    placeholder="Vehicle Number"
                    required>

                <input
                    type="text"
                    name="brand"
                    placeholder="Brand"
                    required>

                <input
                    type="text"
                    name="model"
                    placeholder="Model"
                    required>

            </div>

            <div class="form-row">

                <select name="vehicleType" required>

                    <option value="">Select Type</option>

                    <option value="Car">
                        Car
                    </option>

                    <option value="Bike">
                        Bike
                    </option>

                    <option value="Scooter">
                        Scooter
                    </option>

                    <option value="SUV">
                        SUV
                    </option>

                </select>

                <input
                    type="number"
                    name="pricePerDay"
                    placeholder="Price Per Day"
                    min="1"
                    step="0.01"
                    required>

                <button
                    type="submit"
                    class="add-btn">

                    Add Vehicle

                </button>

            </div>

        </form>

    </div>


    <!-- VEHICLE LIST -->

    <div class="card">

        <h2>All Vehicles</h2>

        <% if (vehicles == null || vehicles.isEmpty()) { %>

            <p>No vehicles found.</p>

        <% } else { %>

            <table>

                <tr>

                    <th>ID</th>
                    <th>Vehicle Number</th>
                    <th>Brand</th>
                    <th>Model</th>
                    <th>Type</th>
                    <th>Price / Day</th>
                    <th>Status</th>
                    <th>Actions</th>

                </tr>


                <% for (Vehicle vehicle : vehicles) { %>

                    <tr>

                        <td>
                            <%= vehicle.getVehicleId() %>
                        </td>

                        <td>
                            <%= vehicle.getVehicleNumber() %>
                        </td>

                        <td>
                            <%= vehicle.getBrand() %>
                        </td>

                        <td>
                            <%= vehicle.getModel() %>
                        </td>

                        <td>
                            <%= vehicle.getVehicleType() %>
                        </td>

                        <td>
                            Rs.<%= vehicle.getPricePerDay() %>
                        </td>

                        <td>

                            <% if ("AVAILABLE".equalsIgnoreCase(vehicle.getStatus())) { %>

                                <span class="available">
                                    AVAILABLE
                                </span>

                            <% } else { %>

                                <span class="rented">
                                    <%= vehicle.getStatus() %>
                                </span>

                            <% } %>

                        </td>

                        <td>

                            <div class="actions">

                                <a
                                    href="edit-vehicle.jsp?id=<%= vehicle.getVehicleId() %>"
                                    class="edit-btn">

                                    Edit

                                </a>


                                <form
                                    action="delete-vehicle"
                                    method="post"
                                    onsubmit="return confirm('Are you sure you want to delete this vehicle?');">

                                    <input
                                        type="hidden"
                                        name="vehicleId"
                                        value="<%= vehicle.getVehicleId() %>">

                                    <button
                                        type="submit"
                                        class="delete-btn">

                                        Delete

                                    </button>

                                </form>

                            </div>

                        </td>

                    </tr>

                <% } %>

            </table>

        <% } %>

    </div>

</div>

</body>
</html>