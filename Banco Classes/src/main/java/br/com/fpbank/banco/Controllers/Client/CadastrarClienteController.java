
// Classe CadastrarClienteController
// Gerencia o Cadastro de um novo cliente


package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Entities.Conta;
import br.com.fpbank.banco.Models.Entities.ContaCorrente;
import br.com.fpbank.banco.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.ResourceBundle;

public class CadastrarClienteController implements Initializable {

    private Cliente cliente;
    private String numAgencia ="5520";
    public TextField fld_nome;
    public TextField fld_sobrenome;
    public TextField fld_cpf;
    public DatePicker dtp_nascimento;
    public TextField fld_telefone;
    public TextField fld_email;
    public TextField fld_rua;
    public TextField fld_numero;
    public TextField fld_cep;
    public TextField fld_complemento;
    public TextField fld_cidade;
    public TextField fld_estado;
    public TextField fld_bairro;
    public CheckBox cb_contaPoupanca;
    public CheckBox cb_contaCorrente;
    public TextField fld_saldoContaPoupanca;
    public TextField fld_saldoContaCorrente;
    public TextField fld_senha;
    public TextField fld_confirmaSenha;
    public Button btn_cadastrar;
    public Label lbl_erro;
    private  boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conta conta = new ContaCorrente(0, null, 0.00, null, null, null, 0.00);

        btn_cadastrar.setOnAction(event -> abrirConta());

        cb_contaCorrente.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                createCheckingAccountFlag = true;
            }
        });

        cb_contaPoupanca.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                createSavingsAccountFlag = true;
            }
        } );

    }

    // Faz-se o cadastro de um novo cliente
    public void abrirConta() {

        this.cliente = new Cliente("", "", "", 0,"", "", "", null, null, null, null);

        //Cria o Cliente
        this.cliente.nomeProperty().set(fld_nome.getText());
        this.cliente.sobrenomeProperty().set(fld_sobrenome.getText());

        if(fld_cpf.getText().length() != 11) {
            fld_cpf.setText("");
            fld_cpf.setPromptText("CPF deve conter 11 dígitos!!!");
            fld_cpf.getStyleClass().setAll("erro");
            return;
        } else if(this.cliente.validarCPF(fld_cpf.getText()) == true && fld_cpf.getText().length() == 11) {
            fld_cpf.getStyleClass().setAll("register");
            this.cliente.cpfProperty().set(fld_cpf.getText());
        } else if(!(this.cliente.validarCPF(fld_cpf.getText()) == true && fld_cpf.getText().length() == 11)){
            fld_cpf.setText("");
            fld_cpf.setPromptText("CPF Inválido!!!");
            fld_cpf.getStyleClass().setAll("erro");
            return;
        } else {
            fld_cpf.setText("");
            fld_cpf.setPromptText("CPF");
            fld_cpf.getStyleClass().setAll("register");
        }

        if(fld_senha.getText().length() < 8) {
            fld_senha.setText("");
            fld_senha.setPromptText("A senha inválida!!!");
            fld_senha.getStyleClass().setAll("erro");
            lbl_erro.setText("A senha deve ter no mínimo 8 digitos!");
            return;
        } else if(!(fld_senha.getText().equals(fld_confirmaSenha.getText()))) {
            fld_senha.setText("");
            fld_confirmaSenha.setText("");
            fld_senha.getStyleClass().setAll("erro");
            fld_confirmaSenha.getStyleClass().setAll("erro");
            lbl_erro.setText("As Senhas não conferem");
            return;
        } else {
            this.cliente.senhaProperty().set(fld_senha.getText());
            fld_senha.setText("");
            fld_confirmaSenha.setText("");
            fld_senha.setPromptText("Senha: ");
            fld_confirmaSenha.setPromptText("Confirmar Senha: ");
            fld_cpf.getStyleClass().setAll("register");
        }

        this.cliente.dtNascimentoProperty().set(dtp_nascimento.getValue());
        this.cliente.idadeProperty().set(calcularIdade(this.cliente.dtNascimentoProperty().get()));
        this.cliente.telefoneProperty().set(fld_telefone.getText());
        this.cliente.emailProperty().set(fld_email.getText());

        this.cliente.obterEndereco(fld_cep.getText(), Integer.parseInt(fld_numero.getText()), fld_rua.getText(), fld_bairro.getText(), fld_cidade.getText(), fld_estado.getText(), fld_complemento.getText());

        String endereco = this.cliente.getEndereco().ruaProperty().get()+ ", " +this.cliente.getEndereco().numProperty().get()+
                ", "+this.cliente.getEndereco().cepProperty().get()+", "+this.cliente.getEndereco().complementoProperty().get()+
                ", "+this.cliente.getEndereco().cidadeProperty().get()+", "+this.cliente.getEndereco().estadoProperty().get()+
                ", "+cliente.getEndereco().bairroProperty().get();

        Model.getInstance().getDatabaseDriver().criaCliente(this.cliente.cpfProperty().get(), this.cliente.nomeProperty().get(), this.cliente.sobrenomeProperty().get(),
                this.cliente.idadeProperty().get(), this.cliente.dtNascimentoProperty().get(), this.cliente.emailProperty().get(), this.cliente.telefoneProperty().get(),
                this.cliente.senhaProperty().get(), endereco);

        // Cria Conta Corrente
        if(createCheckingAccountFlag) {
            criaConta("Corrente");
        }

        // Cria Conta Poupança
        if(createSavingsAccountFlag) {
            criaConta("Poupança");
        }

        lbl_erro.setText("Cadastro Realizado com Sucesso!");
        limpaCampos();

    }

    //Gera um número aleatório, com base no cálculo do digito verificador, da nova Conta criada
    public String geradorNumConta() {
        String resultado = "";
        int digito;
        int y = 0, j = 10, soma = 0;
        String[] multiplica = new String[9];

        int[] conta = new int[6];
        Random gerador = new Random();

        String resultadoNumConta, numConta = "";

        for (int i = 0; i < 5; i++) {
            conta[i] = gerador.nextInt(9);
        }
        for (int i = 0; i < 5; i++) {
            numConta += String.valueOf(conta[i]);
        }

        resultadoNumConta = numConta.substring(0, 5) + "-" + numConta.substring(4);

        String[] numeros = resultadoNumConta.split("-");
        String agenciaConta = this.numAgencia + numeros[0] + numeros[1];

        while (y < 9) {
            int num = Integer.parseInt(agenciaConta.charAt(y) + "");
            if (y % 2 == 0) {
                multiplica[y] = String.valueOf(num * 2);
            } else {
                multiplica[y] = String.valueOf(num * 1);
            }
            y++;
        }

        for (int k = 0; k < 9; k++){
            if(multiplica[k].length() == 2) {
                soma += Integer.parseInt(multiplica[k].charAt(0) + "") + Integer.parseInt(multiplica[k].charAt(1) + "");
            } else {
                soma += Integer.parseInt(multiplica[k].charAt(0) + "");
            }
        }

        digito = 10 - (soma % 10);

        if (digito == 0)
            digito = 0;

        String dig = String.valueOf(digito);

        resultado = String.join("-", numeros[0], dig);

        return resultado;
    }

    //Descobre a idade do Cliente
    public int calcularIdade(final LocalDate aniversario) {
        return Period.between(aniversario, LocalDate.now()).getYears();
    }

    // Cria conta de acordo com a escolha do usuário
    private void criaConta(String accountType) {

        //Cria conta Corrente
        if(accountType.equals("Corrente")) {

            String[] parteDecimal = fld_saldoContaCorrente.getText().split(" ");
            double valor = Double.parseDouble(parteDecimal[1].replace(",", "."));

            if (valor > 0) {
                this.cliente.criarContaCorrenteEspecial(geradorNumConta(), valor, "Corrente", 500.00, LocalDate.now());
                Model.getInstance().getDatabaseDriver().criaContaCorrente(this.cliente.getContaCorrente().numContaProperty().get(), this.cliente.getContaCorrente().saldoProperty().get(), this.cliente.getContaCorrente().tipoContaProperty().get(), this.cliente.getContaCorrente().limiteProperty().get(), this.cliente.getContaCorrente().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
            }

            if (valor == 0) {
                this.cliente.criarContaCorrenteEspecial(geradorNumConta(), valor, "Corrente", 500.00, LocalDate.now());
                Model.getInstance().getDatabaseDriver().criaContaCorrente(this.cliente.getContaCorrente().numContaProperty().get(), this.cliente.getContaCorrente().saldoProperty().get(), this.cliente.getContaCorrente().tipoContaProperty().get(), this.cliente.getContaCorrente().limiteProperty().get(), this.cliente.getContaCorrente().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
            }

            if (!(valor == 0)) {
                Model.getInstance().getDatabaseDriver().novaTransacaoContaCorrente(this.cliente.getContaCorrente().numContaProperty().get(), this.cliente.getContaCorrente().numContaProperty().get(), valor, "Depósito", null);
            }
        }

        //Cria conta Poupança
        if(accountType.equals("Poupança")) {

            String[] parteDecimal = fld_saldoContaPoupanca.getText().split(" ");
            double valor = Double.parseDouble(parteDecimal[1].replace(",", "."));

            if (valor > 0) {
                this.cliente.criarContaPoupanca(geradorNumConta(), valor, "Poupança", LocalDate.now());
                Model.getInstance().getDatabaseDriver().criaContaPoupanca(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaPoupanca().saldoProperty().get(), this.cliente.getContaPoupanca().tipoContaProperty().get(), this.cliente.getContaPoupanca().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
            }

            if (valor == 0) {
                this.cliente.criarContaPoupanca(geradorNumConta(), valor, "Poupança", LocalDate.now());
                Model.getInstance().getDatabaseDriver().criaContaPoupanca(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaPoupanca().saldoProperty().get(), this.cliente.getContaPoupanca().tipoContaProperty().get(), this.cliente.getContaPoupanca().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
                fld_saldoContaPoupanca.getStyleClass().setAll("register");
            }

            if (!(valor == 0)) {
                Model.getInstance().getDatabaseDriver().novaTransacaoContaPoupanca(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaPoupanca().numContaProperty().get(), valor, "Depósito", "", this.cliente.cpfProperty().get());
            }
        }
    }

    private void limpaCampos() {
        fld_nome.setText("");
        fld_sobrenome.setText("");
        fld_cpf.setText("");
        dtp_nascimento.setPromptText(String.valueOf(LocalDate.now()));
        fld_telefone.setText("");
        fld_email.setText("");
        fld_rua.setText("");
        fld_numero.setText("");
        fld_cep.setText("");
        fld_complemento.setText("");
        fld_cidade.setText("");
        fld_estado.setText("");
        fld_bairro.setText("");
        cb_contaPoupanca.setSelected(false);
        cb_contaCorrente.setSelected(false);
        fld_saldoContaPoupanca.setText("");
        fld_saldoContaCorrente.setText("");
        fld_senha.setText("");
        fld_confirmaSenha.setText("");
        dtp_nascimento.setValue(null);
    }

}
