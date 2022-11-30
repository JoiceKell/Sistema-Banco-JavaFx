package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Cliente {

    // Atributos
    private final StringProperty cpf;
    private final StringProperty nome;
    private final StringProperty sobrenome;
    private final IntegerProperty idade;
    private final StringProperty email;
    private final StringProperty senha;
    private final StringProperty telefone;
    private final ObjectProperty<LocalDate> dtNascimento;
    private final ObjectProperty<Endereco> endereco;
    private final ObjectProperty<ContaEspecial> contaCorrente;
    private final ObjectProperty<ContaPoupanca> contaPoupanca;

    public Cliente(String cpf, String nome, String sobrenome, int idade, String email, String senha, String telefone, LocalDate dtNascimento, Endereco endereco, ContaEspecial contaCorrente, ContaPoupanca contaPoupanca) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", sobrenome);
        this.idade = new SimpleIntegerProperty(this, "Idade", idade);
        this.email = new SimpleStringProperty(this, "E-mail", email);
        this.senha = new SimpleStringProperty(this, "Senha", senha);
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", endereco);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", contaCorrente);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", contaPoupanca);
    }

    public Cliente(String cpf, String nome, int idade, String email, String telefone, LocalDate dtNascimento, Endereco endereco, ContaEspecial contaCorrente, ContaPoupanca contaPoupanca) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", null);
        this.idade = new SimpleIntegerProperty(this, "Idade", idade);
        this.email = new SimpleStringProperty(this, "E-mail", email);
        this.senha = new SimpleStringProperty(this, "Senha", null);
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", endereco);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", contaCorrente);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", contaPoupanca);
    }

    public Cliente(String cpf, String nome, String sobrenome, int idade, String email, String telefone, LocalDate dtNascimento, Endereco endereco, ContaEspecial contaCorrente, ContaPoupanca contaPoupanca) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", sobrenome);
        this.idade = new SimpleIntegerProperty(this, "Idade", idade);
        this.email = new SimpleStringProperty(this, "E-mail", email);
        this.senha = new SimpleStringProperty(this, "Senha", null);
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", endereco);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", contaCorrente);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", contaPoupanca);
    }

    public Cliente(String cpf, String nome, String sobrenome, int idade, String email, String telefone, LocalDate dtNascimento, Endereco endereco) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", sobrenome);
        this.idade = new SimpleIntegerProperty(this, "Idade", idade);
        this.email = new SimpleStringProperty(this, "E-mail", email);
        this.senha = new SimpleStringProperty(this, "Senha", null);
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", endereco);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", null);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", null);
    }

    public Cliente(String cpf, String nome, String sobrenome, int idade, String email, String senha, String telefone, LocalDate dtNascimento) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", sobrenome);
        this.idade = new SimpleIntegerProperty(this, "Idade", idade);
        this.email = new SimpleStringProperty(this, "E-mail", email);
        this.senha = new SimpleStringProperty(this, "Senha", senha);
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", null);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", null);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", null);
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty sobrenomeProperty() {
        return sobrenome;
    }

    public IntegerProperty idadeProperty() {
        return idade;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty senhaProperty() {
        return senha;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public ObjectProperty<LocalDate> dtNascimentoProperty() {
        return dtNascimento;
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public ObjectProperty<Endereco> enderecoProperty() {
        return endereco;
    }

    public ContaEspecial getContaCorrente() {
        return contaCorrente.get();
    }

    public ObjectProperty<ContaEspecial> contaCorrenteProperty() {
        return contaCorrente;
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca.get();
    }

    public ObjectProperty<ContaPoupanca> contaPoupancaProperty() {
        return contaPoupanca;
    }

    public boolean validarCPF(String cpf) {

        String cpfResultante = "";
        int digito1 = 0, digito2 = 0;
        int i = 0, j = 10, soma = 0;

        while (i < 9) {
            soma += (Integer.parseInt((cpf.charAt(i) + "")) * j);
            i++;
            j--;
        }
        digito1 = soma % 11;
        if (digito1 < 2)
            digito1 = 0;
        else
            digito1 = (11 - digito1);

        cpfResultante = cpf.substring(0, 9) + digito1;

        i = 0;
        j = 11;
        soma = 0;

        while (i < 10) {
            soma += (Integer.parseInt((cpfResultante.charAt(i) + "")) * j);
            i++;
            j--;
        }

        digito2 = soma % 11;
        if (digito2 < 2)
            digito2 = 0;
        else
            digito2 = (11 - digito2);

        cpfResultante += digito2;

        if (cpf.equals(cpfResultante)) {
            return true;
        }
        return false;
    }

    public void obterEndereco(String cep, int num, String rua, String bairro, String cidade, String estado, String complemento) {
        this.enderecoProperty().set(new Endereco(cep, num, rua, bairro, cidade, estado, complemento));
    }

    public void criarContaCorrenteEspecial(String numConta, double saldo, String tipoConta, double limite, LocalDate dtCriacao){
        this.contaCorrenteProperty().set(new ContaEspecial(numConta, saldo, tipoConta, limite,  dtCriacao));
    }

    public void criarContaCorrenteEspecial(int numAgencia, String numConta, double saldo, String tipoConta, String status, double limite, LocalDate dtCriacao){
        this.contaCorrenteProperty().set(new ContaEspecial(numAgencia, numConta, saldo, tipoConta, status, limite,  dtCriacao));
    }

    public void criarContaPoupanca(String numConta, double saldo, String tipoConta, LocalDate dtCriacao){
        this.contaPoupancaProperty().set(new ContaPoupanca(numConta, saldo, tipoConta, dtCriacao));
    }

    public void criarContaPoupanca(int numAgencia, String numConta, double saldo, String tipoConta, String status, LocalDate dtCriacao){
        this.contaPoupancaProperty().set(new ContaPoupanca(numAgencia, numConta, saldo, tipoConta, status,  dtCriacao));
    }

}
