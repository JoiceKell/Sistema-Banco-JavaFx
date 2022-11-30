package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Entities.Movimentacao;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioMovimentacaoCellController implements Initializable {
    public Label lbl_nome;
    public Label lbl_sobrenome;
    public Label lbl_contaOrigem;
    public Label lbl_contaDestino;
    public Label lbl_valor;
    public Label lbl_dtMovimentacao;
    public Label lbl_tipoMovimentacao;
    public Label lbl_mensagem;

    private final Movimentacao movimentacao;

    public RelatorioMovimentacaoCellController(Movimentacao movimentacao){
        this.movimentacao = movimentacao;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_nome.textProperty().bind(movimentacao.nomeOrigemProperty());
        lbl_contaOrigem.textProperty().bind(movimentacao.remetenteProperty());
        lbl_contaDestino.textProperty().bind(movimentacao.destinatarioProperty());
        lbl_valor.textProperty().bind(movimentacao.montanteProperty().asString());
        lbl_dtMovimentacao.textProperty().bind(movimentacao.dtMovimentacaoProperty().asString());
        lbl_tipoMovimentacao.textProperty().bind(movimentacao.tipoMovimentacaoProperty());
    }
}
