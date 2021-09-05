package br.com.phtc.utils;

import java.util.Objects;

import javafx.scene.control.TextField;

public class FXTextFieldUtils {

	public static Long toLong(TextField txt) {
		if (Objects.nonNull(txt.getText()) && !txt.getText().isEmpty()) {
			try {
				return Long.parseLong(txt.getText());
			} catch (NumberFormatException e) {}
		}
		return null;
	}
}
