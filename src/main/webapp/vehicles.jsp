<%@ page import="java.util.List" %>
<%@ page import="com.vehicle.rental.Vehicle" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Vehicle> vehicles =
            (List<Vehicle>) request.getAttribute("vehicles");

    String selectedType =
            (String) request.getAttribute("selectedType");

    String selectedMaxPrice =
            (String) request.getAttribute("selectedMaxPrice");

    String error =
            (String) request.getAttribute("error");
%>

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
    max-width: 1200px;
    margin: 30px auto;
}

/* Search box */

.search-box {
    background-color: white;
    padding: 25px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    margin-bottom: 30px;
}

.search-box h2 {
    margin-top: 0;
}

.filters {
    display: flex;
    gap: 15px;
    align-items: end;
    flex-wrap: wrap;
}

.filter-group {
    display: flex;
    flex-direction: column;
}

.filter-group label {
    margin-bottom: 6px;
    font-weight: bold;
}

.filter-group select,
.filter-group input {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    min-width: 180px;
}

.search-btn {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.search-btn:hover {
    background-color: #0056b3;
}

.clear-btn {
    padding: 10px 20px;
    background-color: #6c757d;
    color: white;
    text-decoration: none;
    border-radius: 5px;
}

.clear-btn:hover {
    background-color: #545b62;
}

/* Messages */

.error {
    background-color: #f8d7da;
    color: #721c24;
    padding: 12px;
    border-radius: 5px;
    margin-bottom: 20px;
}

/* Vehicles */

.vehicle-container {
    display: grid;
    grid-template-columns:
        repeat(auto-fit, minmax(250px, 1fr));

    gap: 20px;
}

.vehicle-card {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.vehicle-card h3 {
    margin-top: 0;
}

.vehicle-info {
    margin: 8px 0;
}

.price {
    font-size: 20px;
    font-weight: bold;
    margin: 15px 0;
}

.rent-btn {
    display: inline-block;
    width: 100%;
    padding: 10px;
    background-color: #28a745;
    color: white;
    text-align: center;
    text-decoration: none;
    border-radius: 5px;
    box-sizing: border-box;
}

.rent-btn:hover {
    background-color: #218838;
}

.no-results {
    background-color: white;
    padding: 30px;
    text-align: center;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.navigation {
    margin-top: 30px;
    display: flex;
    gap: 10px;
}

.navigation a {
    padding: 10px 15px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
}

.navigation a:hover {
    background-color: #0056b3;
}

</style>

</head>

<body>

<div class="header">

    <h1>Available Vehicles</h1>

</div>

<div class="container">

<%
    if (error != null) {
%>

    <div class="error">
        <%= error %>
    </div>

<%
    }
%>

<!-- SEARCH / FILTER -->

<div class="search-box">

    <h2>Find Your Vehicle</h2>

    <form action="vehicles" method="get">

        <div class="filters">

            <div class="filter-group">

                <label for="vehicleType">
                    Vehicle Type
                </label>

                <select name="vehicleType" id="vehicleType">

    				<option value="">
        				All Types
    				</option>

				    <option value="Car"
    				    <%= "Car".equals(selectedType)
    						? "selected" : "" %>>
        				Car
    				</option>

				    <option value="Bike"
        				<%= "Bike".equals(selectedType)
        					? "selected" : "" %>>
        				Bike
    				</option>

				    <option value="SUV"
        				<%= "SUV".equals(selectedType)
        					? "selected" : "" %>>
				        SUV
				    </option>

				    <option value="Scooter"
				        <%= "Scooter".equals(selectedType)
							? "selected" : "" %>>
				        Scooter
				    </option>

				</select>
            </div>


            <div class="filter-group">

                <label for="maxPrice">
                    Maximum Cost Per Day
                </label>

                <input
                    type="number"
                    name="maxPrice"
                    id="maxPrice"
                    placeholder="e.g. 1500"
                    min="0"
                    step="100"
                    value="<%= selectedMaxPrice != null
                            ? selectedMaxPrice : "" %>"
                >

            </div>


            <button
                type="submit"
                class="search-btn">

                Search

            </button>


            <a href="vehicles"
               class="clear-btn">

                Clear Filters

            </a>

        </div>

    </form>

</div>


<!-- VEHICLE RESULTS -->

<%
    if (vehicles == null || vehicles.isEmpty()) {
%>

    <div class="no-results">

        <h2>No vehicles found</h2>

        <p>
            Try changing the vehicle type
            or maximum cost.
        </p>

    </div>

<%
    } else {
%>

    <div class="vehicle-container">

<%
        for (Vehicle vehicle : vehicles) {
%>

        <div class="vehicle-card">

            <h3>
                <%= vehicle.getBrand() %>
                <%= vehicle.getModel() %>
            </h3>

            <div class="vehicle-info">
                <strong>Vehicle Number:</strong>
                <%= vehicle.getVehicleNumber() %>
            </div>

            <div class="vehicle-info">
                <strong>Type:</strong>
                <%= vehicle.getVehicleType() %>
            </div>

            <div class="price">
                Rs. <%= vehicle.getPricePerDay() %> / day
            </div>

            <a
                href="rent.jsp?vehicleId=<%= vehicle.getVehicleId() %>"
                class="rent-btn">

                Rent Vehicle

            </a>

        </div>

<%
        }
%>

    </div>

<%
    }
%>


<div class="navigation">

    <a href="my-rentals">
        My Rentals
    </a>

    <a href="logout">
        Logout
    </a>

</div>

</div>

</body>

</html>