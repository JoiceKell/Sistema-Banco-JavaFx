package br.com.fpbank.banco.Views;

import br.com.fpbank.banco.Controllers.Client.TransacaoCellController;
import br.com.fpbank.banco.Models.Entities.Movimentacao;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class TransactionCellFactory extends ListCell<Movimentacao> {
    @Override
    protected void updateItem(Movimentacao transaction, boolean empty) {
        super.updateItem(transaction, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/TransacaoCell.fxml"));
            TransacaoCellController controller = new TransacaoCellController(transaction);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
