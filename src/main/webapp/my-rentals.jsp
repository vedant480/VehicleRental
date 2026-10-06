<%@ page import="java.util.List" %>
<%@ page import="com.vehicle.rental.Rental" %>

<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Rental> rentals =
            (List<Rental>) request.getAttribute("rentals");

    String message =
            request.getParameter("message");

    String error =
            request.getParameter("error");
%>

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
    max-width: 1100px;
    margin: 30px auto;
}

.message {
    background-color: #d4edda;
    color: #155724;
    padding: 12px;
    border-radius: 5px;
    margin-bottom: 20px;
}

.error {
    background-color: #f8d7da;
    color: #721c24;
    padding: 12px;
    border-radius: 5px;
    margin-bottom: 20px;
}

.table-container {
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    overflow-x: auto;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th {
    background-color: #007bff;
    color: white;
    padding: 12px;
}

td {
    padding: 12px;
    border-bottom: 1px solid #ddd;
    text-align: center;
}

button {
    background-color: #dc3545;
    color: white;
    border: none;
    padding: 8px 12px;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #b02a37;
}

.pending {
    color: #856404;
    background-color: #fff3cd;
    padding: 6px 10px;
    border-radius: 4px;
}

.active {
    color: #155724;
    background-color: #d4edda;
    padding: 6px 10px;
    border-radius: 4px;
}

.completed {
    color: #155724;
    background-color: #d4edda;
    padding: 6px 10px;
    border-radius: 4px;
}

.back {
    display: inline-block;
    margin-top: 20px;
    padding: 10px 15px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
}

.back:hover {
    background-color: #0056b3;
}

</style>

</head>

<body>

<div class="header">

    <h1>My Rental History</h1>

</div>

<div class="container">

<%
    if (message != null) {
%>

    <div class="message">
        <%= message %>
    </div>

<%
    }

    if (error != null) {
%>

    <div class="error">
        <%= error %>
    </div>

<%
    }
%>

<div class="table-container">

<%
    if (rentals == null || rentals.isEmpty()) {
%>

    <p>No rental history found.</p>

<%
    } else {
%>

<table>

<tr>

    <th>Rental ID</th>
    <th>Vehicle</th>
    <th>Vehicle Number</th>
    <th>Start Date</th>
    <th>End Date</th>
    <th>Total Amount</th>
    <th>Status</th>
    <th>Action</th>

</tr>

<%
    for (Rental rental : rentals) {
%>

<tr>

    <td>
        <%= rental.getRentalId() %>
    </td>

    <td>
        <%= rental.getVehicleBrand() %>
        <%= rental.getVehicleModel() %>
    </td>

    <td>
        <%= rental.getVehicleNumber() %>
    </td>

    <td>
        <%= rental.getStartDate() %>
    </td>

    <td>
        <%= rental.getEndDate() %>
    </td>

    <td>
        Rs. <%= rental.getTotalAmount() %>
    </td>

    <td>

<%
        if ("ACTIVE".equalsIgnoreCase(
                rental.getStatus())) {
%>

        <span class="active">
            ACTIVE
        </span>

<%
        } else if ("RETURN_REQUESTED".equalsIgnoreCase(
                rental.getStatus())) {
%>

        <span class="pending">
            RETURN REQUESTED
        </span>

<%
        } else {
%>

        <span class="completed">
            COMPLETED
        </span>

<%
        }
%>

    </td>

    <td>

<%
        if ("ACTIVE".equalsIgnoreCase(
                rental.getStatus())) {
%>

        <form action="return"
              method="post"
              onsubmit="return confirm('Are you sure you want to request vehicle return?');">

            <input type="hidden"
                   name="rentalId"
                   value="<%= rental.getRentalId() %>">

            <button type="submit">
                Request Return
            </button>

        </form>

<%
        } else if ("RETURN_REQUESTED".equalsIgnoreCase(
                rental.getStatus())) {
%>

        Waiting for admin verification.

<%
        } else {
%>

        Returned

<%
        }
%>

    </td>

</tr>

<%
    }
%>

</table>

<%
    }
%>

</div>

<a href="vehicles" class="back">
    Back to Vehicles
</a>

</div>

</body>

</html>