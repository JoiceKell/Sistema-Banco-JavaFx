package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {

    public ListView<Movimentacao> transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAllTransactions();
        transactions_listview.setItems(Model.getInstance().getTodasTransacoes());
        transactions_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initAllTransactions() {
        if(Model.getInstance().getTodasTransacoes().isEmpty()) {
            Model.getInstance().setTodasTransacoes();
        }
    }

}
