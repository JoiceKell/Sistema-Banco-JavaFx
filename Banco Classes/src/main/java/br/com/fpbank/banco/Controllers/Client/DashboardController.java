package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;


import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        initLatestTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(event -> onSendMoney());
    }

    private void bindData() {
        String saldo = "";

        System.out.println(Model.getInstance().getCliente().nomeProperty().get());
        user_name.textProperty().bind(Bindings.concat("Olá, ").concat(Model.getInstance().getCliente().nomeProperty()));
        login_date.setText("Hoje, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        try {
            saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
            savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
            savings_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
            saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
            checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
            checking_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
        } catch (Exception e) {
            try {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                savings_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
            } catch (Exception a) {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                checking_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }
        }
    }

    private void initLatestTransactionsList() {
        if(Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney() {
        String destinatario = payee_fld.getText();
        double valor = Double.parseDouble(amount_fld.getText());
        String mensagem = message_fld.getText();

        String remetente = Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(destinatario);

        try {
            if(resultSet.isBeforeFirst()) {
                Model.getInstance().getDatabaseDriver().updateBalance(destinatario, valor, "ADD");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //Subtract from sender's saving account
        Model.getInstance().getDatabaseDriver().updateBalance(remetente, valor, "SUB");
        //Update the savings account balance in the client object
        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(remetente));
        //Record new transaction
        Model.getInstance().getDatabaseDriver().newTransaction(remetente, destinatario, valor, "Transferencia", mensagem);
        // Clear the fields
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }

}
