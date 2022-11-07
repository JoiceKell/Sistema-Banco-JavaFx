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
        this.cliente = new Cliente("", "", "", "", "", null, null, null, null);
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
            if (resultSet.isBeforeFirst()){
                this.clientLoginSuccessFlag = true;
            }
                //this.cliente.nomeProperty().set(resultSet.getString("Nome"));
//                this.cliente.sobrenomeProperty().set(resultSet.getString("Sobrenome"));
//                String[] dateParts = resultSet.getString("Date").split("-");
//                LocalDate data = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
//                this.cliente.getContaCorrente().dtCriacaoProperty().set(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
