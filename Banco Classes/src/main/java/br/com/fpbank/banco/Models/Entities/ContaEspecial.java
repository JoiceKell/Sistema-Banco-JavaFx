package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class ContaEspecial extends Conta{

    // Atributos
    private final DoubleProperty limite;
    private final DoubleProperty juros;

    public ContaEspecial(int numAgencia, String numConta, double saldo, String tipoConta, String statusConta, LocalDate dtCriacao, double limite, double juros) {
        super(numAgencia, numConta, saldo, tipoConta, statusConta, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", limite);
        this.juros = new SimpleDoubleProperty(this, "Juros", juros);
    }

    public ContaEspecial(String numConta, double saldo, String tipoConta, LocalDate dtCriacao) {
        super(numConta, saldo, tipoConta, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", 0.00);
        this.juros = new SimpleDoubleProperty(this, "Juros", 0.00);
    }

    public double getLimite() {
        return limite.get();
    }

    public DoubleProperty limiteProperty() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite.set(limite);
    }

    public double getJuros() {
        return juros.get();
    }

    public DoubleProperty jurosProperty() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros.set(juros);
    }
}
