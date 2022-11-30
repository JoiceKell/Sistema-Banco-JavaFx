package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;


import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {

    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;
    public Label lbl_tipoMovimentacao;
    public Label fld_NomeRemetente;
    public Label fld_NomeDestinatario;
    public Button message_btn;

    private final Movimentacao transaction;

    public TransactionCellController(Movimentacao transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sender_lbl.textProperty().bind(transaction.remetenteProperty());
        receiver_lbl.textProperty().bind(transaction.destinatarioProperty());
        amount_lbl.textProperty().bind(transaction.montanteProperty().asString());
        fld_NomeRemetente.textProperty().bind(transaction.remetenteProperty());
        fld_NomeDestinatario.textProperty().bind(transaction.destinatarioProperty());
        lbl_tipoMovimentacao.textProperty().bind(transaction.tipoMovimentacaoProperty());
        trans_date_lbl.textProperty().bind(transaction.dtMovimentacaoProperty().asString());
        message_btn.setOnAction(event -> Model.getInstance().getViewFactory().showMessageWindow(transaction.remetenteProperty().get(), transaction.mensagemProperty().get()));
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
