package br.com.fpbank.banco.Views;

import br.com.fpbank.banco.Controllers.Admin.AdminController;
import br.com.fpbank.banco.Controllers.Client.ClientController;
import br.com.fpbank.banco.Controllers.Client.DashboardController;
import br.com.fpbank.banco.Controllers.Client.LoginController;
import br.com.fpbank.banco.Models.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import javafx.stage.Stage;


public class ViewFactory {
    public static ViewFactory view;
    private AccountType loginAccountType;

    // Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane statementView;
    private AnchorPane accountsView;

    // Admin Views
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane reportClientView;
    private AnchorPane relatorioMovimentacaoView;

    public ViewFactory() {
        view = this;
        this.loginAccountType = AccountType.CLIENTE;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
        Client Views Section
     */

    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if(dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getStatementView() {
        if(statementView == null){
            try {
                statementView = new FXMLLoader(getClass().getResource("/Fxml/Client/Statement.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return statementView;
    }

    public AnchorPane getAccountsView() {
        if(accountsView == null){
            try {
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Accounts.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    /*
        Admin Views Section
     */

    public AnchorPane getReportClientView() {
        if(reportClientView == null) {
            try {
                reportClientView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reportClientView;
    }

    public AnchorPane getRelatorioMovimentacaoView() {
        if(relatorioMovimentacaoView == null) {
            try{
                System.out.println("Entrou aqui");
                relatorioMovimentacaoView = new FXMLLoader(getClass().getResource("/Fxml/Admin/RelatorioMovimentacao.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return relatorioMovimentacaoView;
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

    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return  adminSelectedMenuItem;
    }

    public void showMainMenu(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/MainMenu.fxml"));
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
