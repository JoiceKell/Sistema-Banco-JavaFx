package br.com.fpbank.banco.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmarTransferenciaController implements Initializable {
    public Label lbl_cpf;
    public Label lbl_nome;
    public Label lbl_valor;
    public Label lbl_numConta;
    public PasswordField senha_fld;
    public String cpf;
    public String nome;
    public double valor;
    public String numConta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_cpf.setText(cpf);
        lbl_nome.setText(nome);
        lbl_valor.setText(String.valueOf(valor));
        lbl_numConta.setText(numConta);
    }

    public void inicializarDados(String cpf, String nome, double valor, String numConta) {
        this.cpf = cpf;
        this.nome = nome;
        this.valor = valor;
        this.numConta = numConta;
    }

}
