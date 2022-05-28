package net.guilhermejr.livro;

import org.jsoup.internal.StringUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var busca = new Busca();
        var livro = new Livro();
        try {
            livro = busca.livro("0123456789012");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " - "+ ex.getClass());
        }

        System.out.println(livro);

    }

}
