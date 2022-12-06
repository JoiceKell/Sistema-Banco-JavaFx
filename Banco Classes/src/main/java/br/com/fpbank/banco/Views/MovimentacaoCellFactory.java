package br.com.fpbank.banco.Views;

import br.com.fpbank.banco.Controllers.Admin.RelatorioMovimentacaoCellController;
import br.com.fpbank.banco.Models.Entities.Movimentacao;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class MovimentacaoCellFactory extends ListCell<Movimentacao> {
    @Override
    protected void updateItem(Movimentacao movimentacao, boolean empty) {
        super.updateItem(movimentacao, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/RelatorioMovimentacaoCell.fxml"));
            RelatorioMovimentacaoCellController controller = new RelatorioMovimentacaoCellController(movimentacao);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
