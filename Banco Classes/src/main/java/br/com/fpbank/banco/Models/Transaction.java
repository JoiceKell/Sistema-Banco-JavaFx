package br.com.fpbank.banco.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {

    private final StringProperty remetente;
    private final StringProperty destinatario;
    private final DoubleProperty valor;
    private final ObjectProperty<LocalDate> data;
    private final StringProperty mensagem;

    public Transaction(String remetente, String destinatario, double valor, LocalDate data, String mensagem) {
        this.remetente = new SimpleStringProperty(this, "Remetente", remetente);
        this.destinatario = new SimpleStringProperty(this, "Destinatario", destinatario);
        this.valor = new SimpleDoubleProperty(this, "Valor", valor);
        this.data = new SimpleObjectProperty<>(this, "Data", data);
        this.mensagem = new SimpleStringProperty(this, "Mensagem", mensagem);
    }

    public StringProperty remetenteProperty() { return this.remetente; }

    public StringProperty destinatarioProperty() { return this.destinatario; }

    public DoubleProperty valorProperty() { return  this.valor; }

    public ObjectProperty<LocalDate> dataProperty() { return this.data; }

    public StringProperty mensagemProperty() { return  this.mensagem; }

}
