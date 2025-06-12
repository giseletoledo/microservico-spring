package com.bancodigital.jdbc.service;

import com.bancodigital.jdbc.model.Conta;

public interface RegraTaxa {
    void aplicarTaxa(Conta conta);
}
