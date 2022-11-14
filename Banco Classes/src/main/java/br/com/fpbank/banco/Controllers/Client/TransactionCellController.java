package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


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
        trans_date_lbl.textProperty().bind(transaction.dtMovimentacaoProperty().asString());
    }

}
