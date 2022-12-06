
// Classe ManterContaAtuDesController
// Mantem Conta nas opções de Atualizar dados, e-mail - telefone - endereço - senha, e Desativar conta

package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Models.PasswordDialog;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManterContaAtuDesController implements Initializable {
    public TextField fld_telefone;
    public TextField fld_email;
    public TextField fld_rua;
    public TextField fld_numero;
    public TextField fld_cep;
    public TextField fld_cidade;
    public TextField fld_estado;
    public TextField fld_bairro;
    public TextField fld_senha;
    public TextField fld_confirmaSenha;
    public TextField fld_complemento;
    public Button btn_atualizar;
    public Button btn_desativar;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_atualizar.setOnAction(event -> alterarDados());
        btn_desativar.setOnAction(event -> desativarConta());

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

    // Atualiza os dados de acordo com a seleção de escolha dentre as opções de Dados(Telefone e E-mail), Endereço e Senha
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
                telefone = fld_telefone.getText();
                email = fld_email.getText();

                Model.getInstance().getDatabaseDriver().atualizarDadosCampoDados(telefone, email, cpf);
                limpaCampos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (checkBox.equals("Endereço")) {
            try {
                rua = fld_rua.getText();
                num = Integer.parseInt(fld_numero.getText());
                cep = fld_cep.getText();
                cidade = fld_cidade.getText();
                estado = fld_estado.getText();
                bairro = fld_bairro.getText();
                complemento = fld_complemento.getText();

                String endereco = rua+ ", " +num+ ", "+cep+", "+complemento+ ", "+cidade+", "+estado+ ", "+bairro;

                Model.getInstance().getDatabaseDriver().atualizarDadosEndereco(endereco, cpf);
                limpaCampos();
            } catch (Exception e) {
                lbl_erro.setText("Campo endereço não preenchido");
            }
        } else {
            try {
                if (fld_senha.getText().equals(fld_confirmaSenha.getText())) {
                    senha = fld_senha.getText();

                    if (senha.length() >= 8) {
                        Model.getInstance().getDatabaseDriver().atualizarDadosSenha(senha, cpf);
                    } else {
                        lbl_erro.setText("A senha deve conter 8 digítos!");
                    }
                }
                limpaCampos();
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
                                    Stage stage = (Stage) btn_desativar.getScene().getWindow(); //Obtendo a janela atual
                                    stage.close(); //Fechando o Stage
                                    Model.getInstance().getViewFactory().showMainMenu(new Stage());
                                    Model.getInstance().setFlagSucessoLoginCliente(false);
                                    Platform.exit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lbl_erroDesativar.setText("Senha Inválida!");
                            }
                        });
                    } else {
                        Model.getInstance().getViewFactory().getContaView();
                    }
                } else {
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
                                    Stage stage = (Stage) btn_desativar.getScene().getWindow(); //Obtendo a janela atual
                                    stage.close(); //Fechando o Stage
                                    Model.getInstance().getViewFactory().showMainMenu(new Stage());
                                    Model.getInstance().setFlagSucessoLoginCliente(false);
                                    Platform.exit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lbl_erroDesativar.setText("Senha Inválida!");
                            }
                        });
                    } else {
                        Model.getInstance().getViewFactory().getContaView();
                    }
                } else {
                    alertaSaldo.setTitle("Erro de Desativacao - Saldo Deve estar Zerado");
                    alertaSaldo.setContentText("O SALDO DA CONTA DEVE ESTAR ZERADO PARA DESATIVAR!");
                    alertaSaldo.showAndWait();
                }
            } catch (Exception e) {
                lbl_erroDesativar.setText("Conta Invalida!");
            }
        }
    }

    private void limpaCampos() {
        fld_telefone.setText("");
        fld_email.setText("");
        fld_rua.setText("");
        fld_numero.setText("");
        fld_cep.setText("");
        fld_cidade.setText("");
        fld_estado.setText("");
        fld_bairro.setText("");
        fld_complemento.setText("");
        fld_senha.setText("");
        fld_confirmaSenha.setText("");
        lbl_erro.setText("");
    }

}
