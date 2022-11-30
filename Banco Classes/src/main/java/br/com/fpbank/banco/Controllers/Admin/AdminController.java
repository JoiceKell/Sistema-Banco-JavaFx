package br.com.fpbank.banco.Controllers.Admin;

import br.com.fpbank.banco.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener(((observableValue, oldVal, newVal) -> {
            switch (newVal) {
                case RELATORIO_MOVIMENTACAO -> admin_parent.setCenter(Model.getInstance().getViewFactory().getRelatorioMovimentacaoView());
                case RELATORIO_CLIENTE -> admin_parent.setCenter(Model.getInstance().getViewFactory().getReportClientView());
            }
        }));
    }
}
