
// Classe Movimentacao

package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Movimentacao {

    private final ObjectProperty<LocalDate> dtMovimentacao;
    private final DoubleProperty montante;
    private final StringProperty tipoMovimentacao;
    private final StringProperty mensagem;
    private final StringProperty remetente;
    private final StringProperty destinatario;
    private final StringProperty nomeOrigem;

    public Movimentacao(String remetente, String destinatario, double montante, LocalDate dtMovimentacao, String tipoMovimentacao, String mensagem) {
        this.dtMovimentacao = new SimpleObjectProperty<>(this, "Data da Movimentacao", dtMovimentacao);
        this.montante = new SimpleDoubleProperty(this, "Valor", montante);
        this.tipoMovimentacao = new SimpleStringProperty(this, "Tipo da Movimentacao", tipoMovimentacao);
        this.mensagem = new SimpleStringProperty(this, "Mensagem", mensagem);
        this.remetente = new SimpleStringProperty(this, "Remetente", remetente);
        this.destinatario = new SimpleStringProperty(this, "Destinatário", destinatario);

        this.nomeOrigem = new SimpleStringProperty(this, "Nome da Origem", null);
    }

    public Movimentacao(String nomeOrigem, String remetente, String destinatario, double montante, LocalDate dtMovimentacao, String tipoMovimentacao) {
        this.dtMovimentacao = new SimpleObjectProperty<>(this, "Data da Movimentacao", dtMovimentacao);
        this.montante = new SimpleDoubleProperty(this, "Valor", montante);
        this.tipoMovimentacao = new SimpleStringProperty(this, "Tipo da Movimentacao", tipoMovimentacao);
        this.remetente = new SimpleStringProperty(this, "Remetente", remetente);
        this.destinatario = new SimpleStringProperty(this, "Destinatário", destinatario);
        this.nomeOrigem = new SimpleStringProperty(this, "Nome da Origem", nomeOrigem);

        this.mensagem = new SimpleStringProperty(this, "Mensagem", null);
    }

    public ObjectProperty<LocalDate> dtMovimentacaoProperty() {
        return dtMovimentacao;
    }

    public DoubleProperty montanteProperty() {
        return montante;
    }

    public StringProperty tipoMovimentacaoProperty() {
        return tipoMovimentacao;
    }

    public StringProperty mensagemProperty() {
        return mensagem;
    }

    public StringProperty remetenteProperty() {
        return remetente;
    }

    public StringProperty destinatarioProperty() {
        return destinatario;
    }

    public StringProperty nomeOrigemProperty() {
        return nomeOrigem;
    }

}
