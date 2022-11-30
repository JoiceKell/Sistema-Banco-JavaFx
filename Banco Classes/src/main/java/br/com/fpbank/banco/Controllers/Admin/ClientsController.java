package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Entities.Cliente;
import br.com.fpbank.banco.Models.Model;
import br.com.fpbank.banco.Views.ClienteCorrenteCellFactory;
import br.com.fpbank.banco.Views.ClientePoupancaCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    public ListView<Cliente> clientes_listviewContaPoupanca;
    public ListView<Cliente> clientes_listviewContaCorrente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        clientes_listviewContaPoupanca.setItems(Model.getInstance().getClientesContaPoupanca());
        clientes_listviewContaPoupanca.setCellFactory(e -> new ClientePoupancaCellFactory());

        clientes_listviewContaCorrente.setItems(Model.getInstance().getClientesContaCorrente());
        clientes_listviewContaCorrente.setCellFactory(e -> new ClienteCorrenteCellFactory());
    }

    private void initData() {
        if (Model.getInstance().getClientesContaPoupanca().isEmpty())
            Model.getInstance().setClientesPoupanca();

        if (Model.getInstance().getClientesContaCorrente().isEmpty())
            Model.getInstance().setClientesCorrente();
    }
}
