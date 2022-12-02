package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Models.PasswordDialog;
import br.com.fpbank.banco.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;


import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public static DashboardController dashboardController;
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
    public RadioButton rb_poupanca;
    public RadioButton rb_corrente;
    public Label lbl_erro;

    //----------- Confirmar Transferência FXML
    public Label lbl_cpf;
    public Label lbl_nome;
    public Label lbl_valor;
    public Label lbl_numConta;
    public PasswordField senha_fld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        dashboardController = this;
        send_money_btn.setOnAction(event -> onTransferir());
        initLatestTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    public void bindData() {
        String saldo = "";

        System.out.println(Model.getInstance().getCliente().nomeProperty().get());
        user_name.textProperty().bind(Bindings.concat("Olá, ").concat(Model.getInstance().getCliente().nomeProperty()));
        login_date.setText("Hoje, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        try {
            System.out.println("Poupança: " + Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get());
            System.out.println("Corrente: " + Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get());

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Ativa") && (Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Ativa"))) {
//                System.out.println("Entrou 1");
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                savings_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                checking_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Ativa") && Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Desativada")) {
                System.out.println("Entrou 2");
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                savings_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
            }

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Desativada") && Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Ativa")) {
                System.out.println("Entrou 3");
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                checking_acc_num.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }
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

    public void padrao() {
        String saldo = "";

        System.out.println(Model.getInstance().getCliente().nomeProperty().get());
        user_name.textProperty().bind(Bindings.concat("Olá, ").concat(Model.getInstance().getCliente().nomeProperty()));
        login_date.setText("Hoje, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

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

    private void initLatestTransactionsList() {
        if(Model.getInstance().getLatestTransactions().isEmpty()) {
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onTransferir() {

        if(rb_poupanca.isSelected()) {
            transferirValor("Poupanca");
        }

        if(rb_corrente.isSelected()) {
            transferirValor("Corrente");
        }
    }

    private void transferirValor(String tipoConta) {

        lbl_erro.setText("");

        String destinatario = null;
        final String[] saldo = {null};
        double valor = 0.00;
        double montanteRemetente = 0.00;
        double limite = 0.00;
        String mensagem = null;
        String remetente = null;
        String descobreConta = null;
        final String[] numContaResultado = new String[1];
        String status = null;
        String nome = null;
        String sobrenome = null;
        String cpf = null;

        Alert confirma = new Alert(Alert.AlertType.CONFIRMATION);
        confirma.setTitle("Confirmar Transferencia");

        if (tipoConta.equals("Poupanca")) {
            destinatario = payee_fld.getText();
            valor = Double.parseDouble(amount_fld.getText());
            mensagem = message_fld.getText();

            remetente = Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get();
            montanteRemetente = Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(destinatario);

            try {
                while (resultSet.next()) {
                    descobreConta = resultSet.getString("tipoConta");
                    status = resultSet.getString("status");
                    limite = resultSet.getDouble("limite");
                    nome = resultSet.getString("nome");
                    sobrenome = resultSet.getString("sobrenome");
                    cpf = resultSet.getString("clienteCpf");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(status.equals("Ativa")) {
                if (descobreConta.equals("Poupança")) {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+payee_fld.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+amount_fld.getText()+"\nMensagem: "+message_fld.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(amount_fld.getText())  <= montanteRemetente) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente = remetente;
                            String finalDestinatario = destinatario;
                            double finalValor = valor;
                            String finalMensagem = mensagem;

                            String finalCpf = cpf;
                            result.ifPresent(password -> {

                                if (password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                    try {
                                        //Subtract from sender's saving account
                                        Model.getInstance().getDatabaseDriver().updateBalanceContaPoupanca(finalRemetente, finalValor, "SUB");
                                        Model.getInstance().getDatabaseDriver().updateBalanceContaPoupanca(finalDestinatario, finalValor, "ADD");

                                        //Update the savings account balance in the client object
                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                        savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        //Record new transaction
                                        Model.getInstance().getDatabaseDriver().novaTransacaoContaPoupanca(finalRemetente, finalDestinatario, finalValor, "Transferencia", finalMensagem, finalCpf);

                                    } catch (Exception e) {
                                        lbl_erro.setText("Saldo insuficiente!");
                                    }

                                    // Clear the fields
                                    payee_fld.setText("");
                                    amount_fld.setText("");
                                    message_fld.setText("");
                                }
                            });

                            lbl_erro.setText("Transferencia Realizada Com Sucesso");
                            lbl_erro.setStyle("-fx-text-fill: #00fee5");

                        } else {
                            Model.getInstance().getViewFactory().getDashboardView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                        System.out.println("Saldo insuficiente!");
                    }

                } else {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+payee_fld.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+amount_fld.getText()+"\nMensagem: "+message_fld.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(amount_fld.getText())  <= montanteRemetente) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente1 = remetente;
                            double finalValor1 = valor;
                            String finalDestinatario1 = destinatario;
                            double finalLimite = limite;
                            String finalMensagem1 = mensagem;
                            result.ifPresent(password -> {

                                try {
                                    //Subtract from sender's saving account
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaPoupanca(finalRemetente1, finalValor1, "SUB");
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaCorrente(finalDestinatario1, finalValor1, "ADD", finalLimite);

                                    //Update the savings account balance in the client object
                                    Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente1));
                                    //Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(destinatario));

                                    numContaResultado[0] = Model.getInstance().getDatabaseDriver().verificarContas(Model.getInstance().getCliente().cpfProperty().get(), Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get());

                                    if (numContaResultado[0] != null && numContaResultado[0].equals(finalDestinatario1)) {

                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente1));
                                        Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalDestinatario1));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                        savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                        checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                    } else {

                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente1));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                        savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                    }

                                    //Record new transaction
                                    Model.getInstance().getDatabaseDriver().novaTransacaoContaCorrente(finalRemetente1, finalDestinatario1, finalValor1, "Transferencia", finalMensagem1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            lbl_erro.setText("Transferencia Realizada Com Sucesso");
                            lbl_erro.setStyle("-fx-text-fill: #00fee5");

                        } else {
                            Model.getInstance().getViewFactory().getDashboardView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                        System.out.println("Saldo insuficiente!");
                    }

                    // Clear the fields
                    payee_fld.setText("");
                    amount_fld.setText("");
                    message_fld.setText("");
                    System.out.println("9");
                }
            } else {
                lbl_erro.setText("Conta não encontrada!");
            }

        } else {

            destinatario = payee_fld.getText();
            valor = Double.parseDouble(amount_fld.getText());
            mensagem = message_fld.getText();

            remetente = Model.getInstance().getCliente().getContaCorrente().numContaProperty().get();
            montanteRemetente = Model.getInstance().getCliente().getContaCorrente().saldoProperty().get();
            limite = Model.getInstance().getCliente().getContaCorrente().limiteProperty().get();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(destinatario);

            try {

                while (resultSet.next()) {
                    descobreConta = resultSet.getString("tipoConta");
                    status = resultSet.getString("status");
                    nome = resultSet.getString("nome");
                    sobrenome = resultSet.getString("sobrenome");
                    cpf = resultSet.getString("clienteCpf");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (status.equals("Ativa")) {
                if (descobreConta.equals("Poupança")) {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+payee_fld.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+amount_fld.getText()+"\nMensagem: "+message_fld.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(amount_fld.getText())  <= montanteRemetente + limite) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente2 = remetente;
                            double finalValor2 = valor;
                            double finalLimite1 = limite;
                            String finalDestinatario2 = destinatario;
                            String finalMensagem2 = mensagem;
                            String finalCpf1 = cpf;
                            result.ifPresent(password -> {

                                try {
                                    //Subtract from sender's saving account
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaCorrente(finalRemetente2, finalValor2, "SUB", finalLimite1);
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaPoupanca(finalDestinatario2, finalValor2, "ADD");

                                    //Update the savings account balance in the client object
                                    numContaResultado[0] = Model.getInstance().getDatabaseDriver().verificarContas(Model.getInstance().getCliente().cpfProperty().get(), Model.getInstance().getCliente().getContaCorrente().numContaProperty().get());
                                    if (numContaResultado[0] != null && numContaResultado[0].equals(finalDestinatario2)) {

                                        Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente2));
                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalDestinatario2));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                        savings_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                        checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                    } else {
                                        Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente2));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                        checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                    }

                                    //Record new transaction
                                    Model.getInstance().getDatabaseDriver().novaTransacaoContaPoupanca(finalRemetente2, finalDestinatario2, finalValor2, "Transferencia", finalMensagem2, finalCpf1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            lbl_erro.setText("Transferencia Realizada Com Sucesso");
                            lbl_erro.setStyle("-fx-text-fill: #00fee5");

                        } else {
                            Model.getInstance().getViewFactory().getDashboardView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                        System.out.println("Saldo insuficiente!");
                    }

                    // Clear the fields
                    payee_fld.setText("");
                    amount_fld.setText("");
                    message_fld.setText("");

                } else {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+payee_fld.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+amount_fld.getText()+"\nMensagem: "+message_fld.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(amount_fld.getText())  <= montanteRemetente + limite) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente3 = remetente;
                            double finalValor3 = valor;
                            double finalLimite2 = limite;
                            String finalDestinatario3 = destinatario;
                            String finalMensagem3 = mensagem;
                            result.ifPresent(password -> {

                                try {
                                    //Subtract from sender's saving account
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaCorrente(finalRemetente3, finalValor3, "SUB", finalLimite2);
                                    Model.getInstance().getDatabaseDriver().updateBalanceContaCorrente(finalDestinatario3, finalValor3, "ADD", finalLimite2);

                                    //Update the savings account balance in the client object
                                    Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(finalRemetente3));

                                    saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                    checking_bal.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                    //Record new transaction
                                    Model.getInstance().getDatabaseDriver().novaTransacaoContaCorrente(finalRemetente3, finalDestinatario3, finalValor3, "Transferencia", finalMensagem3);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            lbl_erro.setText("Transferencia Realizada Com Sucesso");
                            lbl_erro.setStyle("-fx-text-fill: #00fee5");

                        } else {
                            Model.getInstance().getViewFactory().getDashboardView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                        System.out.println("Saldo insuficiente!");
                    }


                    // Clear the fields
                    payee_fld.setText("");
                    amount_fld.setText("");
                    message_fld.setText("");
                }
            } else {
                lbl_erro.setText("Conta não encontrada!");
            }
        }
    }
}
