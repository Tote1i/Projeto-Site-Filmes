package br.tote.ufmt.Filmes_2.servlets;

import br.tote.ufmt.Filmes_2.dao.HistoricoDownloads;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int filmeId = Integer.parseInt(request.getParameter("filmeId"));
        int usuarioId = Integer.parseInt(request.getParameter("usuarioId"));

        String jdbcUrl = "jdbc:mysql://localhost:3306/Filmes_2";
        String dbUser = "tote";
        String dbPassword = "some_pass";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "SELECT nome_arquivo, tamanho, conteudo_do_arquivo FROM Filmes WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, filmeId);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String nomeArquivo = resultSet.getString("nome_arquivo");
                    int tamanho = resultSet.getInt("tamanho");

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
                    response.setContentType("application/octet-stream");
                    response.setContentLength(tamanho);

                    try (
                        InputStream inputStream = resultSet.getBinaryStream("conteudo_do_arquivo");
                        OutputStream outputStream = response.getOutputStream()) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    // Inserir registro no histórico de downloads
                    HistoricoDownloads historicoDao = new HistoricoDownloads();
                    historicoDao.inserirRegistroHistoricoDownload(usuarioId, filmeId);
                    }
                } else {
                    response.getWriter().println("Filme não encontrado");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Trate os erros de conexão ou consulta aqui
        }
    }
}
