package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class ContaEspecial extends Conta{

    // Atributos
    private final DoubleProperty limite;
    private final DoubleProperty juros;

    public ContaEspecial(int numAgencia, int numConta, double saldo, String tipoConta, String senha, String statusConta, LocalDate dtCriacao, double limite, double juros) {
        super(numAgencia, numConta, saldo, tipoConta, senha, statusConta, dtCriacao);
        this.limite = new SimpleDoubleProperty(this, "Limite", limite);
        this.juros = new SimpleDoubleProperty(this, "Juros", juros);
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
