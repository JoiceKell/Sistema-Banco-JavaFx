package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Controllers.PrincipalController;
import br.com.fpbank.banco.Main;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.ClientMenuOptions;
import br.com.fpbank.banco.Views.ViewFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static br.com.fpbank.banco.Controllers.Client.DashboardController.dashboardController;

public class ClientMenuController implements Initializable {
    public static ClientMenuController clientMenuController;
    public Button dashboard_btn;
    public Button statement_btn;
    public Button account_btn;
    public Button logout_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientMenuController = this;
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
        Stage stage = (Stage) logout_btn.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
        Model.getInstance().getViewFactory().showMainMenu(new Stage());
        Model.getInstance().setClientLoginSuccessFlag(false);
        Platform.exit();
    }
}
