/*
 * Author: Kyle Tenney
 * Title: HomeController
 * Last update: 11/4/2023   5:12 PM
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
    	m.changeScene("Input_page.fxml");
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
}



