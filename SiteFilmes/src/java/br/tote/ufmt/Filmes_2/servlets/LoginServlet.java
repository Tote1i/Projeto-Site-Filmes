package br.tote.ufmt.Filmes_2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Verificar as credenciais no banco de dados
        if (verificarCredenciais(username, password)) {
            // Credenciais válidas, autenticar o usuário
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("DashboardServlet");
        } else {
            // Credenciais inválidas, redirecionar de volta para a página de login com uma mensagem de erro
            response.sendRedirect("erroLogin.jsp");
        }
    }

    private boolean verificarCredenciais(String username, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT id FROM Usuarios WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next(); // Retorna verdadeiro se a consulta retornar algum resultado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
