package com.bancodigital.jdbc.exception;

public class ChavePixNaoEncontradaException extends RuntimeException {
    public ChavePixNaoEncontradaException(String chavePix) {
        super("Conta com chave Pix " + chavePix + " não encontrada");
    }
}
