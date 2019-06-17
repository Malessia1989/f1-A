/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;


import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader
	
	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader
	

    @FXML
    private ComboBox<Country> StatiTendina;

    @FXML
    private Button trovaVicini;

	@FXML
	void doCalcolaConfini(ActionEvent event) {

		String annoInput=txtAnno.getText();
		
		if(!annoInput.isEmpty()) {
			if(model.isValid(annoInput)) {
				
				String elenco=model.creaGrafo(annoInput);
				txtResult.setText(elenco);
				
			}else {
				showAlert("Inserire un anno compreso tra il 1806 e il 2016");
			}
		}else {
			showAlert("Inserire un anno!");
		}
	}
	 
	  @FXML
	    void doTrovaVicini(ActionEvent event) {
		 String annoInput=txtAnno.getText();
		 Country c= StatiTendina.getValue();
		 	if (!annoInput.isEmpty()) {
			if (model.isValid(annoInput)) {
				if (c != null) {
					String elenco = model.calcolaTuttiVicini( c);
					txtResult.setText(elenco);
				}
			}
		}
	    }

	private void showAlert(String message) {
		Alert alert =new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
		
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
		assert StatiTendina != null : "fx:id=\"StatiTendina\" was not injected: check your FXML file 'Borders.fxml'.";
	    assert trovaVicini != null : "fx:id=\"trovaVicini\" was not injected: check your FXML file 'Borders.fxml'.";
	    
	}

	public void setModel(Model model) {
		this.model=model;
		StatiTendina.getItems().addAll(model.getCountry());
	}
}
