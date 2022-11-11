package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Movimentacao {

    // Atributos
    private final IntegerProperty idMovimentacao;
    private final ObjectProperty<LocalDate> dtMovimentacao;
    private final DoubleProperty valor;
    private final StringProperty tipoMovimentacao;

    public Movimentacao(int idMovimentacao, LocalDate dtMovimentacao, double valor, String tipoMovimentacao) {
        this.idMovimentacao = new SimpleIntegerProperty(this, "ID da movimentacao", idMovimentacao);
        this.dtMovimentacao = new SimpleObjectProperty<>(this, "Data da Movimentacao", dtMovimentacao);
        this.valor = new SimpleDoubleProperty(this, "Valor", valor);
        this.tipoMovimentacao = new SimpleStringProperty(this, "Tipo da Movimentacao", tipoMovimentacao);
    }

    public int getIdMovimentacao() {
        return idMovimentacao.get();
    }

    public IntegerProperty idMovimentacaoProperty() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao.set(idMovimentacao);
    }

    public LocalDate getDtMovimentacao() {
        return dtMovimentacao.get();
    }

    public ObjectProperty<LocalDate> dtMovimentacaoProperty() {
        return dtMovimentacao;
    }

    public void setDtMovimentacao(LocalDate dtMovimentacao) {
        this.dtMovimentacao.set(dtMovimentacao);
    }

    public double getValor() {
        return valor.get();
    }

    public DoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao.get();
    }

    public StringProperty tipoMovimentacaoProperty() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao.set(tipoMovimentacao);
    }
}
