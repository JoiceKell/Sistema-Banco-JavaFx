package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    
    public TextField fld_name;
    public TextField fld_lastName;
    public TextField fld_cpf;
    public DatePicker dtp_birth;
    public TextField fld_phone;
    public TextField fld_email;
    public TextField fld_street;
    public TextField fld_number;
    public TextField fld_zipCode;
    public TextField fld_addInfo;
    public TextField fld_city;
    public TextField fld_state;
    public TextField fld_district;
    public CheckBox cb_savingAcc;
    public CheckBox cb_checkingA;
    public TextField fld_savingAccBal;
    public TextField fld_checkingAccBal;
    public TextField fld_password;
    public TextField fld_confirmPassword;
    public Button bt_Register;
    public Label lbl_error;
    private  boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_Register.setOnAction(event -> createClient());
        cb_checkingA.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                createCheckingAccountFlag = true;
            }
        });

        cb_savingAcc.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                createSavingsAccountFlag = true;
            }
        } );
    }

    private void createClient() {
        // Create Checking account
        if(createCheckingAccountFlag) {
            createAccount("Corrente");
        }

        // Create Saving Account
        if(createSavingsAccountFlag) {
            createAccount("Poupanca");
        }

        //Create Client
        String name = fld_name.getText();
        String lastName = fld_lastName.getText();
        String cpf = fld_cpf.getText();

        LocalDate dtBirth = dtp_birth.getValue();

        String phone = fld_phone.getText();
        String email = fld_email.getText();

        String street = fld_street.getText();
        String number = fld_number.getText();
        String zipCode = fld_zipCode.getText();
        String addInfo = fld_addInfo.getText();
        String city = fld_city.getText();
        String state = fld_state.getText();
        String district = fld_district.getText();

        String password = fld_password.getText();
        String confirmPassword = fld_confirmPassword.getText();

        String senha = null;
        if(password.equals(confirmPassword)) {
            senha = password;
        }

        String endereco = fld_street.getText()+ ", " +fld_number.getText()+ ", "+ fld_zipCode.getText()+", "+fld_addInfo.getText()+", "+fld_city.getText()+", "+fld_state.getText()+", "+fld_district.getText();


        Model.getInstance().getDatabaseDriver().createClient(name, lastName, cpf, dtBirth, phone, email, senha, endereco) ;
        lbl_error.setText("Cadastro Realizado com Sucesso!");
        emptyFields();

    }

    private void createAccount(String accountType) {
        String cliente = fld_cpf.getText();

        // Generate Account Number

        String accountNumber = "123456789";
        //Create the checking account
        if(accountType.equals("Corrente")) {
            double balance = Double.parseDouble(fld_checkingAccBal.getText());
            Model.getInstance().getDatabaseDriver().createCheckingAccount(cliente, accountNumber, 550, balance);
        } else {
            double balance = Double.parseDouble(fld_savingAccBal.getText());
            Model.getInstance().getDatabaseDriver().createSavingsAccount(cliente, accountNumber, balance);
        }
    }

    private void emptyFields() {
        fld_name.setText("");
        fld_lastName.setText("");
        fld_cpf.setText("");
        dtp_birth.setPromptText(String.valueOf(LocalDate.now()));
        fld_phone.setText("");
        fld_email.setText("");
        fld_street.setText("");
        fld_number.setText("");
        fld_zipCode.setText("");
        fld_addInfo.setText("");
        fld_city.setText("");
        fld_state.setText("");
        fld_district.setText("");
        cb_savingAcc.setSelected(false);
        cb_checkingA.setSelected(false);
        fld_savingAccBal.setText("");
        fld_checkingAccBal.setText("");
        fld_password.setText("");
        fld_confirmPassword.setText("");
        dtp_birth.setValue(null);
    }

}
