package br.com.fpbank.banco.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PrincipalController implements Initializable {

	@FXML
	private VBox vbox;

	private Parent fxml;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		t.setToX(20 * vbox.getLayoutX());
		t.play();
		t.setOnFinished((e) -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));
				vbox.getChildren().removeAll();
				vbox.getChildren().setAll(fxml);
			} catch (IOException ex) {

			}
		});
	}

	@FXML
	private void abrirLogin(ActionEvent event) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		t.setToX(vbox.getLayoutX() * 20);
		t.play();
		t.setOnFinished((e) -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));
				vbox.getChildren().removeAll();
				vbox.getChildren().setAll(fxml);
			} catch (IOException ex) {

			}
		});
	}

	@FXML
	private void abrirCadastro(ActionEvent event) {
		TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
		t.setToX(0);
		t.play();
		t.setOnFinished((e) -> {
			try {
				fxml = FXMLLoader.load(getClass().getResource("/Fxml/Register.fxml"));
				vbox.getChildren().removeAll();
				vbox.getChildren().setAll(fxml);
			} catch (IOException ex) {

			}
		});
	}
}
