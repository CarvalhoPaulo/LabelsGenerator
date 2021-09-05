package br.com.phtc.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LabelsGeneratorApplication extends Application {

	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		LabelsGeneratorApplication.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(this.getClass().getResource("LabelsGeneratorScreen.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        primaryStage.setTitle("Gerador de etiquetas");
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
