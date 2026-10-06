<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Admin Dashboard</title>

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
            width: 80%;
            max-width: 700px;
            margin: 40px auto;
        }

        .card {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }

        .menu {
            display: flex;
            flex-direction: column;
            gap: 15px;
            margin-top: 25px;
        }

        .menu a {
            display: block;
            padding: 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }

        .menu a:hover {
            background-color: #0056b3;
        }

        .logout {
            background-color: #dc3545 !important;
        }

        .logout:hover {
            background-color: #b02a37 !important;
        }

    </style>

</head>

<body>

<div class="header">

    <h1>Admin Dashboard</h1>

</div>


<div class="container">

    <div class="card">

        <h2>Welcome Admin</h2>

        <p>Manage your vehicle rental system.</p>


        <div class="menu">

            <!-- Manage Vehicles -->

            <a href="manage-vehicles.jsp">
                Manage Vehicles
            </a>

			<a href="admin-rentals">
    			Manage Rentals / Returns
			</a>

            <!-- Logout -->

            <a href="logout" class="logout">
                Logout
            </a>

        </div>

    </div>

</div>

</body>

</html>