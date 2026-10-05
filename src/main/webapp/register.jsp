<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Vehicle Rental</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
        }

        .box {
            width: 400px;
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

        .error {
            color: red;
            text-align: center;
        }

        .link {
            text-align: center;
            margin-top: 15px;
        }
    </style>
</head>

<body>

<div class="box">

    <h2>Create Account</h2>

    <%
        String error = request.getParameter("error");

        if (error != null) {
    %>

        <p class="error"><%= error %></p>

    <%
        }
    %>

    <form action="register" method="post">

        <label>Name</label>
        <input type="text" name="name" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Register</button>

    </form>

    <div class="link">
        Already have an account?
        <a href="login.jsp">Login</a>
    </div>

</div>

</body>
</html>