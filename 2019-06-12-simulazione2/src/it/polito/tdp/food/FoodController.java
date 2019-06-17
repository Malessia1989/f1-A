
package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class FoodController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Condiment> boxIngrediente;

    @FXML
    private Button btnDietaEquilibrata;

    @FXML
    private TextArea txtResult;

	private Model model;

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	
    	txtResult.clear();
    	Condiment cInput=boxIngrediente.getValue();
    	if(cInput != null) {
    		String elenco=model.getDieta(cInput);
    		txtResult.setText(elenco);
    	}else {showAlert("devi selezionare un ingrediente!");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
		String calorieInput = txtCalorie.getText();
		if (calorieInput != null && !calorieInput.isEmpty()) {
			if (model.isValid(calorieInput)) {
				String ris = model.creaGrafo(calorieInput);
				txtResult.setText(ris);
				boxIngrediente.getItems().addAll(model.getGrafo().vertexSet());
			} else {
				showAlert("Inserire un numero valido!");
			}
		} else {
			showAlert("Inserire un numero di calorie");
		}

	}

    private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText(message);
		alert.show();
		
	}

	@FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		// TODO Auto-generated method stub
		
	}
}
