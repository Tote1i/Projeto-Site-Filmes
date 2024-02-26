<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comentários de Filmes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #333;
            color: #fff;
            text-align: center;
            padding: 10px;
        }

        container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .comment {
            background-color: #fff;
            border: 1px solid #ccc;
            margin: 10px 0;
            padding: 10px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .comment h2 {
            color: #333;
            margin: 0 0 10px;
        }

        .comment p {
            color: #666;
            margin: 0;
        }
    </style>
</head>
<body>
    <header>
        <h1>Comentários de Filmes</h1>
    </header>

    <div class="container">
        <%
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String jdbcURL = "jdbc:mysql://localhost:3306/Filmes_2";
                String dbUser = "tote";
                String dbPassword = "some_pass";
                Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

                String sql = "SELECT c.texto, c.data, c.filme_id, f.nome_arquivo " +
                             "FROM Comentarios c " +
                             "JOIN Filmes f ON (c.filme_id = f.id)";
                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String texto = resultSet.getString("texto");
                    String data = resultSet.getString("data");
                    String nomeFilme = resultSet.getString("nome_arquivo");

                    %>
                    <div class="comment">
                        <h2>Nome do Filme: <%= nomeFilme %></h2>
                        <p><strong>Comentário:</strong> <%= texto %></p>
                        <p><strong>Data:</strong> <%= data %></p>
                    </div>
                    <%
                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        %>
    </div>
</body>
</html>
