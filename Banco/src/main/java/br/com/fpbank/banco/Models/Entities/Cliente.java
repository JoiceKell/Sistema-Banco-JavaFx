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

    public StringProperty cpfProperty() {
        return cpf;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty sobrenomeProperty() {
        return sobrenome;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public ObjectProperty<LocalDate> dtNascimentoProperty() {
        return dtNascimento;
    }

    public ObjectProperty<Endereco> enderecoProperty() {
        return endereco;
    }

    public ObjectProperty<ContaEspecial> contaCorrenteProperty() {
        return contaCorrente;
    }

    public ObjectProperty<ContaPoupanca> contaPoupancaProperty() {
        return contaPoupanca;
    }
}
