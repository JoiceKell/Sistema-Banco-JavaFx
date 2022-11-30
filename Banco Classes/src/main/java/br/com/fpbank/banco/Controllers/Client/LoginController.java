package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.AccountType;
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

    public ChoiceBox<AccountType> acc_selector;
    public TextField cpf_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public Label usuario_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENTE, AccountType.ADMINISTRADOR));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event -> acessarConta());
    }

    public void acessarConta() {

        Stage stage = (Stage) error_lbl.getScene().getWindow();

        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENTE){
            //Model.getInstance().getViewFactory().showClientWindow();
            // Evaluate Client Login Credencials
            Model.getInstance().evaluateClientCred(cpf_fld.getText(), password_fld.getText());
            if (Model.getInstance().getClientLoginSuccesFlag()){
                Model.getInstance().getViewFactory().showClientWindow();
                // Close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                cpf_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Credenciais de Login não encontradas!");
            }
        } else {
            // Evaluate Admin Login Credentials
            Model.getInstance().evaluateAdminCred(cpf_fld.getText(), password_fld.getText());
            if(Model.getInstance().getAdminLoginSuccessFlag()) {
                Model.getInstance().getViewFactory().showAdminWindow();
                // Close the login stage
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                cpf_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Credenciais de Login não encontrada!");
            }
        }
    }

    private void setAcc_selector() {
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        // Change usuario labek according
        if(acc_selector.getValue() == AccountType.ADMINISTRADOR) {
            usuario_lbl.setText("Usuário: ");
            cpf_fld.setPromptText("Usuário");
        } else {
            usuario_lbl.setText("CPF: ");
            cpf_fld.setPromptText("CPF");
        }

    }

}
