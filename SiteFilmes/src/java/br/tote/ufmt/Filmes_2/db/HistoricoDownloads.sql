
CREATE TABLE HistoricoDownloads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    arquivo_id INT NOT NULL,
    data_download TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),
    FOREIGN KEY (arquivo_id) REFERENCES Filmes(id)
);
