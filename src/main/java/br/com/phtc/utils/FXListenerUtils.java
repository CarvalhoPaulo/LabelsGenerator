package br.com.phtc.utils;

import javafx.scene.control.TextField;

public class FXListenerUtils {
	public static void addTextLimiter(final TextField txt, final int maxLength) {
		txt.textProperty().addListener((ov, oldValue, newValue) -> {
			if (txt.getText().length() > maxLength) {
                String s = txt.getText().substring(0, maxLength);
                txt.setText(s);
            }
		});
	}

	public static void addOnlyNumber(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!newValue.isEmpty() && !newValue.matches("\\d*")) {
				txt.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}
}
