// Classe AcessarConta
// Faz-se a autenticação e acesso na Aplicação de acordo com a opção selecionada no choiceBox: CLIENTE ou ADMINISTRADOR

package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.TipoConta;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public static LoginController login;
    public ChoiceBox<TipoConta> acc_selector;
    public TextField fld_cpf;
    public TextField fld_senha;
    public Button btn_login;
    public Label lbl_erro;
    public Label lbl_usuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login = this;
        acc_selector.setItems(FXCollections.observableArrayList(TipoConta.CLIENTE, TipoConta.ADMINISTRADOR));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginTipoConta());
        acc_selector.valueProperty().addListener(observable -> setAccSelector());
        btn_login.setOnAction(event -> acessarConta());
    }

    public void acessarConta() {

        Stage stage = (Stage) btn_login.getScene().getWindow();

        if(Model.getInstance().getViewFactory().getLoginTipoConta() == TipoConta.CLIENTE){
            //Verifica dados de Login do Cliente
            Model.getInstance().verificaCredenciaisCliente(fld_cpf.getText(), fld_senha.getText());
            if (Model.getInstance().getClientLoginSuccesFlag()){
                Model.getInstance().getViewFactory().showClientWindow();
                //Verificação bem sucedida, fecha a tela de login
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                fld_cpf.setText("");
                fld_senha.setText("");
                lbl_erro.setText("Credenciais de Login não encontradas!");
            }
        } else {
            //Verifica dados de Login do Administrador
            Model.getInstance().verificaCredenciaisAdministrador(fld_cpf.getText(), fld_senha.getText());
            if(Model.getInstance().getFlagSucessoLoginAdmin()) {
                Model.getInstance().getViewFactory().showAdminWindow();
                //Verificação bem sucedida, fecha a tela de login
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                fld_cpf.setText("");
                fld_senha.setText("");
                lbl_erro.setText("Credenciais de Login não encontradas!");
            }
        }
    }

    private void setAccSelector() {
        Model.getInstance().getViewFactory().setLoginTipoConta(acc_selector.getValue());
        //Altera e informa se o login é para usuário do tipo Cliente ou Administrador
        if(acc_selector.getValue() == TipoConta.ADMINISTRADOR) {
            lbl_usuario.setText("Usuário: ");
            fld_cpf.setPromptText("Usuário");
        } else {
            lbl_usuario.setText("CPF: ");
            fld_cpf.setPromptText("CPF");
        }

    }

}
