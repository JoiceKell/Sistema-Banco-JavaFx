package br.com.fpbank.banco.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public TextField phone_fld;
    public TextField email_fld;
    public TextField street_fld;
    public TextField number_fld;
    public TextField zipCode_fld;
    public TextField city_fld;
    public TextField state_fld;
    public TextField district_fld;
    public  TextField password_fld;
    public TextField confirmPassword_fld;
    public TextField complemento_fld;
    public Button update_btn;
    public Button disable_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
