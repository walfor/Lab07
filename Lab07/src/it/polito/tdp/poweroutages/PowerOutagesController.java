package it.polito.tdp.poweroutages;



import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	Model model = new Model ();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="menuNerc"
    private ComboBox<Nerc> menuNerc; // Value injected by FXMLLoader


    @FXML // fx:id="textYears"
    private TextField textYears; // Value injected by FXMLLoader

    @FXML // fx:id="textHours"
    private TextField textHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnWCA"
    private Button btnWCA; // Value injected by FXMLLoader

    @FXML // fx:id="textArea"
    private TextArea textArea; // Value injected by FXMLLoader

    @FXML
    void handleSelectionNerc(ActionEvent event) {
    }

    @FXML
    void handleWCA(ActionEvent event) {
    	Nerc nerc = (menuNerc.getSelectionModel().getSelectedItem());
    	
    	if(nerc==null) {
    		textArea.setText("Errore nerc non ammissibile.");
    		return;
    	}else {
    		if(textHours.getText().equals("")) {
    			textArea.setText("Errore ora non selezionata.");
    			return;
    		}
    		
    		if(textYears.getText().equals("")) {
    			textArea.setText("Errore anni non selezionati.");
    			return;
    		}
    		
    		try {
    			model.start(nerc,Integer.parseInt(textYears.getText()),Integer.parseInt( textHours.getText()));
    			
    			
    			
    			textArea.appendText("Tot people effectd : " + model.contaPersone(model.getSoluzioneOttimale())+"\n");
    			textArea.appendText("Tot hours of outage: " + model.contaOre(model.getSoluzioneOttimale())+"\n");
    			textArea.appendText(model.ListToString((model.getSoluzioneOttimale())));
    			
    		} catch (NumberFormatException nfe) {
				System.err.println("Errore formato per anni o ore. Scrivere numeri.");
			}
    		
    		
    		
    		
    	}


    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	  assert menuNerc != null : "fx:id=\"menuNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert textYears != null : "fx:id=\"textYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert textHours != null : "fx:id=\"textHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnWCA != null : "fx:id=\"btnWCA\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        
        menuNerc.getItems().addAll(model.getListaNerc());
        
        
    }
}
