package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {

    public Button dashboard_btn;
    public Button statement_btn;
    public Button account_btn;
    public Button logout_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_btn.setOnAction(event -> onDashboard());
        statement_btn.setOnAction(event -> onStatement());
        account_btn.setOnAction(event -> onAccounts());
        logout_btn.setOnAction(event -> {
            try {
                onLogout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }

    private void onStatement() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.STATEMENT);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS);
    }

    private void onLogout() throws Exception {
        // Get Stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        // Close the client Window
        Model.getInstance().getViewFactory().closeStage(stage);
        // Show login Window
        stage = new Stage();
        Model.getInstance().getViewFactory().showMainMenu(stage);
        // Set Client Login Succes Flag To False
        Model.getInstance().setClientLoginSuccessFlag(false);
    }

}
