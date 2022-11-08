package br.com.fpbank.banco.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    Connection conexao;

    public DatabaseDriver() {
        try {
            this.conexao = DriverManager.getConnection("jdbc:mysql://34.95.193.96/banco?" +
                    "user=root&password=FPB@261022");
            System.out.println("Conexão realizada com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Client Section
     */

    public ResultSet getClientData(String cpf, String senha) {

        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Cliente WHERE CPF='"+cpf+"' AND Senha='"+senha+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void createClient(String nome, String sobrenome, String cpf, LocalDate dtAniversario, String telefone, String email, String senha, String endereco) {
        Statement statement;
        try {
            statement = this.conexao.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Cliente (CPF, Senha, Nome, Sobrenome, DtNascimento, Email, Telefone, Endereço)" +
                    "VALUES ('"+cpf+"', '"+senha+"', '"+nome+"', '"+sobrenome+"', '"+dtAniversario.toString()+"', '"+email+"', '"+telefone+"', '"+endereco+"');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createCheckingAccount(String proprietario, String numero, double limite, double valor) {
        Statement statement;

        String conta = "Corrente";
        int numAgencia = 1234;
        String status = "Ativa";

        try {
            statement = this.conexao.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Conta (NumeroConta, Saldo, TipoConta, NumAgencia, Status, Limite, Cliente_CPF)" +
                    " VALUES ('"+numero+"', '"+valor+"', '"+conta+"', '"+numAgencia+"', '"+status+"', '"+limite+"','"+proprietario+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createSavingsAccount(String proprietario, String numero, double valor) {
        Statement statement;

        String conta = "Poupança";
        int numAgencia = 1234;
        String status = "Ativa";

        try {
            statement = this.conexao.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Conta (NumeroConta, Saldo, TipoConta, NumAgencia, Status, Cliente_CPF)" +
                    " VALUES ('"+numero+"', '"+valor+"', '"+conta+"', '"+numAgencia+"', '"+status+"', '"+proprietario+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     *  Admin Section
     */

    public ResultSet getAdminData(String username, String senha) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Funcionario WHERE username='"+username+"' AND senha='"+senha+"';");
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Utility Methods
     */

}

