
// Classe ClienteBotoesMenuController
// Gerencia os botões do menu e suas ações

package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.OpcoesMenuCliente;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteBotoesMenuController implements Initializable {
    public static ClienteBotoesMenuController clienteBotoesMenuController;
    public Button btn_menuPrincipal;
    public Button btn_historico;
    public Button btn_conta;
    public Button btn_logout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clienteBotoesMenuController = this;
        addListeners();
    }

    private void addListeners() {
        btn_menuPrincipal.setOnAction(event -> onMenuPrincipal());
        btn_historico.setOnAction(event -> onHistorico());
        btn_conta.setOnAction(event -> onConta());
        btn_logout.setOnAction(event -> {
            try {
                onLogout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void onMenuPrincipal() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(OpcoesMenuCliente.DASHBOARD);
    }

    private void onHistorico() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(OpcoesMenuCliente.STATEMENT);
    }

    private void onConta() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(OpcoesMenuCliente.ACCOUNTS);
    }

    private void onLogout() throws Exception {
        Stage stage = (Stage) btn_logout.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
        Model.getInstance().getViewFactory().showMainMenu(new Stage());
        Model.getInstance().setFlagSucessoLoginCliente(false);
        Platform.exit();
    }
}
