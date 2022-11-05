package br.com.fpbank.banco.Models.Entities;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class Cliente {

    // Atributos
    private final StringProperty cpf;
    private final StringProperty nome;
    private final StringProperty sobrenome;
    private final StringProperty email;
    private final StringProperty telefone;
    private final ObjectProperty<LocalDate> dtNascimento;
    private final ObjectProperty<Endereco> endereco;
    private final ObjectProperty<ContaEspecial> contaCorrente;
    private final ObjectProperty<ContaPoupanca> contaPoupanca;



    public Cliente(String cpf, String nome, String sobrenome, String email, String telefone, LocalDate dtNascimento, Endereco endereco, ContaEspecial contaCorrente, ContaPoupanca contaPoupanca) {
        this.cpf = new SimpleStringProperty(this, "CPF", cpf);
        this.nome = new SimpleStringProperty(this, "Nome", nome);;
        this.sobrenome = new SimpleStringProperty(this, "Sobrenome", sobrenome);;
        this.email = new SimpleStringProperty(this, "E-mail", email);;
        this.telefone = new SimpleStringProperty(this, "Telefone", telefone);
        this.dtNascimento = new SimpleObjectProperty<>(this, "Data de Nascimento", dtNascimento);
        this.endereco = new SimpleObjectProperty<>(this, "Endereco", endereco);
        this.contaCorrente = new SimpleObjectProperty<>(this, "Conta Corrente", contaCorrente);
        this.contaPoupanca = new SimpleObjectProperty<>(this, "Conta Poupança", contaPoupanca);
    }

    public String getCpf() {
        return cpf.get();
    }

    public StringProperty cpfProperty() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getSobrenome() {
        return sobrenome.get();
    }

    public StringProperty sobrenomeProperty() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome.set(sobrenome);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public LocalDate getDtNascimento() {
        return dtNascimento.get();
    }

    public ObjectProperty<LocalDate> dtNascimentoProperty() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento.set(dtNascimento);
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public ObjectProperty<Endereco> enderecoProperty() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco.set(endereco);
    }

    public ContaEspecial getContaCorrente() {
        return contaCorrente.get();
    }

    public ObjectProperty<ContaEspecial> contaCorrenteProperty() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaEspecial contaCorrente) {
        this.contaCorrente.set(contaCorrente);
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca.get();
    }

    public ObjectProperty<ContaPoupanca> contaPoupancaProperty() {
        return contaPoupanca;
    }

    public void setContaPoupanca(ContaPoupanca contaPoupanca) {
        this.contaPoupanca.set(contaPoupanca);
    }
}
