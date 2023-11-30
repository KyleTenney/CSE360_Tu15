/*
 * Author: Kyle Tenney
 * Title: HomeController
 * 
 * Description: This is the controller for HomePage
 * 		The user can choose to Input data, Edit/Delete data, Calculate information for PlanningPoker, or logout.
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
    void goToPlanningPoker(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("PlanningPokerPage.fxml");
    }

    @FXML
    void goToLogIn(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("LoginPage.fxml");
    }
    
    @FXML
    void goToEditDelete(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("EditDeletePage.fxml");
    }
}



