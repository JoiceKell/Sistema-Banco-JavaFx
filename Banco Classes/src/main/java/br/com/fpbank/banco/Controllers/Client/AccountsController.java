package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Models.PasswordDialog;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public TextField phone_fld;
    public TextField email_fld;
    public TextField street_fld;
    public TextField number_fld;
    public TextField zipCode_fld;
    public TextField city_fld;
    public TextField state_fld;
    public TextField district_fld;
    public TextField password_fld;
    public TextField confirmPassword_fld;
    public TextField complemento_fld;
    public Button bt_atualizar;
    public Button bt_desativar;
    public CheckBox cb_dados;
    public CheckBox cb_endereco;
    public CheckBox cb_senha;
    public Label lbl_erro;
    public Label lbl_erroDesativar;
    public RadioButton rb_contaPoupanca;
    public RadioButton rb_contaCorrente;

    private  boolean atualizarDadosFlag = false;
    private boolean atualizarEnderecoFlag = false;
    private boolean atualizarSenhaFlag = false;

    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_atualizar.setOnAction(event -> alterarDados());
        bt_desativar.setOnAction(event -> desativarConta());

        cb_dados.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                atualizarDadosFlag = true;
            }
        });

        cb_endereco.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                atualizarEnderecoFlag = true;
            }
        });

        cb_senha.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                atualizarSenhaFlag = true;
            }
        });

    }
    public void alterarDados() {
        // Atualiza E-mail e Telefone
        if(atualizarDadosFlag) {
            atualizarDadosSelecionado("Dados");
        }

        // Atualiza Campo Endereço
        if(atualizarEnderecoFlag) {
            atualizarDadosSelecionado("Endereço");
        }

        // Atualiza Senha
        if(atualizarSenhaFlag) {
            atualizarDadosSelecionado("Senha");
        }
    }

    public void atualizarDadosSelecionado(String checkBox) {

        String telefone = null;
        String email = null;
        String rua = null;
        int num = 0;
        String cep = null;
        String cidade = null;
        String estado = null;
        String bairro = null;
        String complemento = null;
        String senha = null;

        String cpf = Model.getInstance().getCliente().cpfProperty().get();

        if (checkBox.equals("Dados")) {

            try {
                telefone = phone_fld.getText();
                email = email_fld.getText();

                Model.getInstance().getDatabaseDriver().atualizarDadosCampoDados(telefone, email, cpf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkBox.equals("Endereço")) {
            try {
                rua = street_fld.getText();
                num = Integer.parseInt(number_fld.getText());
                cep = zipCode_fld.getText();
                cidade = city_fld.getText();
                estado = state_fld.getText();
                bairro = district_fld.getText();
                complemento = complemento_fld.getText();

                String endereco = rua+ ", " +num+ ", "+cep+", "+complemento+ ", "+cidade+", "+estado+ ", "+bairro;

                Model.getInstance().getDatabaseDriver().atualizarDadosEndereco(endereco, cpf);
            } catch (Exception e) {
                lbl_erro.setText("Campo endereço não preenchido");
            }
        } else {
            try {
                if (password_fld.getText().equals(confirmPassword_fld.getText())) {
                    senha = password_fld.getText();

                    if (senha.length() >= 8) {
                        Model.getInstance().getDatabaseDriver().atualizarDadosSenha(senha, cpf);
                    } else {
                        lbl_erro.setText("A senha deve conter 8 digítos!");
                    }
                }
            } catch (Exception e) {
                lbl_erro.setText("Senhas não conferem");
            }
        }
    }

    public void desativarConta(){

        lbl_erroDesativar.setText("");

        String cpf = Model.getInstance().getCliente().cpfProperty().get();

        Alert alertaSaldo = new Alert(Alert.AlertType.ERROR);

        if(rb_contaPoupanca.isSelected()) {
            try {
                if (Model.getInstance().getCliente().getContaPoupanca().saldoProperty().get() == 0) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Desativação de conta");
                    alerta.setContentText("Deseja realmente desativar sua conta?");

                    Optional<ButtonType> resposta = alerta.showAndWait();
                    if (resposta.get() == ButtonType.OK) {

                        PasswordDialog pd = new PasswordDialog();
                        Optional<String> result = pd.showAndWait();
                        result.ifPresent(password -> {

                            if(password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                try {
                                    Model.getInstance().getDatabaseDriver().desativarContaPoupanca(cpf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Stage stage = (Stage) bt_desativar.getScene().getWindow();
                                    // Close the client Window
                                    //Platform.exit();
                                    Model.getInstance().getViewFactory().closeStage(stage);
                                    // Show login Window
                                    stage = new Stage();
                                    Model.getInstance().getViewFactory().showMainMenu(stage);
                                    // Set Client Login Succes Flag To False
                                    //Model.getInstance().setClientLoginSuccessFlag(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lbl_erroDesativar.setText("Senha Inválida!");
                            }
                        });
                    } else {
                        Model.getInstance().getViewFactory().getAccountsView();
                    }
                } else {
                    System.out.println("ENTROU NO BAGULHO");
                    alertaSaldo.setTitle("Erro de Desativacao - Saldo Deve estar Zerado");
                    alertaSaldo.setContentText("O SALDO DA CONTA DEVE ESTAR ZERADO PARA DESATIVAR!");
                    alertaSaldo.showAndWait();
                }
            } catch (Exception e) {
                lbl_erroDesativar.setText("Conta Invalida!");
            }
        } else {
            try {
                if (Model.getInstance().getCliente().getContaCorrente().saldoProperty().get() == 0) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Desativação de conta");
                    alerta.setContentText("Deseja realmente desativar sua conta?");

                    Optional<ButtonType> resposta = alerta.showAndWait();
                    if (resposta.get() == ButtonType.OK) {

                        PasswordDialog pd = new PasswordDialog();
                        Optional<String> result = pd.showAndWait();
                        result.ifPresent(password -> {

                            if(password.equals(Model.getInstance().getCliente().senhaProperty().get())) {

                                try {
                                    Model.getInstance().getDatabaseDriver().desativarContaCorrente(cpf);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Stage stage = (Stage) bt_desativar.getScene().getWindow();
                                    // Desloga usuário
                                    Platform.exit();
                                    Model.getInstance().getViewFactory().closeStage(stage);


                                    // Apresenta Tela de Login
                                    stage = new Stage();
                                    Model.getInstance().getViewFactory().showMainMenu(stage);
                                    // Set Client Login Succes Flag To False
                                    Model.getInstance().setClientLoginSuccessFlag(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lbl_erroDesativar.setText("Senha Inválida!");
                            }
                        });
                    } else {
                        Model.getInstance().getViewFactory().getAccountsView();
                    }
                } else {
                    System.out.println("ENTROU NO BAGULHO");
                    alertaSaldo.setTitle("Erro de Desativacao - Saldo Deve estar Zerado");
                    alertaSaldo.setContentText("O SALDO DA CONTA DEVE ESTAR ZERADO PARA DESATIVAR!");
                    alertaSaldo.showAndWait();
                }
            } catch (Exception e) {
                lbl_erroDesativar.setText("Conta Invalida!");
            }
        }
    }

    private void emptyFields() {
        phone_fld.setText("");
        email_fld.setText("");
        street_fld.setText("");
        number_fld.setText("");
        zipCode_fld.setText("");
        city_fld.setText("");
        state_fld.setText("");
        district_fld.setText("");
        complemento_fld.setText("");
        password_fld.setText("");
        confirmPassword_fld.setText("");
        lbl_erro.setText("");
    }

}
