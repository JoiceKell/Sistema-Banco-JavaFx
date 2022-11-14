package br.com.fpbank.banco.Models;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Entities.Movimentacao;
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

    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        //Client Data Section
        this.clientLoginSuccessFlag = false;
        this.cliente = new Cliente("", "", "", 0,"", "", null, null, null, null, null);

        this.latestTransactions = FXCollections.observableArrayList();
        this.allTransactions = FXCollections.observableArrayList();

        //Admin Data Section
        this.adminLoginSuccessFlag = false;
    }

    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() { return databaseDriver; }

    /**
     * Client Method Section
     */

    public boolean getClientLoginSuccesFlag() { return this.clientLoginSuccessFlag; }
    public void setClientLoginSuccessFlag(boolean flag) { this.clientLoginSuccessFlag = flag; }

    public Cliente getCliente() {
        return cliente;
    }

    public void evaluateClientCred(String cpf, String senha) {

        String endereco = "";

        ResultSet resultSet = databaseDriver.getClientData(cpf, senha);
        try {

            if (resultSet.isBeforeFirst()) {

                while(resultSet.next()){
                    this.cliente.cpfProperty().set(resultSet.getString("cpf"));
                    this.cliente.nomeProperty().set(resultSet.getString("nome"));
                    this.cliente.sobrenomeProperty().set(resultSet.getString("sobrenome"));
                    this.cliente.idadeProperty().set(Integer.parseInt(resultSet.getString("idade")));
                    this.cliente.dtNascimentoProperty().set(LocalDate.parse(resultSet.getString("dtNascimento")));
                    this.cliente.emailProperty().set(resultSet.getString("email"));
                    this.cliente.telefoneProperty().set(resultSet.getString("telefone"));
                    this.cliente.senhaProperty().set(resultSet.getString("senha"));

                    endereco = resultSet.getString("endereco");

                    if(resultSet.getString("tipoConta").equals("Corrente")){
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
                System.out.println("Teste: "+ end[0]);

                this.cliente.obterEndereco(end[0], Integer.parseInt(end[1]), end[2], end[3], end[4], end[5], end[6]);

                this.clientLoginSuccessFlag = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private void prepareTransactions(ObservableList<Movimentacao> transactions) {
        ResultSet resultSet = null;
        try {
            resultSet = databaseDriver.getTransactions(this.cliente.getContaPoupanca().numContaProperty().get(), this.cliente.getContaCorrente().numContaProperty().get());
        } catch (Exception e){
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
                String mensagem = resultSet .getString("mensagem");
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

    public void setAllTransactions() {
        prepareTransactions(this.allTransactions);
    }

    public ObservableList<Movimentacao> getAllTransactions() {
        return allTransactions;
    }



    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException)e).
                                getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException)e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException)e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while(t != null) {
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

    public boolean getAdminLoginSuccessFlag() {return this.adminLoginSuccessFlag;}

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }

    public void evaluateAdminCred(String username, String senha) {
        ResultSet resultSet = databaseDriver.getAdminData(username, senha);
        try {
            if (resultSet.isBeforeFirst()){
                this.adminLoginSuccessFlag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
