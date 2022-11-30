package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.AdminMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    public Button reportClients_btn;
    public Button logout_btn;
    public Button btn_Transferencias;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        reportClients_btn.setOnAction(event -> onReport());
        btn_Transferencias.setOnAction(event -> onRelatorioMovimentacao());
        logout_btn.setOnAction(event -> {
            try {
                onLogout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onReport() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.RELATORIO_CLIENTE);
    }

    private void onRelatorioMovimentacao() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.RELATORIO_MOVIMENTACAO);
    }

    private void onLogout() throws Exception {
        // Get Stage
        Stage stage = (Stage) reportClients_btn.getScene().getWindow();
        // Close the Admin Window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show login Window
        stage = new Stage();
        Model.getInstance().getViewFactory().showMainMenu(stage);
        // Set Admin Login Succes Flag To False
        Model.getInstance().setAdminLoginSuccessFlag(false);
    }
}
