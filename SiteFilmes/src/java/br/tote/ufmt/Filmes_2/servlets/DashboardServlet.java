package br.tote.ufmt.Filmes_2.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar se o usuário está autenticado
        HttpSession session = request.getSession(false); // Obtém a sessão atual, se existir

        if (session != null && session.getAttribute("username") != null) {
            // O usuário está autenticado, exibe a página do painel de controle
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            // O usuário não está autenticado, redireciona de volta para a página de login
            response.sendRedirect("login.jsp");
        }
    }
}
