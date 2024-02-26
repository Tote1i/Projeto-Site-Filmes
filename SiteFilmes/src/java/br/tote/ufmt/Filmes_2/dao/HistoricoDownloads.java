package br.tote.ufmt.Filmes_2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoDownloads {

    public void inserirRegistroHistoricoDownload(int usuarioId, int filmeId) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Filmes_2";
        String dbUser = "tote";
        String dbPassword = "some_pass";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO HistoricoDownloads (usuario_id, arquivo_id) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, usuarioId);
                statement.setInt(2, filmeId);
                statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Trate os erros de conexão ou consulta aqui
        }
    }
}

