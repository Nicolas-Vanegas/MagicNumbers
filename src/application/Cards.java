package application;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Scene;

//Esta clase la creamos porque se usará tanto en Cards como en el Controller.
public class Cards
{
	//Crear una lista para traer las cartas de los números para desabilitarlos si no se ha iniciado el juego.
	private List<Node> cards = new ArrayList<Node>();
	//Función para desactivar o activar las cartas dependiendo si se ha iniciado el juego o no.
	public void setDisabledCards(Scene scene, Boolean disable)
	{
		for (int i = 1; i <= 10; i++) {
			//Establecimos las cartas de cada número con el siguiente id = #number(número).
			Node card = scene.lookup(MessageFormat.format("#number{0}", i));
			//añadimos cada carta a la lista de cartas
			cards.add(card);
		}
		System.out.println(cards);
		//Cada una de las cartas será desabilitada o habilitada con el método setDisable
		for (Node card : cards) {
			card.setDisable(disable);
		}
	}
}
