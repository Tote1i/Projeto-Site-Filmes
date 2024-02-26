package br.tote.ufmt.Filmes_2.servlets;

import br.tote.ufmt.Filmes_2_models.Filme;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class.getName());


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/Filmes_2";
        String dbUser = "tote";
        String dbPassword = "some_pass";

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            List<Filme> filmes;
            // Consulta SQL para buscar os filmes
            try (
                    Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                // Consulta SQL para buscar os filmes
                String sql = "SELECT * FROM Filmes";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                // Lista para armazenar os filmes
                filmes = new ArrayList<>();
                // Processa os resultados da consulta
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nomeArquivo = resultSet.getString("nome_arquivo");
                    int tamanho = resultSet.getInt("tamanho");
                    String descricao = resultSet.getString("descricao");
                    String dataUpload = resultSet.getString("data_upload");
                    
                    LOGGER.info("Filme: ID=" + id + ", Nome Arquivo=" + nomeArquivo + ", Tamanho=" + tamanho + " bytes, Descrição=" + descricao + ", Data Upload=" + dataUpload);
                    
                    Filme filme = new Filme(id, nomeArquivo, tamanho, descricao, dataUpload);
                    filmes.add(filme);
                    

                }
                
            }

            // Define a lista de filmes como um atributo da solicitação
            request.setAttribute("filmes", filmes);

            // Encaminha a solicitação para a página search.jsp
            request.getRequestDispatcher("search.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Lide com erros de conexão ou consulta aqui
        }
    }
}
