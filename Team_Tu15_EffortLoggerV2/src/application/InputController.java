/*
 * Author: Kyle Tenney
 * Title: Input
 * Last update: 11/5/2023   4:26 PM
 * 
 * Description: This is the controller to be in charge of taking the information
 * 		from the Input_page and putting that in the file.
 *  
 */

package application;

import java.io.IOException;
import java.time.LocalDateTime;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InputController {

	@FXML
    private Label fileCharErrorDisplay;
    @FXML
    private Button button;
    @FXML
    private TextField contIn;
    @FXML
    private Label timeErrorDisplay;
    @FXML
    private Label weightErrorDisplay;
    @FXML
    private TextField description;
    @FXML
    private TextField effortCatagory;
    @FXML
    private TextField lifeCycleStep;
    @FXML
    private TextField project;
    @FXML
    private TextField subSection;
    @FXML
    private TextField timeEnd;
    @FXML
    private TextField timeStart;
    @FXML
    private TextField weight;

    @FXML
    void store(ActionEvent event) throws IOException {
    	
    	boolean isSafe = true;
    	isSafe = checkIsSafe(isSafe);
    	
    	Main m = new Main();
    	if (isSafe) {
    		Data_Line data = new Data_Line(timeStart.getText(), timeEnd.getText(), project.getText(), lifeCycleStep.getText(), effortCatagory.getText(), subSection.getText(), description.getText(), Integer.valueOf(weight.getText()));
    	
    		data.inputInFile();
    	}
    	if(isSafe) {
    		if(contIn.getText().equalsIgnoreCase("y")) {
    			//Reset the page
    			clearScreen(m);
    		}
    		else {
    			//Go back to home
    			m.changeScene("homePlaceHolder.fxml");
    		}
    	}
    	
	}
    
    // This function brings the user back to the home page
    @FXML
    void goToHome(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("HomePlaceHolder.fxml");
    }

    // This function resets the screen to the current one so that it returns back new
    private void clearScreen(Main m) throws IOException {
    	m.changeScene("Input_page.fxml");
    }
    
    // Make sure that all input is good and safe
    private boolean checkIsSafe(boolean isSafe) {
    	try {
			Integer.valueOf(weight.getText());
		} catch (Exception w) {
			isSafe = false;
			weight.clear();
			weightErrorDisplay.setText("Make sure that your weight is an integer.");
		}
		try {
			LocalDateTime.parse(timeStart.getText());
		} catch (Exception w) {
			isSafe = false;
			timeStart.clear();
			timeErrorDisplay.setText("Make sure that the times are in the correct format.");
		}
		try {
			LocalDateTime.parse(timeEnd.getText());
		} catch (Exception w) {
			isSafe = false;
			timeEnd.clear();
			timeErrorDisplay.setText("Make sure that the times are in the correct format.");
		}
		
		if(description.getText().contains("~")) {
			description.clear();
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(effortCatagory.getText().contains("~")) {
			effortCatagory.clear();
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(lifeCycleStep.getText().contains("~")) {
			lifeCycleStep.clear();
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(project.getText().contains("~")) {
			project.clear();
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(subSection.getText().contains("~")) {
			subSection.clear();
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		
		return isSafe;
    }
}



