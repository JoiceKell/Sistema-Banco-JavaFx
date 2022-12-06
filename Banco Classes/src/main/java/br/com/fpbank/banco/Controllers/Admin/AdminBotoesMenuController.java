
// Classe AdminBotoesMenuController
// Gerencia os botões do menu e suas ações

package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.OpcoesMenuAdmin;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminBotoesMenuController implements Initializable {

    public Button btn_relatorioClientes;
    public Button btn_logout;
    public Button btn_relatorioTransferencias;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        acessarRelatorios();
        btn_logout.setOnAction(event -> {
            try {
                onLogout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void acessarRelatorios() {
        btn_relatorioClientes.setOnAction(event -> onRelatorioClientes());
        btn_relatorioTransferencias.setOnAction(event -> onRelatorioTransferencias());
    }

    // Abre a tela com relatório de dados dos clientes
    private void onRelatorioClientes() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(OpcoesMenuAdmin.RELATORIO_CLIENTE);
    }

    // Abre a tela com os dados de Transferencias feitas pelos seus clientes
    private void onRelatorioTransferencias() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(OpcoesMenuAdmin.RELATORIO_MOVIMENTACAO);
    }

    // Esse método seria para deslogar, porém não estava funcionando como o esperado, então forçamos o fechamento da aplicação
    // Quando deslogava os dados do cliente anterior persistia na tela.
    private void onLogout() throws Exception {
        Stage stage = (Stage) btn_relatorioClientes.getScene().getWindow(); //Obtendo a janela atual
        stage.close(); //Fechando o Stage
        Model.getInstance().getViewFactory().showMainMenu(new Stage()); // Cria-se um novo palco
        Model.getInstance().setFlagSucessoLoginAdmin(false); // Seta a flag de login para falso
        // Até aqui seria o código para logout

        Platform.exit(); // Finaliza o processo
    }
}
