package net.guilhermejr.livro.exceptions;

public class ISBNInvalido extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ISBNInvalido(String mensagem) {
        super(mensagem);
    }
}
