
//Classe DatabaseDriver
//Trata de estabelecer conexão com o Banco de Dados e gerenciar interações com o mesmo

package br.com.fpbank.banco.Models;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseDriver {
    Connection conexao;

    public DatabaseDriver() {
        try {
            this.conexao = DriverManager.getConnection("jdbc:mysql://34.95.193.96/banco?" +
                    "user=root&password=FPB@261022");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Métodos referentes ao Cliente

    public ResultSet getDadosCliente(String cpf, String senha) {

        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select Cliente.*, Conta.* from Cliente inner join Conta on Cliente.cpf = Conta.clienteCpf where cpf='"+cpf+"' AND senha='"+senha+"' and status != 'Desativada';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getTransacoes(String numConta1, String numConta2) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select * from Movimentacao where contaNumContaOrigem = '"+numConta1+"' or contaNumContaOrigem = '"+numConta2+"' or contaNumContaDestino = '"+numConta1+"' or contaNumContaDestino = '"+numConta2+"' ORDER BY idMovimentacao desc ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String verificarContas(String cpfRemetente, String numContaRemetente) {
        Statement statement;
        ResultSet resultSet;
        String numConta = null;
        try{
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select numConta from Conta where clienteCpf = '"+cpfRemetente+"' and numConta != '"+numContaRemetente+"' ");
            while (resultSet.next()) {
                numConta = resultSet.getString("numConta");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numConta;
    }

    //Retorna o saldo da Conta
    public double getSaldoConta(String numConta) {
        Statement statement;
        ResultSet resultSet;
        double montante = 0;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select * from Conta where numConta='"+numConta+"';");
            while (resultSet.next()) {
                montante = resultSet.getDouble("saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return montante;
    }

    //Método para somar ou subtrair valor do saldo de Conta Poupança, dependendo do parâmetro operacao
    public void updateSaldoContaPoupanca(String numConta, double montante, String operacao) {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("Select * from Conta where tipoConta = 'Poupança' and numConta = '"+numConta+"' ");
            double novoSaldo = 0.00;

            //Soma ao saldo
            if (operacao.equals("ADD")) {
                if(resultSet.next()) {
                    novoSaldo = resultSet.getDouble("saldo") + montante;
                    statement.executeUpdate("update Conta set saldo=" + novoSaldo + " where numConta='" + numConta + "';");
                }
            //Subtrai do saldo
            } else {
                if (resultSet.next() && resultSet.getDouble("saldo") >= montante) {
                    novoSaldo = resultSet.getDouble("saldo") - montante;
                    statement.executeUpdate("update Conta set saldo=" + novoSaldo + " where numConta='" + numConta + "' ;");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Método para somar ou subtrair valor do saldo de Conta Corrente, dependendo do parâmetro operacao
    public void updateSaldoContaCorrente(String numConta, double montante, String operacao, double limite) {
        Statement statement;
        ResultSet resultSet;

        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("Select * from Conta where tipoConta = 'Corrente' and numConta = '"+numConta+"' ");
            double novoSaldo = 0.00;

            //Soma ao saldo
            if (operacao.equals("ADD")) {
                if(resultSet.next()) {
                    novoSaldo = resultSet.getDouble("saldo") + montante;
                    statement.executeUpdate("update Conta set saldo=" + novoSaldo + " where numConta='" + numConta + "';");
                }
            //Subtrai do saldo
            } else {
                if (resultSet.next() && resultSet.getDouble("saldo") + limite >= montante) {
                    novoSaldo = resultSet.getDouble("saldo") - montante;
                    statement.executeUpdate("update Conta set saldo=" + novoSaldo + " where numConta='" + numConta + "' ;");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desativarContaPoupanca(String cpf) {
        PreparedStatement pstm = null;
        try {
            this.conexao.createStatement();
            pstm = this.conexao.prepareStatement("update Conta set status = 'Desativada' where clienteCpf = '" + cpf + "' && tipoConta = 'Poupança';");
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desativarContaCorrente(String cpf) {
        PreparedStatement pstm = null;
        try {
            this.conexao.createStatement();
            pstm = this.conexao.prepareStatement("update Conta set status = 'Desativada' where clienteCpf = '" + cpf + "' && tipoConta = 'Corrente';");
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarDadosCampoDados(String telefone, String email, String cpf) {

        PreparedStatement pstm = null;

        try {
            if(!(telefone.equals("")) && !(email.equals(""))) {
                this.conexao.createStatement();
                pstm = this.conexao.prepareStatement("update Cliente set telefone = '" + telefone + "', email = '" + email + "' where cpf = '" + cpf + "';");
                pstm.execute();
                pstm.close();
            } else if(!(telefone.equals(""))) {
                    this.conexao.createStatement();
                    pstm = this.conexao.prepareStatement("update Cliente set telefone = '" + telefone + "' where cpf = '" + cpf + "';");
                    pstm.execute();
                    pstm.close();
            } else {
                this.conexao.createStatement();
                pstm = this.conexao.prepareStatement( "update Cliente set email = '" + email + "' where cpf = '" + cpf + "';");
                pstm.execute();
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarDadosEndereco(String endereco, String cpf) {

        PreparedStatement pstm = null;

        try {
            if(!(endereco.equals(""))){
                this.conexao.createStatement();
                pstm = this.conexao.prepareStatement("update Cliente set endereco = '" + endereco + "' where cpf = '" + cpf + "';");
                pstm.execute();
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarDadosSenha(String senha, String cpf) {

        PreparedStatement pstm = null;

        try {
            if(!(senha.equals(""))){
                this.conexao.createStatement();
                pstm = this.conexao.prepareStatement("update Cliente set senha = '" + senha + "' where cpf = '" + cpf + "';");
                pstm.execute();
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet obterTodosClientesDadosPoupanca() {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select Cliente.*, Conta.* " +
                    "from Cliente inner join Conta on Cliente.cpf = Conta.clienteCpf " +
                    "WHERE tipoConta='Poupança';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet obterTodosClientesDadosCorrente() {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select Cliente.*, Conta.* " +
                    "from Cliente inner join Conta on Cliente.cpf = Conta.clienteCpf " +
                    "WHERE tipoConta='Corrente';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet obterTodasMovimentacoes() {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select Cliente.nome, Cliente.sobrenome, Movimentacao.contaNumContaOrigem, Movimentacao.contaNumContaDestino, Cliente.nome as nomeDestino, Cliente.sobrenome as sobrenomeDestino, Conta.clienteCpf as cpfDestino, Movimentacao.montante, Movimentacao.dtMovimentacao, Movimentacao.tipoMovimentacao " +
                    " from Cliente inner join Conta on Cliente.cpf = Conta.clienteCpf" +
                    " inner join Movimentacao on Conta.numConta = Movimentacao.contaNumContaOrigem" +
                    " order by idMovimentacao; ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getDadosContaCorrente(String cpf) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("Select * from Conta where tipoConta = 'Corrente' and clienteCpf= '"+cpf+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getDadosContaPoupanca(String cpf) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("Select * from Conta where tipoConta = 'Poupança' and clienteCpf= '"+cpf+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getDadosEndereco(String cpf) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("Select endereco from Cliente where cpf= '"+cpf+"' ;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Cria e registra nova Transação
    public void novaTransacaoContaCorrente(String remetente, String destinatario, double montante, String tipoMovimentacao, String mensagem) {
        Statement statement;
        try {
            statement = this.conexao.createStatement();
            LocalDate dataTransacao = LocalDate.now();
            statement.executeUpdate("insert into " +
                    "Movimentacao(contaNumContaOrigem, contaNumContaDestino, montante, dtMovimentacao, tipoMovimentacao, mensagem) " +
                    "values ('"+remetente+"', '"+destinatario+"', '"+montante+"', '"+dataTransacao+"', '"+tipoMovimentacao+"', '"+mensagem+"'); ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Cria e registra nova Transação
    public void novaTransacaoContaPoupanca(String remetente, String destinatario, double montante, String tipoMovimentacao, String mensagem, String cpf) {
        Statement statement;
        Statement statement2;
        try {
            statement = this.conexao.createStatement();
            LocalDate dataTransacao = LocalDate.now();
            statement.executeUpdate("insert into " +
                    "Movimentacao(contaNumContaOrigem, contaNumContaDestino, montante, dtMovimentacao, tipoMovimentacao, mensagem) " +
                    "values ('"+remetente+"', '"+destinatario+"', '"+montante+"', '"+dataTransacao+"', '"+tipoMovimentacao+"', '"+mensagem+"'); ");
            statement.close();

            statement2 = this.conexao.createStatement();
            LocalDate diaTransacao = LocalDate.now();
            int day = diaTransacao.getDayOfMonth();
            statement2.executeUpdate("UPDATE Conta SET aniversario = '"+day+"', dtUltimAtu = '"+dataTransacao+"' WHERE (`numConta` = '"+destinatario+"') and (`clienteCpf` = '"+cpf+"');  ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criaCliente(String cpf, String nome, String sobrenome, int idade, LocalDate dtAniversario, String email, String telefone, String senha, String endereco) {
        Statement statement;

        try {
            statement = this.conexao.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Cliente (cpf, nome, sobrenome, idade, dtNascimento, email, telefone, senha, endereco)" +
                    "VALUES ('"+cpf+"', '"+nome+"', '"+sobrenome+"', '"+idade+"' ,'"+dtAniversario.toString()+"', '"+email+"', '"+telefone+"', '"+senha+"' ,'"+endereco+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void criaContaCorrente(String numConta, double valor, String tipoConta, double limite, LocalDate dtAbertura, String proprietario) {
        Statement statement;

        String conta = "Corrente";

        try {
            statement = this.conexao.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "Conta (numConta, saldo, tipoConta, limite, dtAbertura, clienteCpf)" +
                    " VALUES ('"+numConta+"','"+valor+"', '"+tipoConta+"', '"+limite+"', '"+dtAbertura+"' ,'"+proprietario+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void criaContaPoupanca(String numConta, double valor, String tipoConta, LocalDate dtAbertura, String proprietario) {
        Statement statement;

        String conta = "Poupança";

        try {
            statement = this.conexao.createStatement();

            LocalDate diaTransacao = LocalDate.now();
            int day = diaTransacao.getDayOfMonth();

            if(valor != 0) {
                statement.executeUpdate("INSERT INTO " +
                        "Conta (numConta, saldo, tipoConta, dtAbertura, aniversario, dtUltimAtu, clienteCpf)" +
                        " VALUES ('" + numConta + "','" + valor + "', '" + tipoConta + "', '" + dtAbertura + "', '" + day + "', '" + diaTransacao + "', '" + proprietario + "')");
            } else {
                statement.executeUpdate("INSERT INTO " +
                        "Conta (numConta, saldo, tipoConta, dtAbertura, clienteCpf)" +
                        " VALUES ('" + numConta + "','" + valor + "', '" + tipoConta + "', '" + dtAbertura + "' ,'" + proprietario + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Métodos referentes ao Administrador

    public ResultSet getDadosAdmin(String username, String senha) {
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

    //Outros métodos

    public ResultSet buscaCliente(String numConta) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conexao.createStatement();
            resultSet = statement.executeQuery("select Cliente.nome, Cliente.sobrenome, Conta.* from Cliente inner join Conta on Cliente.cpf = Conta.clienteCpf" +
                                  "    WHERE numConta = '"+numConta+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}

