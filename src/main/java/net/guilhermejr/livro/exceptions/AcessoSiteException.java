package net.guilhermejr.livro.exceptions;

public class AcessoSiteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AcessoSiteException(String message) {
        super(message);
    }
}
