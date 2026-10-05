<%@ page import="com.vehicle.rental.Vehicle" %>
<%@ page import="com.vehicle.rental.VehicleDAO" %>

<%
    // Check login
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idParameter = request.getParameter("id");

    if (idParameter == null) {
        response.sendRedirect("manage-vehicles.jsp");
        return;
    }

    int vehicleId = Integer.parseInt(idParameter);

    VehicleDAO dao = new VehicleDAO();

    Vehicle selectedVehicle = null;

    for (Vehicle v : dao.getAllVehicles()) {

        if (v.getVehicleId() == vehicleId) {
            selectedVehicle = v;
            break;
        }
    }

    if (selectedVehicle == null) {
        response.sendRedirect("manage-vehicles.jsp?error=Vehicle not found");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Edit Vehicle</title>

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
            width: 500px;
            margin: 40px auto;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-top: 0;
            text-align: center;
        }

        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input,
        select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        .update-btn {
            width: 100%;
            margin-top: 25px;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .update-btn:hover {
            background-color: #0056b3;
        }

        .back-btn {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #555;
            text-decoration: none;
        }

        .info {
            background-color: #fff3cd;
            color: #856404;
            padding: 12px;
            border-radius: 5px;
            margin-top: 20px;
            font-size: 14px;
        }

    </style>

</head>

<body>

<div class="header">

    <h1>Edit Vehicle</h1>

</div>


<div class="container">

    <div class="card">

        <h2>Update Vehicle</h2>


        <form action="update-vehicle" method="post">

            <!-- Vehicle ID -->

            <input
                type="hidden"
                name="vehicleId"
                value="<%= selectedVehicle.getVehicleId() %>">


            <label>
                Vehicle Number
            </label>

            <input
                type="text"
                name="vehicleNumber"
                value="<%= selectedVehicle.getVehicleNumber() %>"
                required>


            <label>
                Brand
            </label>

            <input
                type="text"
                name="brand"
                value="<%= selectedVehicle.getBrand() %>"
                required>


            <label>
                Model
            </label>

            <input
                type="text"
                name="model"
                value="<%= selectedVehicle.getModel() %>"
                required>


            <label>
                Vehicle Type
            </label>

            <select
                name="vehicleType"
                required>

                <option
                    value="Car"
                    <%= "Car".equalsIgnoreCase(selectedVehicle.getVehicleType()) ? "selected" : "" %>>
                    Car
                </option>

                <option
                    value="Bike"
                    <%= "Bike".equalsIgnoreCase(selectedVehicle.getVehicleType()) ? "selected" : "" %>>
                    Bike
                </option>

                <option
                    value="Scooter"
                    <%= "Scooter".equalsIgnoreCase(selectedVehicle.getVehicleType()) ? "selected" : "" %>>
                    Scooter
                </option>

                <option
                    value="SUV"
                    <%= "SUV".equalsIgnoreCase(selectedVehicle.getVehicleType()) ? "selected" : "" %>>
                    SUV
                </option>

            </select>


            <label>
                Price Per Day
            </label>

            <input
                type="number"
                name="pricePerDay"
                value="<%= selectedVehicle.getPricePerDay() %>"
                min="1"
                step="0.01"
                required>


            <label>
                Vehicle Status
            </label>

            <select
                name="status"
                required>

                <option
                    value="AVAILABLE"
                    <%= "AVAILABLE".equalsIgnoreCase(selectedVehicle.getStatus()) ? "selected" : "" %>>
                    AVAILABLE
                </option>

                <option
                    value="RENTED"
                    <%= "RENTED".equalsIgnoreCase(selectedVehicle.getStatus()) ? "selected" : "" %>>
                    RENTED
                </option>

            </select>


            <button
                type="submit"
                class="update-btn">

                Update Vehicle

            </button>

        </form>


        <div class="info">

            <strong>Important:</strong>

            If you change a vehicle from
            <b>RENTED</b> to
            <b>AVAILABLE</b>,
            the active customer rental for this vehicle
            will automatically be marked as
            <b>COMPLETED</b>.

        </div>


        <a
            href="manage-vehicles.jsp"
            class="back-btn">

            ← Back to Manage Vehicles

        </a>

    </div>

</div>

</body>

</html>