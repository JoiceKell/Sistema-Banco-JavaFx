
// Classe DashboardController
// Apresenta os Dados da Conta como Saldo e Nº da conta dos tipos de contas que o cliente possui, assim como as últimas transações e possibilidade de realizar uma transferência

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
    public Text txt_usuario;
    public Label lbl_dataLogin;
    public Label lbl_contaCorrente;
    public Label lbl_numContaCorrente;
    public Label lbl_contaPoupanca;
    public Label lbl_numContaPoupanca;
    public ListView listview_transferencias;
    public TextField fld_destinatario;
    public TextField fld_valor;
    public TextArea fld_mensagem;
    public Button btn_transferir;
    public RadioButton rb_poupanca;
    public RadioButton rb_corrente;
    public Label lbl_erro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apresentarSaldo();
        dashboardController = this;
        btn_transferir.setOnAction(event -> onTransferir());
        inicListaTransferenciasRecentes();
        listview_transferencias.setItems(Model.getInstance().getTransacoesRecentes());
        listview_transferencias.setCellFactory(e -> new TransactionCellFactory());
    }

    public void apresentarSaldo() {
        String saldo = "";

        txt_usuario.textProperty().bind(Bindings.concat("Olá, ").concat(Model.getInstance().getCliente().nomeProperty()));
        lbl_dataLogin.setText("Hoje, " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        try {

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Ativa") && (Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Ativa"))) {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaPoupanca.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaCorrente.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Ativa") && Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Desativada")) {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaPoupanca.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
            }

            if(Model.getInstance().getCliente().getContaPoupanca().statusContaProperty().get().equals("Desativada") && Model.getInstance().getCliente().getContaCorrente().statusContaProperty().get().equals("Ativa")) {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaCorrente.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }
        } catch (Exception e) {
            try {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaPoupanca.textProperty().bind(Model.getInstance().getCliente().getContaPoupanca().numContaProperty());
            } catch (Exception a) {
                saldo = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo.replace(".", ",")));
                lbl_numContaCorrente.textProperty().bind(Model.getInstance().getCliente().getContaCorrente().numContaProperty());
            }
        }
    }

    // Apresenta as últimas transações
    private void inicListaTransferenciasRecentes() {
        if(Model.getInstance().getTransacoesRecentes().isEmpty()) {
            Model.getInstance().setTransacoesRecentes();
        }
    }

    // Transfere valor de acordo com a seleção da conta de transferência
    private void onTransferir() {

        if(rb_poupanca.isSelected()) {
            transferirValor("Poupanca");
        }

        if(rb_corrente.isSelected()) {
            transferirValor("Corrente");
        }
    }

    /* Método que faz a transferência do valor de acordo com as contas de origem e destino são corrente ou poupança, faz-se a verificação dentro das 4 opções:
         Poupança p/ Poupança, Poupança p/ Corrente, Corrente p/ Poupança e Corrente para Corrente */
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
            destinatario = fld_destinatario.getText();
            valor = Double.parseDouble(fld_valor.getText());
            mensagem = fld_mensagem.getText();

            remetente = Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get();
            montanteRemetente = Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().buscaCliente(destinatario);

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

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+ fld_destinatario.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+ fld_valor.getText()+"\nMensagem: "+ fld_mensagem.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(fld_valor.getText())  <= montanteRemetente) {
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
                                        //Subtrai o valor da conta poupança do remetente e adiciona à conta poupança do destinatário
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaPoupanca(finalRemetente, finalValor, "SUB");
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaPoupanca(finalDestinatario, finalValor, "ADD");

                                        //Atualiza o saldo da conta poupança
                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                        lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        //Registra a movimentação
                                        Model.getInstance().getDatabaseDriver().novaTransacaoContaPoupanca(finalRemetente, finalDestinatario, finalValor, "Transferencia", finalMensagem, finalCpf);

                                        //Informa sucesso da transferência
                                        lbl_erro.setText("Transferencia Realizada Com Sucesso");
                                        lbl_erro.setStyle("-fx-text-fill: #00fee5");
                                    } catch (Exception e) {
                                        lbl_erro.setText("Saldo insuficiente!");
                                    }

                                    //Limpa os campos de texto
                                    fld_destinatario.setText("");
                                    fld_valor.setText("");
                                    fld_mensagem.setText("");
                                } else {
                                    lbl_erro.setText("Senha Incorreta!");
                                    lbl_erro.setStyle("-fx-text-fill: red");
                                }
                            });

                        } else {
                            Model.getInstance().getViewFactory().getMenuPrincipalView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                    }

                } else {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+ fld_destinatario.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+ fld_valor.getText()+"\nMensagem: "+ fld_mensagem.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(fld_valor.getText())  <= montanteRemetente) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente1 = remetente;
                            double finalValor1 = valor;
                            String finalDestinatario1 = destinatario;
                            double finalLimite = limite;
                            String finalMensagem1 = mensagem;
                            result.ifPresent(password -> {

                                if (password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                    try {
                                        //Subtrai o valor da conta poupança do remetente e adiciona à conta corrente do destinatário
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaPoupanca(finalRemetente1, finalValor1, "SUB");
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaCorrente(finalDestinatario1, finalValor1, "ADD", finalLimite);

                                        //Atualiza o saldo da conta poupança
                                        Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente1));

                                        numContaResultado[0] = Model.getInstance().getDatabaseDriver().verificarContas(Model.getInstance().getCliente().cpfProperty().get(), Model.getInstance().getCliente().getContaPoupanca().numContaProperty().get());

                                        if (numContaResultado[0] != null && numContaResultado[0].equals(finalDestinatario1)) {

                                            Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente1));
                                            Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalDestinatario1));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                            lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                            lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        } else {

                                            Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente1));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                            lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                        }

                                        //Registra a movimentação
                                        Model.getInstance().getDatabaseDriver().novaTransacaoContaCorrente(finalRemetente1, finalDestinatario1, finalValor1, "Transferencia", finalMensagem1);

                                        //Informa sucesso da transferência
                                        lbl_erro.setText("Transferencia Realizada Com Sucesso");
                                        lbl_erro.setStyle("-fx-text-fill: #00fee5");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //Limpa os campos de texto
                                    fld_destinatario.setText("");
                                    fld_valor.setText("");
                                    fld_mensagem.setText("");
                                } else {
                                    lbl_erro.setText("Senha Incorreta!");
                                    lbl_erro.setStyle("-fx-text-fill: red");
                                }
                            });

                        } else {
                            Model.getInstance().getViewFactory().getMenuPrincipalView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                    }

                }
            } else {
                lbl_erro.setText("Conta não encontrada!");
            }

        } else {

            destinatario = fld_destinatario.getText();
            valor = Double.parseDouble(fld_valor.getText());
            mensagem = fld_mensagem.getText();

            remetente = Model.getInstance().getCliente().getContaCorrente().numContaProperty().get();
            montanteRemetente = Model.getInstance().getCliente().getContaCorrente().saldoProperty().get();
            limite = Model.getInstance().getCliente().getContaCorrente().limiteProperty().get();
            ResultSet resultSet = Model.getInstance().getDatabaseDriver().buscaCliente(destinatario);

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

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+ fld_destinatario.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+ fld_valor.getText()+"\nMensagem: "+ fld_mensagem.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(fld_valor.getText())  <= montanteRemetente + limite) {
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

                                if (password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                    try {
                                        //Subtrai o valor da conta corrente do remetente e adiciona à conta poupança do destinatário
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaCorrente(finalRemetente2, finalValor2, "SUB", finalLimite1);
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaPoupanca(finalDestinatario2, finalValor2, "ADD");

                                        //Atualiza o saldo da conta corrente
                                        numContaResultado[0] = Model.getInstance().getDatabaseDriver().verificarContas(Model.getInstance().getCliente().cpfProperty().get(), Model.getInstance().getCliente().getContaCorrente().numContaProperty().get());
                                        if (numContaResultado[0] != null && numContaResultado[0].equals(finalDestinatario2)) {

                                            Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente2));
                                            Model.getInstance().getCliente().getContaPoupanca().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalDestinatario2));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get());
                                            lbl_contaPoupanca.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                            lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                        } else {
                                            Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente2));

                                            saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                            lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));
                                        }

                                        //Registra a movimentação
                                        Model.getInstance().getDatabaseDriver().novaTransacaoContaPoupanca(finalRemetente2, finalDestinatario2, finalValor2, "Transferencia", finalMensagem2, finalCpf1);

                                        //Informa sucesso da transferência
                                        lbl_erro.setText("Transferencia Realizada Com Sucesso");
                                        lbl_erro.setStyle("-fx-text-fill: #00fee5");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //Limpa os campos de texto
                                    fld_destinatario.setText("");
                                    fld_valor.setText("");
                                    fld_mensagem.setText("");
                                } else {
                                    lbl_erro.setText("Senha Incorreta!");
                                    lbl_erro.setStyle("-fx-text-fill: red");
                                }
                            });


                        } else {
                            Model.getInstance().getViewFactory().getMenuPrincipalView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                    }

                } else {

                    confirma.setContentText("Confira os Dados e Confirme a Transferencia\n\nConta Destinataria: "+ fld_destinatario.getText()+"\nNome da(o) Beneficiaria(o): "+nome+" "+sobrenome+"\nValor da Transferencia: R$"+ fld_valor.getText()+"\nMensagem: "+ fld_mensagem.getText()+"\n\n");
                    Optional<ButtonType> confirmaOpcao = confirma.showAndWait();

                    if(Double.parseDouble(fld_valor.getText())  <= montanteRemetente + limite) {
                        if (confirmaOpcao.get() == ButtonType.OK) {

                            PasswordDialog pd = new PasswordDialog();
                            Optional<String> result = pd.showAndWait();

                            String finalRemetente3 = remetente;
                            double finalValor3 = valor;
                            double finalLimite2 = limite;
                            String finalDestinatario3 = destinatario;
                            String finalMensagem3 = mensagem;
                            result.ifPresent(password -> {

                                if (password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                    try {
                                        //Subtrai o valor da conta corrente do remetente e adiciona à conta corrente do destinatário
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaCorrente(finalRemetente3, finalValor3, "SUB", finalLimite2);
                                        Model.getInstance().getDatabaseDriver().updateSaldoContaCorrente(finalDestinatario3, finalValor3, "ADD", finalLimite2);

                                        //Atualiza o saldo da conta corrente
                                        Model.getInstance().getCliente().getContaCorrente().setSaldo(Model.getInstance().getDatabaseDriver().getSaldoConta(finalRemetente3));

                                        saldo[0] = String.valueOf(Model.getInstance().getCliente().getContaCorrente().saldoProperty().get());
                                        lbl_contaCorrente.textProperty().bind(Bindings.concat("R$").concat(saldo[0].replace(".", ",")));

                                        //Registra a movimentação
                                        Model.getInstance().getDatabaseDriver().novaTransacaoContaCorrente(finalRemetente3, finalDestinatario3, finalValor3, "Transferencia", finalMensagem3);

                                        //Informa sucesso da transferencia
                                        lbl_erro.setText("Transferencia Realizada Com Sucesso");
                                        lbl_erro.setStyle("-fx-text-fill: #00fee5");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    //Limpa os campos de texto
                                    fld_destinatario.setText("");
                                    fld_valor.setText("");
                                    fld_mensagem.setText("");
                                } else {
                                    lbl_erro.setText("Senha Incorreta!");
                                    lbl_erro.setStyle("-fx-text-fill: red");
                                }
                            });

                        } else {
                            Model.getInstance().getViewFactory().getMenuPrincipalView();
                        }
                    } else {
                        lbl_erro.setText("Saldo insuficiente!");
                    }

                }
            } else {
                lbl_erro.setText("Conta não encontrada!");
            }
        }
    }
}
