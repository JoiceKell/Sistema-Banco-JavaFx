package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public abstract class Conta {

    // Atributos
    private final IntegerProperty numAgencia;
    private final IntegerProperty numConta;
    private final DoubleProperty saldo;
    private final StringProperty tipoConta;
    private final StringProperty senha;
    private final StringProperty statusConta;
    private final ObjectProperty<LocalDate> dtCriacao;

    public Conta(int numAgencia, int numConta, double saldo, String tipoConta, String senha, String statusConta, LocalDate dtCriacao) {
        this.numAgencia = new SimpleIntegerProperty(this, "Numero da Agencia", numAgencia);
        this.numConta = new SimpleIntegerProperty(this, "Numero da Conta", numConta);
        this.saldo = new SimpleDoubleProperty(this, "Saldo",saldo);
        this.tipoConta = new SimpleStringProperty(this, "Tipo de Conta", tipoConta);
        this.senha = new SimpleStringProperty(this, "Senha", senha);
        this.statusConta = new SimpleStringProperty(this, "Status da Conta", statusConta);
        this.dtCriacao = new SimpleObjectProperty<>(this, "Data de Criação", dtCriacao);
    }

    public int getNumAgencia() {
        return numAgencia.get();
    }

    public IntegerProperty numAgenciaProperty() {
        return numAgencia;
    }

    public void setNumAgencia(int numAgencia) {
        this.numAgencia.set(numAgencia);
    }

    public int getNumConta() {
        return numConta.get();
    }

    public IntegerProperty numContaProperty() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta.set(numConta);
    }

    public double getSaldo() {
        return saldo.get();
    }

    public DoubleProperty saldoProperty() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo.set(saldo);
    }

    public String getTipoConta() {
        return tipoConta.get();
    }

    public StringProperty tipoContaProperty() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta.set(tipoConta);
    }

    public String getSenha() {
        return senha.get();
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public String getStatusConta() {
        return statusConta.get();
    }

    public StringProperty statusContaProperty() {
        return statusConta;
    }

    public void setStatusConta(String statusConta) {
        this.statusConta.set(statusConta);
    }

    public LocalDate getDtCriacao() {
        return dtCriacao.get();
    }

    public ObjectProperty<LocalDate> dtCriacaoProperty() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao.set(dtCriacao);
    }
}
