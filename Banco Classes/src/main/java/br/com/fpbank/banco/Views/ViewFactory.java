package br.com.fpbank.banco.Views;

import br.com.fpbank.banco.Controllers.Admin.AdminController;
import br.com.fpbank.banco.Controllers.Client.ClienteController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import javafx.stage.Stage;


public class ViewFactory {
    public static ViewFactory view;
    private TipoConta loginTipoConta;

    //Views do Cliente
    private final ObjectProperty<OpcoesMenuCliente> clientSelectedMenuItem;
    private AnchorPane menuPrincipalView;
    private AnchorPane historicoView;
    private AnchorPane contaView;

    //Views do Administrador
    private final ObjectProperty<OpcoesMenuAdmin> adminSelectedMenuItem;
    private AnchorPane relatorioClientesView;
    private AnchorPane relatorioMovimentacoesView;

    public ViewFactory() {
        view = this;
        this.loginTipoConta = TipoConta.CLIENTE;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public TipoConta getLoginTipoConta() {
        return loginTipoConta;
    }

    public void setLoginTipoConta(TipoConta loginTipoConta) {
        this.loginTipoConta = loginTipoConta;
    }

    //Métodos referentes ao Cliente

    public ObjectProperty<OpcoesMenuCliente> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getMenuPrincipalView() {
        if(menuPrincipalView == null) {
            try {
                menuPrincipalView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menuPrincipalView;
    }

    public AnchorPane getHistoricoView() {
        if(historicoView == null){
            try {
                historicoView = new FXMLLoader(getClass().getResource("/Fxml/Client/Historico.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return historicoView;
    }

    public AnchorPane getContaView() {
        if(contaView == null){
            try {
                contaView = new FXMLLoader(getClass().getResource("/Fxml/Client/ManterContaAtuDes.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return contaView;
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Cliente.fxml"));
        ClienteController clienteController = new ClienteController();
        loader.setController(clienteController);
        createStage(loader);
    }

    //Métodos referentes ao Administrador

    public AnchorPane getRelatorioClientesView() {
        if(relatorioClientesView == null) {
            try {
                relatorioClientesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clientes.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return relatorioClientesView;
    }

    public AnchorPane getRelatorioMovimentacoesView() {
        if(relatorioMovimentacoesView == null) {
            try{
                relatorioMovimentacoesView = new FXMLLoader(getClass().getResource("/Fxml/Admin/RelatorioMovimentacao.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return relatorioMovimentacoesView;
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }

    public void showMessageWindow(String nome, String mensagemTexto) {
        StackPane pane = new StackPane();
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        Label remetente = new Label(nome);
        Label mensagem = new Label(mensagemTexto);
        vBox.getChildren().addAll(remetente, mensagem);
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane, 300, 100);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Logo2.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Mensagem");
        stage.setScene(scene);
        stage.show();

    }

    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Logo2.png"))));
        stage.setTitle("Financial Pyramid Bank");
        stage.show();
    }

    public ObjectProperty<OpcoesMenuAdmin> getAdminSelectedMenuItem() {
        return  adminSelectedMenuItem;
    }

    public void showMainMenu(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Principal.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Logo2.png"))));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
