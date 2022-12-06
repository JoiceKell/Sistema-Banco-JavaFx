
// Classe TransacaoCellController
// Definição de dados das Transações

package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;

public class TransacaoCellController implements Initializable {

    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label lbl_dtTransac;
    public Label lbl_remetente;
    public Label lbl_destinatario;
    public Label lbl_valor;
    public Label lbl_tipoMovimentacao;
    public Label lbl_NomeRemetente;
    public Label lbl_NomeDestinatario;
    public Button btn_mensagem;

    private final Movimentacao transaction;

    public TransacaoCellController(Movimentacao transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_remetente.textProperty().bind(transaction.remetenteProperty());
        lbl_destinatario.textProperty().bind(transaction.destinatarioProperty());
        lbl_valor.textProperty().bind(transaction.montanteProperty().asString());
        lbl_NomeRemetente.textProperty().bind(transaction.remetenteProperty());
        lbl_NomeDestinatario.textProperty().bind(transaction.destinatarioProperty());
        lbl_tipoMovimentacao.textProperty().bind(transaction.tipoMovimentacaoProperty());
        lbl_dtTransac.textProperty().bind(transaction.dtMovimentacaoProperty().asString());
        btn_mensagem.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow(transaction.remetenteProperty().get(), transaction.mensagemProperty().get()));
        transactionsIcons();
    }

    private void transactionsIcons() {
        try {
            if (transaction.remetenteProperty().get().equals(Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get())) {
                in_icon.setFill(Color.rgb(240, 240, 240));
                out_icon.setFill(Color.RED);
            } else {
                in_icon.setFill(Color.GREEN);
                out_icon.setFill(Color.rgb(240, 240, 240));
            }
        } catch (Exception e) {
            if (transaction.remetenteProperty().get().equals(Model.getInstance().getCliente().getContaCorrente().numContaProperty().get())) {
                in_icon.setFill(Color.rgb(240, 240, 240));
                out_icon.setFill(Color.RED);
            } else {
                in_icon.setFill(Color.GREEN);
                out_icon.setFill(Color.rgb(240, 240, 240));
            }
        }
    }

}
