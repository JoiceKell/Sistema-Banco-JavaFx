package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {

    private final IntegerProperty aniversario;


    public ContaPoupanca(int numAgencia, String numConta, double saldo, String tipoConta, String statusConta, LocalDate dtCriacao, int aniversario) {
        super(numAgencia, numConta, saldo, tipoConta, statusConta, dtCriacao);
        this.aniversario = new SimpleIntegerProperty(this, "Aniversario da Conta", aniversario);
    }

    public ContaPoupanca(String numConta, double saldo, String tipoConta, LocalDate dtCriacao) {
        super(numConta, saldo, tipoConta, dtCriacao);
        this.aniversario = new SimpleIntegerProperty(this, "Aniversario da Conta", 0);
    }

    @Override
    void abrirConta() {

    }

    @Override
    void acessarConta() {

    }

    @Override
    void alterarDados() {

    }

    @Override
    double apresentarSaldo() {
        return 0;
    }

    @Override
    void desativarConta() {

    }

    @Override
    void emitirExtrato() {

    }

    @Override
    void transferirValor() {

    }

    public ContaPoupanca(int numAgencia, String numConta, double saldo, String tipoConta, String status, LocalDate dtCriacao){
        super(numAgencia, numConta, saldo, tipoConta, status, dtCriacao);
        this.aniversario = new SimpleIntegerProperty(this, "Aniversario da Conta", 0);
    }

    public IntegerProperty aniversarioProperty() {
        return aniversario;
    }
}
