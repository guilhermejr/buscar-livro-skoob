package net.guilhermejr.livro.exceptions;

public class ISBNInexistente extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ISBNInexistente(String mensagem) {
        super(mensagem);
    }
}
