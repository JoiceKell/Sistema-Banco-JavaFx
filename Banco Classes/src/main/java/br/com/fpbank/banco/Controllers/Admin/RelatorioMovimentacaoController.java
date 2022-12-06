
// Classe RelatorioMovimentacaoController
// Apresenta todas movimentação realizadas armazenadas no banco

package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Movimentacao;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.MovimentacaoCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioMovimentacaoController implements Initializable {

    public ListView<Movimentacao> listview_movimentacoes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicData();
        listview_movimentacoes.setItems(Model.getInstance().getMovimentacoes());
        listview_movimentacoes.setCellFactory(e -> new MovimentacaoCellFactory());
    }

    private void inicData() {
        if (Model.getInstance().getMovimentacoes().isEmpty()) {
            Model.getInstance().setMovimentacoes();
        }
    }
}
