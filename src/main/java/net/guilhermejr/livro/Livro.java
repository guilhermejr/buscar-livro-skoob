package net.guilhermejr.livro;

import java.util.ArrayList;
import java.util.List;

public class Livro {

    private String capa;
    private String titulo;
    private String subtitulo;
    private String isbn;
    private String descricao;
    private List<String> generos = new ArrayList<>();
    private List<String> autores = new ArrayList<>();
    private String editora;
    private String idioma;
    private String ano;
    private String paginas;

    public Livro() {
    }

    public Livro(String capa, String titulo, String subtitulo, String isbn, String descricao, List<String> generos, List<String> autores, String editora, String idioma, String ano, String paginas) {
        this.capa = capa;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.isbn = isbn;
        this.descricao = descricao;
        this.generos = generos;
        this.autores = autores;
        this.editora = editora;
        this.idioma = idioma;
        this.ano = ano;
        this.paginas = paginas;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPaginas() {
        return paginas;
    }

    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return  "capa=" + capa + "\n" +
                "titulo=" + titulo + "\n" +
                "subtitulo=" + subtitulo + "\n" +
                "isbn=" + isbn + "\n" +
                "descricao=" + descricao + "\n" +
                "generos=" + generos + "\n" +
                "autores=" + autores + "\n" +
                "editora=" + editora + "\n" +
                "idioma=" + idioma + "\n" +
                "ano=" + ano + "\n" +
                "paginas=" + paginas + "\n";
    }

}
