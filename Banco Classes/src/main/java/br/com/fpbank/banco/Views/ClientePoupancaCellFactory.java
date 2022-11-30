package br.com.fpbank.banco.Views;

import br.com.fpbank.banco.Controllers.Admin.ClientePoupancaCellController;
import br.com.fpbank.banco.Models.Entities.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ClientePoupancaCellFactory extends ListCell<Cliente> {
    @Override
    protected void updateItem(Cliente cliente, boolean empty) {
        super.updateItem(cliente, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ClientePoupancaCell.fxml"));
            ClientePoupancaCellController controller = new ClientePoupancaCellController(cliente);
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
