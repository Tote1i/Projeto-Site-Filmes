<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.tote.ufmt.Filmes_2_models.Filme" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Busca de Filmes</title>
    <!-- Link para o CSS do Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Estilo personalizado para a tabela */
        .table th, .table td {
            vertical-align: middle;
        }

        /* Estilo para o formulário de comentário */
        .comment-form {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">Busca de Filmes</h1>

        <!-- Tabela para listar os filmes -->
        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Nome do Arquivo</th>
                    <th>Tamanho</th>
                    <th>Descrição</th>
                    <th>Data de Upload</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Filme> filmes = (List<Filme>) request.getAttribute("filmes");
                    if (filmes != null && !filmes.isEmpty()) {
                        for (Filme filme : filmes) {
                %>
                <tr>
                    <td><%= filme.getNomeArquivo() %></td>
                    <td><%= filme.getTamanho() %> bytes</td>
                    <td><%= filme.getDescricao() %></td>
                    <td><%= filme.getDataUpload() %></td>
                    <td>
                        <a href="download.jsp?filmeId=<%= filme.getId() %>" class="btn btn-primary btn-sm">Download</a>
                        
                        <!-- Formulário de comentário -->
                        <form action="ComentarServlet" method="post" class="comment-form">
                            <input type="hidden" name="filme_id" value="<%= filme.getId() %>">
                            <div class="form-group">
                                <label for="texto">Comentário:</label>
                                <textarea class="form-control" id="texto" name="texto" rows="2" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-success btn-sm">Comentar</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">Nenhum filme encontrado.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Link para o JavaScript do Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
