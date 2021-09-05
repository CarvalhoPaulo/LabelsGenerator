package br.com.phtc.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FXAlertUtils {
	
	public static void alertError(String title, String headerText, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void alertError(String title, String message) {
		alertError(title, null, message);
	}
	
	public static void alertError(String message) {
		alertError("Erro", message);
	}
	
}
