
// Classe RelatorioClientesController
// Apresenta os dados de todos os correntistas dividido em duas ListView, uma para cliente com conta corrente e a outra para cientes com conta poupança.

package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.ClienteCorrenteCellFactory;
import br.com.fpbank.banco.Views.ClientePoupancaCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioClientesController implements Initializable {

    public ListView<Cliente> listview_clientesContaPoupanca;
    public ListView<Cliente> listview_clientesContaCorrente;

    // Cria as Cells com os dados de cada cliente setado no método inicializaDados()
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaDados();
        listview_clientesContaPoupanca.setItems(Model.getInstance().getClientesContaPoupanca());
        listview_clientesContaPoupanca.setCellFactory(e -> new ClientePoupancaCellFactory());

        listview_clientesContaCorrente.setItems(Model.getInstance().getClientesContaCorrente());
        listview_clientesContaCorrente.setCellFactory(e -> new ClienteCorrenteCellFactory());
    }

    // Método que verifica se está vazio e define os dados de cada cliente.
    private void inicializaDados() {
        if (Model.getInstance().getClientesContaPoupanca().isEmpty())
            Model.getInstance().setClientesPoupanca();

        if (Model.getInstance().getClientesContaCorrente().isEmpty())
            Model.getInstance().setClientesCorrente();
    }
}
