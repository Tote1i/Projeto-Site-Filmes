package br.tote.ufmt.Filmes_2.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024,    // 1 MB
                 maxFileSize=5*1024*1024,       // 5 MB
                 maxRequestSize=10*1024*1024)   // 10 MB
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uploadDir = getServletContext().getRealPath("/uploads");
        Path dirPath = Paths.get(uploadDir);

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        for (Part part : request.getParts()) {
            String fileName = getFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                // Gere um nome de arquivo único
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                // Salve o arquivo no diretório de upload
                String filePath = uploadDir + "/" + uniqueFileName;
                part.write(filePath);

                // Inserir informações do filme no banco de dados
                insertFileInfoToDatabase(uniqueFileName, (int) part.getSize(), filePath);

                request.setAttribute("message", "Upload de filme concluído com sucesso!");
            }
        }

        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private void insertFileInfoToDatabase(String fileName, int fileSize, String filePath) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/Filmes_2";
        String dbUser = "tote";
        String dbPassword = "some_pass";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO Filmes (nome_arquivo, tamanho, conteudo_do_arquivo) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, fileName);
                statement.setInt(2, fileSize);
                
                // Ler o conteúdo do arquivo em um array de bytes
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                statement.setBytes(3, fileContent);
                
                statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
