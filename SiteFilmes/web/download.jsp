<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download do Filme</title>
    <!-- Link para o CSS do Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 50px;
            text-align: center;
        }
        .btn-download {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            text-decoration: none;
        }
        .btn-download:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        
        
        <%
            // Recupere o filmeId da URL
            String filmeIdStr = request.getParameter("filmeId");

            if (filmeIdStr != null) {
                try {
                    int filmeId = Integer.parseInt(filmeIdStr);
        %>
        <a href="<%= request.getContextPath() %>/DownloadServlet?filmeId=<%= filmeId %>" class="btn btn-download">Download do Filme</a>
        <%
                } catch (NumberFormatException e) {
                    out.println("ID de filme inválido");
                }
            } else {
                out.println("ID de filme não fornecido na URL");
            }
        %>
    </div>

    <!-- Link para o JavaScript do Bootstrap -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>


