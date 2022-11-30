package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Cliente;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClienteCorrenteCellController implements Initializable {
    public Label lbl_StatusConta;
    public Label lbl_NumConta;
    public Label lbl_NumAgencia;
    public Label lbl_DtAbertura;
    public Label lbl_Nome;
    public Label lbl_Sobrenome;
    public Label lbl_Cpf;
    public Label lbl_DtNasc;
    public Label lbl_Idade;
    public Label lbl_Email;
    public Label lbl_Telefone;
    public Label lbl_Saldo;
    public Label lbl_TipoConta;
    public Label lbl_ChequeEspecial;
    public Label lbl_Endereco;

    private final Cliente cliente;

    public ClienteCorrenteCellController(Cliente cliente){
        this.cliente = cliente;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_Cpf.textProperty().bind(cliente.cpfProperty());
        lbl_Email.textProperty().bind(cliente.emailProperty());
        lbl_Idade.textProperty().bind(cliente.idadeProperty().asString());
        lbl_Nome.textProperty().bind(cliente.nomeProperty());
        lbl_Telefone.textProperty().bind(cliente.telefoneProperty());
        lbl_DtNasc.textProperty().bind(cliente.dtNascimentoProperty().asString());

        String endereco = cliente.getEndereco().ruaProperty().get() + ", " + cliente.getEndereco().numProperty().get() + ", " + cliente.getEndereco().bairroProperty().get() + ", " + cliente.getEndereco().cepProperty().get() + ", " + cliente.getEndereco().complementoProperty().get() + ", " + cliente.getEndereco().cidadeProperty().get() + ", " + cliente.getEndereco().estadoProperty().get();
        lbl_Endereco.textProperty().set(endereco);

        lbl_StatusConta.textProperty().bind(cliente.getContaCorrente().statusContaProperty());
        lbl_NumConta.textProperty().bind(cliente.getContaCorrente().numContaProperty());
        lbl_NumAgencia.textProperty().bind(cliente.getContaCorrente().numAgenciaProperty().asString());
        lbl_DtAbertura.textProperty().bind(cliente.getContaCorrente().dtAberturaProperty().asString());
        lbl_Saldo.textProperty().bind(cliente.getContaCorrente().saldoProperty().asString());
        lbl_TipoConta.textProperty().bind(cliente.getContaCorrente().tipoContaProperty());
        lbl_ChequeEspecial.textProperty().bind(cliente.getContaCorrente().limiteProperty().asString());

//        try{
//            if(cliente.getContaCorrente() == null) {
//                if (cliente.getContaPoupanca().tipoContaProperty().get().equals("Poupanca") || cliente.getContaPoupanca() != null) {
//                    System.out.println("Teste conta Poupança: " + cliente.getContaPoupanca().statusContaProperty().get());
//                    lbl_StatusConta.textProperty().bind(cliente.getContaPoupanca().statusContaProperty());
//                    lbl_NumConta.textProperty().bind(cliente.getContaPoupanca().numContaProperty());
//                    lbl_NumAgencia.textProperty().bind(cliente.getContaPoupanca().numAgenciaProperty().asString());
//                    lbl_DtAbertura.textProperty().bind(cliente.getContaPoupanca().dtAberturaProperty().asString());
//                    lbl_Saldo.textProperty().bind(cliente.getContaPoupanca().saldoProperty().asString());
//                    lbl_ChequeEspecial.textProperty().set("-");
//                }
//            } else {
//                if(cliente.getContaCorrente() != null || cliente.getContaCorrente().tipoContaProperty().get().equals("Corrente") || cliente.getContaPoupanca() == null) {
//                    System.out.println("Teste conta Corrente: " + cliente.getContaCorrente().statusContaProperty());
//                    lbl_StatusConta.textProperty().bind(cliente.getContaCorrente().statusContaProperty());
//                    lbl_NumConta.textProperty().bind(cliente.getContaCorrente().numContaProperty());
//                    lbl_NumAgencia.textProperty().bind(cliente.getContaCorrente().numAgenciaProperty().asString());
//                    lbl_DtAbertura.textProperty().bind(cliente.getContaCorrente().dtAberturaProperty().asString());
//                    lbl_Saldo.textProperty().bind(cliente.getContaCorrente().saldoProperty().asString());
//                    lbl_ChequeEspecial.textProperty().bind(cliente.getContaCorrente().limiteProperty().asString());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
