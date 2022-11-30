package br.com.fpbank.banco.Models.Entities;

import br.com.fpbank.banco.Models.Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class ContaEspecial extends Conta{

    // Atributos
    private final DoubleProperty limite;

    public ContaEspecial(int numAgencia, String numConta, double saldo, String tipoConta, String statusConta, LocalDate dtCriacao, double limite) {
        super(numAgencia, numConta, saldo, tipoConta, statusConta, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", limite);
    }

    public ContaEspecial(String numConta, double saldo, String tipoConta, double limite, LocalDate dtCriacao) {
        super(numConta, saldo, tipoConta, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", limite);
    }

    public ContaEspecial(int numAgencia, String numConta, double saldo, String tipoConta, String status, double limite, LocalDate dtCriacao){
        super(numAgencia, numConta, saldo, tipoConta, status, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", limite);
    }
    public DoubleProperty limiteProperty() {
        return limite;
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
        return Model.getInstance().getCliente().getContaCorrente().saldoProperty().get();
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
}
