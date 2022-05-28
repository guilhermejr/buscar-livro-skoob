package net.guilhermejr.livro;

import net.guilhermejr.livro.exceptions.AcessoImagemException;
import net.guilhermejr.livro.exceptions.AcessoSiteException;
import net.guilhermejr.livro.exceptions.ISBNInexistente;
import net.guilhermejr.livro.exceptions.ISBNInvalido;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Busca {

    // --- Texto padrão quando não achar texto no site ---
    private final String textoPadrao = "Não informado";

    // --- Verifica se ISBN é válido ------------------------------------------
    private boolean verificaSeISBNEValido(String isbn) {

        if (StringUtil.isNumeric(isbn) && isbn.length() == 13) {
            return true;
        }

        return false;

    }

    // --- Pega a página html com o resultado da busca por ISBN ---------------
    private Document resultadoDaBusca(String isbn) {

        // --- Verifica ISBN ---
        if (!verificaSeISBNEValido(isbn)) {
            throw new ISBNInvalido("ISBN é inválido: " + isbn);
        }

        try {

            // --- Recupera a página com o resultado da busca pelo ISBN ---
            Response response = Jsoup.connect("https://www.skoob.com.br/livro/lista/")
                    .method(Method.POST)
                    .data("data[Busca][tag]", isbn)
                    .execute();
            Document document = response.parse();

            // --- Lança erro caso o ISBN não exista ---
            if (!document.getElementsByClass("alert").isEmpty()) {
                throw new ISBNInexistente("ISBN não encontrado.");
            }

            return document;

        } catch (IOException ex) {
            // --- Lança erro de acesso ao site ---
            throw new AcessoSiteException("Não pode acessar resultado da busca.");
        }

    }

    // --- Pega o link com os detalhes do livro -------------------------------
    private String linkParaDetalhesDoLivro(Document document) {

        Element element = document.getElementsByClass("detalhes").first();
        return element.select("a[href]").first().attr("abs:href");

    }

    // --- Pega os autores ----------------------------------------------------
    private List<String> getAutores(Document document) {

        Elements elements = document.select("#resultadoBusca > div.box_lista_busca_vertical > div.box_lista_busca_vertical_detalhe > div.detalhes > a");

        List<String> autores = new ArrayList<>();
        elements.forEach(e -> {
            if (e.attr("href").endsWith("/tipo:autor/")) {
                autores.add(e.text());
            }
        });

        return autores.stream().distinct().collect(Collectors.toList());

    }



    // --- Html com o detalhe do livro ----------------------------------------
    private Document detalheDoLivro(String link) {
        try {

            // --- Recupera a página com os detalhes do livro ---
            Response response = Jsoup.connect(link)
                    .method(Method.GET)
                    .execute();
            Document document = response.parse();

            return document;

        } catch (Exception ex) {
            // --- Lança erro de acesso ao site ---
            throw new AcessoSiteException("Não pode acessar link com detalhes do livro.");
        }

    }

    // --- Pega o ISBN --------------------------------------------------------
    private String getISBN(Document document) {
        return document.getElementsByClass("sidebar-desc").first().getElementsByTag("span").first().text();
    }

    // --- Pega os dados de Editora, Ano, Idioma e Paginas --------------------
    private Map<String, String> getEditoraAnoIdiomaPaginas(Document document) {

        Map<String, String> retorno = new HashMap<>();
        Arrays.stream(document
                        .getElementsByClass("sidebar-desc")
                        .first()
                        .toString()
                        .split("<br>"))
                .map(e -> Jsoup.parse(e).text())
                .forEach(e -> {
                    String[] pedacos;
                    String[] divide = e.split("/");
                    if (!e.isEmpty()) {
                        if (divide.length == 2) {
                            List<String> lista = Arrays.asList(divide).stream().map(s -> s.trim()).collect(Collectors.toList());
                            lista.forEach(l -> {
                                String[] pedacos1;
                                pedacos1 = l.split(":");
                                retorno.put(pedacos1[0], pedacos1[1].trim());
                            });
                        } else {
                            pedacos = e.split(":");
                            retorno.put(pedacos[0], pedacos[1].trim());
                        }
                    }
                });

        return retorno;
    }

    // --- Pega Editora -------------------------------------------------------
    private String getEditora(Map<String, String> dados) {
        return dados.get("Editora");
    }

    // --- Pega Ano -----------------------------------------------------------
    private String getAno(Map<String, String> dados) {
        return dados.get("Ano");
    }
    // --- Pega Páginas -------------------------------------------------------
    private String getPaginas(Map<String, String> dados) {
        return dados.get("Páginas");
    }

    // --- Pega Idioma --------------------------------------------------------
    private String getIdioma(Map<String, String> dados) {
        return dados.get("Idioma");
    }

    // --- Pega gêneros -------------------------------------------------------
    private List<String> getGeneros(Document document) {
        String[] generosArray = document.getElementsByClass("pg-livro-generos").text().split("/");
        List<String> generos = Arrays.stream(generosArray).map(g -> g.trim()).filter(empty->(!empty.equals(""))).collect(Collectors.toList());
        if (generos.isEmpty()) {
            generos.add(textoPadrao);
        }

        return generos;
    }

    // --- Pega a descrição ---------------------------------------------------
    private String getDescricao(Document document) {

        String descricao = Jsoup.parse(document.select("#livro-perfil-sinopse-txt > p").toString().split("<span class=")[0].replace("<br>", "\\n")).text();
        if (descricao.isEmpty()) {
            return textoPadrao;
        }

        return descricao;

    }

    // --- Pega o subtitulo ---------------------------------------------------
    private String getSubtitulo (Document document) {
        String subtitulo = null;
        try{
            subtitulo = document.getElementsByClass("sidebar-subtitulo").first().text();
        } catch (NullPointerException e) {
            subtitulo = "";
        }

        return subtitulo;
    }

    // --- Pega o título ------------------------------------------------------
    private String getTitulo(Document document) {
        return document.getElementsByClass("sidebar-titulo").first().text();
    }


    // --- Pega a capa --------------------------------------------------------
    private String getCapa(Document document) {
        return getBase64EncodedImage(document.select("#pg-livro-menu-principal-container > div:nth-child(1) > a > img").attr("src"));
    }

    public String getBase64EncodedImage(String imageURL) {

        try {
            URL url = new URL(imageURL);
            InputStream is = url.openStream();
            byte[] bytes = IOUtils.toByteArray(is);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ex) {
            throw new AcessoImagemException("Não pode acessar a imagem da capa.");
        }

    }

    // --- Monta Livro --------------------------------------------------------
    public Livro livro (String isbn) {

        Livro livro = new Livro();

        Document document = resultadoDaBusca(isbn);
        String link = linkParaDetalhesDoLivro(document);
        livro.setAutores(getAutores(document));
        document = detalheDoLivro(link);
        livro.setIsbn(getISBN(document));
        Map<String, String> dados = getEditoraAnoIdiomaPaginas(document);
        livro.setAno(getAno(dados));
        livro.setEditora(getEditora(dados));
        livro.setIdioma(getIdioma(dados));
        livro.setPaginas(getPaginas(dados));
        livro.setGeneros(getGeneros(document));
        livro.setDescricao(getDescricao(document));
        livro.setTitulo(getTitulo(document));
        livro.setSubtitulo(getSubtitulo(document));
        livro.setCapa(getCapa(document));

        return livro;
    }

}
