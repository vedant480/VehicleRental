<%
    String vehicleId = request.getParameter("vehicleId");
    String price = request.getParameter("price");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rent Vehicle</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
        }

        .box {
            width: 450px;
            margin: 60px auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px #ccc;
        }

        h2 {
            text-align: center;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0 15px 0;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #218838;
        }
    </style>
</head>

<body>

<div class="box">

    <h2>Rent Vehicle</h2>

    <form action="rent" method="post">

        <input type="hidden"
               name="vehicleId"
               value="<%= vehicleId %>">

        <label>Start Date</label>

        <input type="date"
               name="startDate"
               required>

        <label>End Date</label>

        <input type="date"
               name="endDate"
               required>

        <p>
            Price per day:
            <strong>Rs. <%= price %></strong>
        </p>

        <button type="submit">
            Confirm Rental
        </button>

    </form>

</div>

</body>
</html>