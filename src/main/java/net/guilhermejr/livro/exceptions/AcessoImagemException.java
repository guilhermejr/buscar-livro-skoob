package net.guilhermejr.livro.exceptions;

public class AcessoImagemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AcessoImagemException(String message) {
        super(message);
    }
}
