package br.tote.ufmt.Filmes_2_models;

import java.util.List;

public class Filme {
    private int id;
    private String nomeArquivo;
    private int tamanho;
    private String descricao;
    private String dataUpload;
    

    public Filme(int id, String nomeArquivo, int tamanho, String descricao, String dataUpload) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.tamanho = tamanho;
        this.descricao = descricao;
        this.dataUpload = dataUpload;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(String dataUpload) {
        this.dataUpload = dataUpload;
    }
    
    
}
