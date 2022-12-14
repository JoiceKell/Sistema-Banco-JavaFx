
// Classe ClienteController
// Direciona a navegação dos botões de menu da área do cliente

package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {

    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case STATEMENT -> client_parent.setCenter(Model.getInstance().getViewFactory().getHistoricoView());
                case ACCOUNTS -> client_parent.setCenter(Model.getInstance().getViewFactory().getContaView());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getMenuPrincipalView());
            }
        });
    }
}
