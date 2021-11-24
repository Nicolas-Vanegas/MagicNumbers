package application;
	
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	
	public void start(Stage primaryStage) {
		try {
			
			//Establecer la Escena principal.
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("The Magic Numbers");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Asociar el archivo css a la aplicación.
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//creamos una instancia de Cards para establecer las cartas como desactivadas.
			Cards cards = new Cards();
			cards.setDisabledCards(scene, true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
