package br.com.fpbank.banco.Models;

import java.sql.*;

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
            resultSet = statement.executeQuery("Select * from Cliente where CPF='"+cpf+"' and Senha='"+senha+"';");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     *  Admin Section
     */

    /**
     * Utility Methods
     */

}

