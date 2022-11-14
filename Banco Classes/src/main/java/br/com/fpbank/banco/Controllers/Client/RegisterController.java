package br.com.fpbank.banco.Controllers.Client;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Cliente cliente;
    private String numAgencia ="5520";
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
        this.cliente = new Cliente("", "", "", 0,"", "", "", null, null, null, null);
        //Create Client
        this.cliente.nomeProperty().set(fld_name.getText());
        this.cliente.sobrenomeProperty().set(fld_lastName.getText());

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

        this.cliente.dtNascimentoProperty().set(dtp_birth.getValue());
        this.cliente.idadeProperty().set(calcularIdade(this.cliente.dtNascimentoProperty().get()));
        this.cliente.telefoneProperty().set(fld_phone.getText());
        this.cliente.emailProperty().set(fld_email.getText());

        this.cliente.obterEndereco(fld_zipCode.getText(), Integer.parseInt(fld_number.getText()), fld_street.getText(), fld_district.getText(), fld_city.getText(), fld_state.getText(), fld_addInfo.getText());

        String password = fld_password.getText();
        String confirmPassword = fld_confirmPassword.getText();

        if(password.equals(confirmPassword)) {
            this.cliente.senhaProperty().set(password);
        }

        String endereco = this.cliente.getEndereco().ruaProperty().get()+ ", " +this.cliente.getEndereco().numProperty().get()+
                ", "+this.cliente.getEndereco().cepProperty().get()+", "+this.cliente.getEndereco().complementoProperty().get()+
                ", "+this.cliente.getEndereco().cidadeProperty().get()+", "+this.cliente.getEndereco().estadoProperty().get()+
                ", "+cliente.getEndereco().bairroProperty().get();

        Model.getInstance().getDatabaseDriver().createClient(this.cliente.cpfProperty().get(), this.cliente.nomeProperty().get(), this.cliente.sobrenomeProperty().get(),
                this.cliente.idadeProperty().get(), this.cliente.dtNascimentoProperty().get(), this.cliente.emailProperty().get(), this.cliente.telefoneProperty().get(),
                this.cliente.senhaProperty().get(), endereco);

        // Create Checking account
        if(createCheckingAccountFlag) {
            createAccount("Corrente");
        }

        // Create Saving Account
        if(createSavingsAccountFlag) {
            createAccount("Poupanca");
        }

        lbl_error.setText("Cadastro Realizado com Sucesso!");
        emptyFields();

    }

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

    public int calcularIdade(final LocalDate aniversario) {
        return Period.between(aniversario, LocalDate.now()).getYears();
    }

    private void createAccount(String accountType) {

        // Generate Account Number
        String numConta = geradorNumConta();

        //Create the checking account
        if(accountType.equals("Corrente")) {
            this.cliente.criarContaCorrenteEspecial(geradorNumConta(), Double.parseDouble(fld_checkingAccBal.getText()), "Corrente", 500.00, LocalDate.now());
            Model.getInstance().getDatabaseDriver().createCheckingAccount(this.cliente.getContaCorrente().numContaProperty().get(), this.cliente.getContaCorrente().saldoProperty().get(), this.cliente.getContaCorrente().tipoContaProperty().get(), this.cliente.getContaCorrente().limiteProperty().get(), this.cliente.getContaCorrente().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
        } else {
            this.cliente.criarContaPoupanca(geradorNumConta(), Double.parseDouble(fld_savingAccBal.getText()), "Poupança", LocalDate.now());
            Model.getInstance().getDatabaseDriver().createSavingsAccount(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaPoupanca().saldoProperty().get(), this.cliente.getContaPoupanca().tipoContaProperty().get(), this.cliente.getContaPoupanca().dtAberturaProperty().get(), this.cliente.cpfProperty().get());
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
