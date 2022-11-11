package br.com.fpbank.banco;

import br.com.fpbank.banco.Models.Model;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Model.getInstance().getViewFactory().showMainMenu(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
