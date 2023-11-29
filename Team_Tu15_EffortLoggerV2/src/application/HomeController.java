/*
 * Author: Kyle Tenney
 * Title: HomeController
 * Last update: 11/29/2023   10:30 AM
 * 
 * Description: This is the controller to control what the buttons do. 
 * 		For now it can choose to input data, calculate averages, or go back to log in page
 *  
 */

package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    void goToInputData(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("InputPage.fxml");
    }

    @FXML
    void goToPlanningPokerThing(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("PlanningPokerPage.fxml");
    }

    @FXML
    void goToLogIn(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("LoginScreen.fxml");
    }
    
    @FXML
    void goToEditData(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("EditDataPage.fxml");
    }
}



