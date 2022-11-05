package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    public Button reportClients_btn;
    public Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        reportClients_btn.setOnAction(event -> onReport());
    }

    private void onReport() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.REPORT);
    }

}
