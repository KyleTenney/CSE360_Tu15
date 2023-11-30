/*
 * Author: Kyle Tenney
 * Title: InputController
 * 
 * Description: This is the controller for InputPage
 * 		The user can start the clock when starting an activity. When the activity is over, the user fills in the fields
 * 			and then stops the clock to submit. All fields are validated so that no bad data can be put i to the file. 
 */

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InputController {

	@FXML
    private Label fileCharErrorDisplay, timeErrorDisplay, weightErrorDisplay;
	@FXML
	private Label pro, lCS, subS, desc, wei, effort;
    @FXML
    private Button submitButton, clockButton, continueButton;
    @FXML
    private TextField contIn;
    @FXML
    private TextField description, effortCatagory, lifeCycleStep, project, subSection, timeEnd, timeStart, weight;

    // Add the data safely in the file
    @FXML
    void store(ActionEvent event) throws IOException {
    	
    	boolean isSafe = true;
    	isSafe = checkIsSafe(isSafe);
    	
    	if (isSafe) {
    		Data_Line data = new Data_Line(timeStart.getText(), timeEnd.getText(), project.getText(), lifeCycleStep.getText(), effortCatagory.getText(), subSection.getText(), description.getText(), Integer.valueOf(weight.getText()));
    	
    		data.inputInFile();
    	}
    	
	}
    
    // This function brings the user back to the home page
    @FXML
    void goToHome(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("HomePage.fxml");
    }
    
    // Make sure that all input is good and safe
    private boolean checkIsSafe(boolean isSafe) {
    	weightErrorDisplay.setText("");
    	fileCharErrorDisplay.setText("");
    	try {
			int i = Integer.valueOf(weight.getText());
			if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 ) {
			}
			else {
				isSafe = false;
				weight.clear();
				weightErrorDisplay.setText("Make sure that your weight 0, 1, 2, 3 or 4.");
			}
		} catch (Exception w) {
			isSafe = false;
			weight.clear();
			weightErrorDisplay.setText("Make sure that your weight 0, 1, 2, 3 or 4.");
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
    
    @FXML
    void updatePage(ActionEvent event) throws FileNotFoundException {
    	/* make sure if there is already a data with a start time
    	 * if there is then have the button have a stopTime and have the submit button ready
    	 */
    	if(hasStartedLine()) {
    		//button to "Stop Clock"
    		clockButton.setText("Stop Clock to submit");
    	}
    	else {
    		//button to "Start Clock"
    		clockButton.setText("Start Clock");
    		description.clear();
        	effortCatagory.clear();
        	lifeCycleStep.clear();
        	project.clear();
        	subSection.clear();
    		weight.clear();
    	}
    	//Make everything else visible
    	continueButton.setVisible(false);
    	clockButton.setVisible(true);
    	description.setVisible(true);
    	effortCatagory.setVisible(true);
    	lifeCycleStep.setVisible(true);
    	project.setVisible(true);
    	subSection.setVisible(true);
    	weight.setVisible(true);
    	pro.setVisible(true);
    	lCS.setVisible(true);
    	subS.setVisible(true);
    	desc.setVisible(true);
    	wei.setVisible(true);
    	effort.setVisible(true);
    }
    
    @FXML
    void triggerClock(ActionEvent event) throws IOException {
    	/* If there is not a data with a start time then make a new data with only the start time
    	 * Else edit that line with the current data
    	 */
    	
    	if(hasStartedLine()) {
    		//edit line to include the new items
    		
    		if(checkIsSafe(true)) {
    			//get that lines start time and delete that line
        		String startTime = "";
        		String endTime = getGoodTime();
        		File inputFile = new File("Team_Tu15_Input_Testing.txt");
        		File tempFile = new File("myTempFile.txt");
            	FileWriter fileWriter = new FileWriter(tempFile, true);
        		Scanner scan = new Scanner(inputFile);
        		String line = "";
        		while(scan.hasNextLine()) {
        			line = scan.nextLine();
        			if(line.contains("~~~~~~~9~")) {   // "~~~~~~~9~" will always be part of the line with only a start time
        				startTime = line.substring(0, 19);
        			}
        			else {
        				fileWriter.write(line + "\n"); // If it was not the special one then write it into the new file.
        			}
        		}
        		fileWriter.flush(); // Make sure to write into the file before closing
        		fileWriter.close();
        		scan.close();
        		inputFile.delete();  //Delete the old file so that we can rename the new one to take its place
            	tempFile.renameTo(new File("Team_Tu15_Input_Testing.txt"));
        		
        		Data_Line data = new Data_Line(startTime, endTime, project.getText(), lifeCycleStep.getText(), effortCatagory.getText(), subSection.getText(), description.getText(), Integer.parseInt(weight.getText()));
        		data.inputInFile();
        		updatePage(event);
    		}
    		
    	}
    	else { //Start the timer
    		String time = getGoodTime();
    		LocalDateTime myObj = LocalDateTime.parse(time);
    		Data_Line data = new Data_Line(myObj);
    		data.inputInFile();
    		updatePage(event);
    	}
    	
    }
    
    private boolean hasStartedLine() throws FileNotFoundException {
    	boolean hasStarted = false;
    	File myFile = new File("Team_Tu15_Input_Testing.txt");
    	try {
    		myFile.createNewFile();
    	} catch (IOException e) {
    	}
    	
    	Scanner scan = new Scanner(myFile);
    	
    	// Check all of the lines to see if any of them have the special string
		while(scan.hasNextLine()) {
			Data_Line dataFromFile = new Data_Line(scan.nextLine());
			if(dataFromFile.getFullLine().contains("~~~~~~~9~")) {
				hasStarted = true;
			}
		}
		scan.close();
    	return hasStarted;
    }
    
    // When the clock is triggered it is possible for it to have a different length than another time. 
    // This makes sire that all of the times are of the same length so that all computations can be done nicely.
    private String getGoodTime() {
    	String str = "";
		LocalDateTime myObj = LocalDateTime.now();
		boolean timeFound = false;
		do {
    		myObj = LocalDateTime.now();
    		str = myObj.toString().substring(0, myObj.toString().length()-7);
    		if(str.length() == 19) {
    			timeFound = true;
    		}
		}while(!timeFound);
    	return str;
    }
    
}


