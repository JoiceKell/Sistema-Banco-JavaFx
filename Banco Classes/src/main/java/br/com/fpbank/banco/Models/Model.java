package br.com.fpbank.banco.Models;

import br.com.fpbank.banco.Models.Entities.*;
import br.com.fpbank.banco.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;

    //Client Data Section
    private final Cliente cliente;
    private boolean clientLoginSuccessFlag;
    private final ObservableList<Movimentacao> latestTransactions;
    private final ObservableList<Movimentacao> allTransactions;

    //Admin Data Section
    private boolean adminLoginSuccessFlag;
    private final ObservableList<Cliente> clientesPoupanca;
    private final ObservableList<Cliente> clientesCorrente;
    private final ObservableList<Movimentacao> movimentacoes;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        //Client Data Section
        this.clientLoginSuccessFlag = false;
        this.cliente = new Cliente("", "", "", 78, "", "", null, null, null, null, null);

        this.latestTransactions = FXCollections.observableArrayList();
        this.allTransactions = FXCollections.observableArrayList();

        //Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.clientesPoupanca = FXCollections.observableArrayList();
        this.clientesCorrente = FXCollections.observableArrayList();
        this.movimentacoes = FXCollections.observableArrayList();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    /**
     * Client Method Section
     */

    public boolean getClientLoginSuccesFlag() {
        return this.clientLoginSuccessFlag;
    }

    public void setClientLoginSuccessFlag(boolean flag) {
        this.clientLoginSuccessFlag = flag;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void evaluateClientCred(String cpf, String senha) {

        String endereco = "";

        ResultSet resultSet = databaseDriver.getClientData(cpf, senha);
        try {

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {
                    this.cliente.cpfProperty().set(resultSet.getString("cpf"));
                    this.cliente.nomeProperty().set(resultSet.getString("nome"));
                    this.cliente.sobrenomeProperty().set(resultSet.getString("sobrenome"));
                    this.cliente.idadeProperty().set(Integer.parseInt(resultSet.getString("idade")));
                    this.cliente.dtNascimentoProperty().set(LocalDate.parse(resultSet.getString("dtNascimento")));
                    this.cliente.emailProperty().set(resultSet.getString("email"));
                    this.cliente.telefoneProperty().set(resultSet.getString("telefone"));
                    this.cliente.senhaProperty().set(resultSet.getString("senha"));

                    endereco = resultSet.getString("endereco");

                    if (resultSet.getString("tipoConta").equals("Corrente")) {
                        this.cliente.criarContaCorrenteEspecial(Integer.parseInt(resultSet.getString("numAgencia")), resultSet.getString("numConta"),
                                Double.parseDouble(resultSet.getString("saldo")), resultSet.getString("tipoConta"), resultSet.getString("status"),
                                Double.parseDouble(resultSet.getString("limite")), LocalDate.parse(resultSet.getString("dtAbertura")));
                    } else {
                        this.cliente.criarContaPoupanca((Integer.parseInt(resultSet.getString("numAgencia"))), resultSet.getString("numConta"),
                                Double.parseDouble(resultSet.getString("saldo")), resultSet.getString("tipoConta"), resultSet.getString("status"),
                                LocalDate.parse(resultSet.getString("dtAbertura")));
                    }
                }

                String[] end = endereco.split(", ");
                System.out.println("Teste: " + end[0]);

                this.cliente.obterEndereco(end[0], Integer.parseInt(end[1]), end[2], end[3], end[4], end[5], end[6]);

                this.clientLoginSuccessFlag = true;
            }
        } catch (SQLException e) {
            imprimirSQLException(e);
        }
    }

    private void prepareTransactions(ObservableList<Movimentacao> transactions) {

        ResultSet resultSet = null;

        try {
            resultSet = databaseDriver.getTransactions(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaCorrente().numContaProperty().get());
        } catch (Exception e) {
            try {
                resultSet = databaseDriver.getTransactions(this.cliente.getContaPoupanca().numContaProperty().get(), null);
            } catch (Exception k) {
                resultSet = databaseDriver.getTransactions(this.cliente.getContaCorrente().numContaProperty().get(), null);
            }
        }

        try {
            while (resultSet.next()) {
                String remetente = resultSet.getString("contaNumContaOrigem");
                String destinatario = resultSet.getString("contaNumContaDestino");
                double montante = resultSet.getDouble("montante");
                String[] dateParts = resultSet.getString("dtMovimentacao").split("-");
                LocalDate data = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                String tipoMovimentacao = resultSet.getString("tipoMovimentacao");
                String mensagem = resultSet.getString("mensagem");
                transactions.add(new Movimentacao(remetente, destinatario, montante, data, tipoMovimentacao, mensagem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLatestTransactions() {
        prepareTransactions(this.latestTransactions);
    }

    public ObservableList<Movimentacao> getLatestTransactions() {
        return latestTransactions;
    }

    public void setTodasTransacoes() {
        prepareTransactions(this.allTransactions);
    }

    public ObservableList<Movimentacao> getTodasTransacoes() {
        return allTransactions;
    }


    public static void imprimirSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException) e).
                                getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException) e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;

        return false;
    }

    /**
     * Admin Method Section
     */

    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred(String username, String senha) {
        ResultSet resultSet = databaseDriver.getAdminData(username, senha);
        try {
            if (resultSet.isBeforeFirst()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Cliente> getClientesContaPoupanca() {

        if(clientesPoupanca != null)
            return clientesPoupanca;
        else
            return null;
    }

    public ObservableList<Cliente> getClientesContaCorrente() {

        if(clientesCorrente != null)
            return clientesCorrente;
        else
            return null;
    }


    public void setClientesPoupanca() {
        ContaEspecial checkingAccount;
        ContaPoupanca savingsAccount;
        Endereco endereco;
        ResultSet resultSet = Model.getInstance().databaseDriver.obterTodosClientesDadosPoupanca();

        try {
            while (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                int idade = resultSet.getInt("idade");
                String[] dataParts = resultSet.getString("dtNascimento").split("-");
                LocalDate dtNascimento = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
                String email = resultSet.getString("email");
                String telefone = resultSet.getString("telefone");

                String nomeCompleto = nome + " " +sobrenome;
                endereco = getEndereco(cpf);
                //checkingAccount = getCheckingAccount(cpf);
                savingsAccount = getSavingsAccount(cpf);

                //if (checkingAccount != null){
                    //this.clientes.add(new Cliente(cpf, nome, sobrenome, idade, email, telefone, dtNascimento, endereco));
                    //clientes.add(new Cliente(cpf, nome, sobrenome, idade, email, telefone, dtNascimento, endereco, checkingAccount, null));
                //}
                //if(savingsAccount != null) {
                    clientesPoupanca.add(new Cliente(cpf, nomeCompleto, idade, email, telefone, dtNascimento, endereco, null, savingsAccount));
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setClientesCorrente() {
        ContaEspecial checkingAccount;
        ContaPoupanca savingsAccount;
        Endereco endereco;
        ResultSet resultSet = Model.getInstance().databaseDriver.obterTodosClientesDadosCorrente();

        try {
            while (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                int idade = resultSet.getInt("idade");
                String[] dataParts = resultSet.getString("dtNascimento").split("-");
                LocalDate dtNascimento = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
                String email = resultSet.getString("email");
                String telefone = resultSet.getString("telefone");

                String nomeCompleto = nome + " " +sobrenome;
                endereco = getEndereco(cpf);
                checkingAccount = getCheckingAccount(cpf);
                //savingsAccount = getSavingsAccount(cpf);

                //if (checkingAccount != null){
                //this.clientes.add(new Cliente(cpf, nome, sobrenome, idade, email, telefone, dtNascimento, endereco));
                clientesCorrente.add(new Cliente(cpf, nome, sobrenome, idade, email, telefone, dtNascimento, endereco, checkingAccount, null));
                //}
                //if(savingsAccount != null) {
                //clientesPoupanca.add(new Cliente(cpf, nomeCompleto, idade, email, telefone, dtNascimento, endereco, null, savingsAccount));
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Endereco getEndereco(String cpf) {

        String end = null;

        Endereco endereco = null;

        ResultSet resultSet = databaseDriver.getEnderecoData(cpf);

        try {
            while (resultSet.next()) {
                end = resultSet.getString("endereco");
            }

            String[] enderecoSeparado = end.split(", ");

            endereco = new Endereco(enderecoSeparado[0], Integer.parseInt(enderecoSeparado[1]), enderecoSeparado[2], enderecoSeparado[3], enderecoSeparado[4], enderecoSeparado[5], enderecoSeparado[6]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return endereco;
    }

    public ContaEspecial getCheckingAccount(String cpf) {

        int agencia = 0;
        String numConta = null;
        double saldo = 0.00;
        String tipoConta = null;
        String status = null;
        LocalDate dtAbertura = null;
        double limite = 0.00;

        ContaEspecial account = null;

        ResultSet resultSet = databaseDriver.getCheckingAccountData(cpf);

        try {
            while (resultSet.next()) {
                agencia = resultSet.getInt("numAgencia");
                numConta = resultSet.getString("numConta");
                saldo = resultSet.getDouble("saldo");
                tipoConta = resultSet.getString("tipoConta");
                status = resultSet.getString("status");
                String[] dataParts = resultSet.getString("dtAbertura").split("-");
                dtAbertura = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
                limite = resultSet.getDouble("limite");
            }
            account = new ContaEspecial(agencia, numConta, saldo, tipoConta, status, dtAbertura, limite);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (account != null)
            return account;
        else
            return null;
    }

    public ContaPoupanca getSavingsAccount(String cpf) {

        int agencia = 0;
        String numConta = null;
        double saldo = 0.00;
        String tipoConta = null;
        String status = null;
        LocalDate dtAbertura = null;
        int aniversario = 0;

        ContaPoupanca account = null;

        ResultSet resultSet = databaseDriver.getSavingsAccountData(cpf);
        try {
            while (resultSet.next()) {
                agencia = resultSet.getInt("numAgencia");
                numConta = resultSet.getString("numConta");
                saldo = resultSet.getDouble("saldo");
                tipoConta = resultSet.getString("tipoConta");
                status = resultSet.getString("status");
                String[] dataParts = resultSet.getString("dtAbertura").split("-");
                dtAbertura = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
            }

            account = new ContaPoupanca(agencia, numConta, saldo, tipoConta, status, dtAbertura);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(account != null)
            return account;
        else
            return null;
    }

    public ObservableList<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes() {
        System.out.println("setMovimentacoes");
        ResultSet resultSet = databaseDriver.obterTodasMovimentacoes();
        try {
            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                String contaOrigem = resultSet.getString("contaNumContaOrigem");
                String contaDestino = resultSet.getString("contaNumContaDestino");;
                double valor = resultSet.getDouble("montante");
                String[] dataParts = resultSet.getString("dtMovimentacao").split("-");
                LocalDate dtMovimentacao = LocalDate.of(Integer.parseInt(dataParts[0]), Integer.parseInt(dataParts[1]), Integer.parseInt(dataParts[2]));
                String tipoMovimentacao = resultSet.getString("tipoMovimentacao");

                String nomeCompleto = nome + " " +sobrenome;

                movimentacoes.add(new Movimentacao(nomeCompleto, contaOrigem, contaDestino, valor, dtMovimentacao, tipoMovimentacao));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
