package br.tote.ufmt.Filmes_2.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ComentarServlet")
public class ComentarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Filmes_2";
    private static final String DB_USER = "tote";
    private static final String DB_PASSWORD = "some_pass";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String comentario = request.getParameter("texto");
        int filmeId = -1; // Um valor padrão ou de erro caso a conversão falhe

        String filmeIdParam = request.getParameter("filme_id");

        // Mensagens de depuração
        System.out.println("Comentário: " + comentario);
        System.out.println("Filme ID Param: " + filmeIdParam);

        if (filmeIdParam != null && !filmeIdParam.isEmpty()) {
            try {
                filmeId = Integer.parseInt(filmeIdParam);
            } catch (NumberFormatException e) {
                // Mensagem de depuração em caso de erro de conversão
                System.out.println("Erro de conversão do filme_id: " + e.getMessage());
            }
        }

        if (comentario != null && !comentario.isEmpty() && filmeId != -1) {
            if (adicionarComentario(comentario, filmeId)) {
                // Comentário adicionado com sucesso
                response.sendRedirect("comentarios.jsp");
            } else {
                // Trate o caso em que o comentário não pode ser adicionado (por exemplo, erro de banco de dados)
                request.setAttribute("mensagemErro", "O comentário não pôde ser adicionado devido a um erro no banco de dados.");
                request.getRequestDispatcher("erroComentario.jsp").forward(request, response);
            }
        } else {
            // Trate o caso em que o comentário ou o filme_id não foram fornecidos ou não puderam ser convertidos
            request.setAttribute("mensagemErro", "Por favor, forneça um comentário e selecione um filme válido.");
            request.getRequestDispatcher("erroComentario.jsp").forward(request, response);
        }
    }

    private boolean adicionarComentario(String comentario, int filmeId) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Comentarios (texto, data, filme_id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, comentario);
                pstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
                pstmt.setInt(3, filmeId);

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0; // Retorna verdadeiro se um ou mais registros foram afetados
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
