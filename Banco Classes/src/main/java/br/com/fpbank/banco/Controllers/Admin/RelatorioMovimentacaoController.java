package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.MovimentacaoCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioMovimentacaoController implements Initializable {

    public ListView<Movimentacao> movimentacao_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        System.out.println("1");
        movimentacao_listview.setItems(Model.getInstance().getMovimentacoes());
        System.out.println("5");
        movimentacao_listview.setCellFactory(e -> new MovimentacaoCellFactory());
        System.out.println("6");
    }

    private void initData() {
        if (Model.getInstance().getMovimentacoes().isEmpty()) {
            Model.getInstance().setMovimentacoes();
        }
    }
}
