package application;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;

public class MainSceneController {
	//propiedad para establecer el número que se debe clickear.
	private int number;
	
	private List<Node> cards = new ArrayList<Node>();
	// Event Listener on Button.onAction
	@FXML
	public Boolean numberClicked(ActionEvent event) {
		//Establecer source para poder usar sus métodos.
		final Node source = (Node) event.getSource();
		
		//Inicialiazar la clase cards para desabilitar las cartas mientras suena el próximo número.
		Cards cards = new Cards();
		cards.setDisabledCards(source.getScene(), true);
		
		//Traer el label de los botones que corresponden con el número de cada uno.
		int number = Integer.parseInt(((Button)event.getSource()).getText());
		
		if(number == this.number) {
			//Si el número clickeado es igual al número que se debe clickear sonará el audio de que la respuesta
			//es correcta, se pintará de verde y el número a seleccionar será el número clickeado más 1.
			audio(0, true);
			source.getStyleClass().add("correct-answer");
			
			this.number = number + 1;
			
			//Se establece un delay para reproducir el audio del siguiente número y se remueve la clase que daba el color verde a la carta.
			CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
				if (this.number <= 10) {
					audio(this.number, null);					
				}
				
				//habilitar cartas después de 2 segundos
				cards.setDisabledCards(source.getScene(), false);
				removeClass(source);
			});
			if (number == 10) {
				CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS).execute(() -> {
					//Mostrar mensaje de fin del juego
					JOptionPane.showMessageDialog(null, "Winner, Good Game");
					cards.setDisabledCards(source.getScene(), true);
				});
				return true;
			}
			return false;
		} else {
			//Si el número clickeado es diferente al número del audio se añadirá una clase para pintar
			//la carta de rojo y sonará un audio que representa una respuesta incorrecta.
			source.getStyleClass().add("wrong-answer");
			audio(0, false);
			//Se establece un delay para remover la clase que daba el color rojo a la carta.
			CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
				audio(this.number, null);
				//deshabilitar cartas después de 2 segundos
				cards.setDisabledCards(source.getScene(), false);
				removeClass(source);
			});
			return false;
		}
	}
	
	//Evento para comenzar el juego.
	public Boolean newGame(ActionEvent event) {
		//Establecer source para poder usar sus métodos.
		final Node source = (Node) event.getSource();
		
		//Inicialiazar la clase cards para poder habilitar las cartas.
		Cards cards = new Cards();
		cards.setDisabledCards(source.getScene(), false);
		
		//Establecer el primer número en el que el niño debe dar clic.
		this.number = 1;
		//Audio del número 1.
		audio(this.number, null);
		
		return true;
	}
	
	//Método para establecer la url del audio a reproducir.
	public Boolean audio(int number, Boolean correct) {
		System.out.println(number);
		String url;
		if (number != 0)
		{
			url = MessageFormat.format("resources/audio/{0}.mp3", number);			
		}
		else if (correct) {
			url = "resources/audio/correct.mp3";
		}
		else {
			url = "resources/audio/wrong.m4a";
		}
		playAudio(url);
		
		return true;
	}
	
	//Método para reproducir los audios.
	public Boolean playAudio(String url) {
		Media player = new Media(Paths.get(url).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(player);
		mediaPlayer.play();
		
		return true;
	}
	
	//Método para quitar las clases después de 2 segundos de pintadas.
	public void removeClass(Node source) {
		var cardNumbers = source.getParent().getChildrenUnmodifiable();
		for (Node cardNumber : cardNumbers) {
		  cardNumber.getStyleClass().remove("wrong-answer");
		  cardNumber.getStyleClass().remove("correct-answer");
		}
	}
}