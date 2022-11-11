package br.com.fpbank.banco.Models;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Entities.ContaEspecial;
import br.com.fpbank.banco.Models.Entities.ContaPoupanca;
import br.com.fpbank.banco.Models.Entities.Endereco;
import br.com.fpbank.banco.Views.AccountType;
import br.com.fpbank.banco.Views.ViewFactory;

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

    //Admin Data Section
    private boolean adminLoginSuccessFlag;

    private Model(){
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        //Client Data Section
        this.clientLoginSuccessFlag = false;
        this.cliente = new Cliente("", "", "", "", "", null, null, null, null, null);
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

        ResultSet resultSet = databaseDriver.getClientData(cpf, senha);
        try {
            if (resultSet.isBeforeFirst()) {

                //this.cliente.nomeProperty().set(resultSet.getString("Nome"));
                //this.cliente.sobrenomeProperty().set(resultSet.getString("Sobrenome"));
                // String[] dateParts = resultSet.getString("Date").split("-");
                // LocalDate data = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                // this.cliente.getContaCorrente().dtCriacaoProperty().set(data);
                this.clientLoginSuccessFlag = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
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
