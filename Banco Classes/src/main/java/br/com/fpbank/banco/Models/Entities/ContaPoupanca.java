package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {

    private final ObjectProperty<LocalDate> aniversario;
    private final DoubleProperty rendimento;


    public ContaPoupanca(int numAgencia, String numConta, double saldo, String tipoConta, String statusConta, LocalDate dtCriacao, LocalDate aniversario, double rendimento) {
        super(numAgencia, numConta, saldo, tipoConta, statusConta, dtCriacao);
        this.aniversario = new SimpleObjectProperty<>(this, "Aniversario da Conta", aniversario);
        this.rendimento = new SimpleDoubleProperty(this, "Rendimento", rendimento);
    }

    public ContaPoupanca(String numConta, double saldo, String tipoConta, LocalDate dtCriacao) {
        super(numConta, saldo, tipoConta, dtCriacao);
        this.aniversario = new SimpleObjectProperty<>(this, "Aniversario da Conta", null);
        this.rendimento = new SimpleDoubleProperty(this, "Rendimento", 0.00);
    }

    public ContaPoupanca(int numAgencia, String numConta, double saldo, String tipoConta, String status, LocalDate dtCriacao){
        super(numAgencia, numConta, saldo, tipoConta, status, dtCriacao);
        this.aniversario = new SimpleObjectProperty<>(this, "Aniversario da Conta", null);
        this.rendimento = new SimpleDoubleProperty(this, "Rendimento", 0.00);
    }

    public LocalDate getAniversario() {
        return aniversario.get();
    }

    public ObjectProperty<LocalDate> aniversarioProperty() {
        return aniversario;
    }

    public void setAniversario(LocalDate aniversario) {
        this.aniversario.set(aniversario);
    }

    public double getRendimento() {
        return rendimento.get();
    }

    public DoubleProperty rendimentoProperty() {
        return rendimento;
    }

    public void setRendimento(double rendimento) {
        this.rendimento.set(rendimento);
    }

}
