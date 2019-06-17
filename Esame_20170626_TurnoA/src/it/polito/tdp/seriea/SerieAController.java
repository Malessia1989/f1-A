/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class SerieAController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxSquadra"
	private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

	@FXML // fx:id="boxStagione"
	private ChoiceBox<?> boxStagione; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaConnessioniSquadra"
	private Button btnCalcolaConnessioniSquadra; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimulaTifosi"
	private Button btnSimulaTifosi; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaSquadre"
	private Button btnAnalizzaSquadre; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

	@FXML
	void doAnalizzaSquadre(ActionEvent event) {
		txtResult.clear();
		// deve creare il grafo pesato, non orientato
		model.popolaGrafo(); 
		// popola il menu a tendina con le squadre del grafo(vertexSet())
		boxSquadra.getItems().addAll(model.getGrafo().vertexSet());
		txtResult.setText("Grafo creato, selezionare la squadra");
	}

	@FXML
	void doCalcolaConnessioniSquadra(ActionEvent event) {
		
		// per la squadra selezionata devo stampare :
		// 1) l'elenco delle squadre con cui ha giocato
		// 2) il numero di partite giocate in ordine
		
		Team squadraSelezionata = boxSquadra.getValue();
		
		if (squadraSelezionata != null) {
			
			// elenco squadre con cui ha giocato la squadra selezionata
			String elenco = model.calcolaConnessioni(squadraSelezionata);
			txtResult.setText(elenco);
		} else {
			showAlert("nessuna squadra selezionata!");
		}
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();

	}

	@FXML
	void doSimulaTifosi(ActionEvent message) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
		

	}

	public void setModel(Model model) {
		this.model = model;
		
		
	}
}
