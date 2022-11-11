package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public abstract class Conta {

    // Atributos
    private final IntegerProperty numAgencia;
    private final StringProperty numConta;
    private final DoubleProperty saldo;
    private final StringProperty tipoConta;
    private final StringProperty statusConta;
    private final ObjectProperty<LocalDate> dtCriacao;

    public Conta(int numAgencia, String numConta, double saldo, String tipoConta, String statusConta, LocalDate dtCriacao) {
        this.numAgencia = new SimpleIntegerProperty(this, "Numero da Agencia", numAgencia);
        this.numConta = new SimpleStringProperty(this, "Numero da Conta", numConta);
        this.saldo = new SimpleDoubleProperty(this, "Saldo",saldo);
        this.tipoConta = new SimpleStringProperty(this, "Tipo de Conta", tipoConta);
        this.statusConta = new SimpleStringProperty(this, "Status da Conta", statusConta);
        this.dtCriacao = new SimpleObjectProperty<>(this, "Data de Criação", dtCriacao);
    }

    public Conta(String numConta, double saldo, String tipoConta, LocalDate dtCriacao) {
        this.numAgencia = new SimpleIntegerProperty(this, "Numero da Agencia", 0);
        this.statusConta = new SimpleStringProperty(this, "Status da Conta", "");

        this.numConta = new SimpleStringProperty(this, "Numero da Conta", numConta);
        this.saldo = new SimpleDoubleProperty(this, "Saldo", saldo);
        this.tipoConta = new SimpleStringProperty(this, "Tipo de Conta", tipoConta);
        this.dtCriacao = new SimpleObjectProperty<>(this, "Data de Criação", dtCriacao);
    }

    public IntegerProperty numAgenciaProperty() {
        return numAgencia;
    }
    public StringProperty numContaProperty() {
        return numConta;
    }
    public DoubleProperty saldoProperty() {
        return saldo;
    }
    public StringProperty tipoContaProperty() {
        return tipoConta;
    }
    public StringProperty statusContaProperty() {
        return statusConta;
    }
    public ObjectProperty<LocalDate> dtCriacaoProperty() {
        return dtCriacao;
    }

}
