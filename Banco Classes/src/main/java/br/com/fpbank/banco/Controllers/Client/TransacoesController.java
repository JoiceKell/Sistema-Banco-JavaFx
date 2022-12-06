package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.TransactionCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


import java.net.URL;
import java.util.ResourceBundle;

public class TransacoesController implements Initializable {

    public ListView<Movimentacao> listview_movimentacoes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emitirExtrato();
        listview_movimentacoes.setItems(Model.getInstance().getTodasTransacoes());
        listview_movimentacoes.setCellFactory(e -> new TransactionCellFactory());
    }

    private void emitirExtrato() {
        if(Model.getInstance().getTodasTransacoes().isEmpty()) {
            Model.getInstance().setTodasTransacoes();
        }
    }

}
