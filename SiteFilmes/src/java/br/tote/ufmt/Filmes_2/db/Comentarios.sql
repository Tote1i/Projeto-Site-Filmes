CREATE TABLE Comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto TEXT NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    filme_id INT,  -- Chave estrangeira para associar o comentário a um filme
    FOREIGN KEY (filme_id) REFERENCES Filmes(id)
);
