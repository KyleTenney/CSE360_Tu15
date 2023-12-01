/*
* Author: Kyle Tenney
* Title: EditDeleteController
*
* Description: This is the controller for the EditDeletePage. This allows the user to edit or delete data.
* 	First filter to narrow down what you need to do and then change the values or delete them.
* 	This is still safe in the file system because it first adds the new data and then deletes the old one. 
*/

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EditDeleteController {

	private LinkedList<Data_Line> lines = new LinkedList<Data_Line>(); // All of the lines in the file
	private Data_Line currentData = new Data_Line(""); // Keep track of the data currently on the page
	
	@FXML
 	private Button searchButton, nextButton, deleteButton, editButton, submitButton;
	@FXML
    private TextField displayProject, displayLCS, displayDesc, displayEffort, displaySubS, displayWeight, displayStartTime, displayEndTime;
    @FXML
    private Button onDeleteNoButton, onDeleteYesButton, homeButton;
    @FXML
    private Label onDeletePrompt, timeErrorDisplay, fileCharErrorDisplay, weightErrorDisplay;
    @FXML
    private  TextField proj, subS, wei, desc, effort, lCS, start, end;
    
    // Collect all of the lines from the file into the LinkedList
    @FXML
    public void findData(ActionEvent event) throws FileNotFoundException {
    	File myFile = new File("Team_Tu15_Data.txt");
    	try {
    			myFile.createNewFile();
    	} catch (IOException e) {
    		 System.out.println("An error occurred while making the file from planning poker.");
    	}
    	
    	Scanner scan = new Scanner(myFile);
    	
    	//Get all of the specific lines of data from the file
		while(scan.hasNextLine()) {
			Data_Line dataFromFile = new Data_Line(scan.nextLine());
			if(dataFromFile.getProject().contains(proj.getText()) || proj.getText().equals("")) {
				if(dataFromFile.getLifeCycleStep().contains(lCS.getText()) || lCS.getText().equals("")) {
					if(dataFromFile.getEffortCatagory().contains(effort.getText()) || effort.getText().equals("")) {
						if(dataFromFile.getSubSection().contains(subS.getText()) || subS.getText().equals("")) {
							if(dataFromFile.getDescription().contains(desc.getText()) || desc.getText().equals("")) {
								if((Integer.toString(dataFromFile.getWeight()).equals(wei.getText()) || wei.getText().equals("")) && !(Integer.toString(dataFromFile.getWeight()).equals("9"))) {
									if(dataFromFile.getTimeStart().contains(start.getText()) || start.getText().equals("")) {
										if(dataFromFile.getTimeEnd().contains(end.getText()) || end.getText().equals("")) {
											lines.addFirst(dataFromFile);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		scan.close();
		DisplayLine(lines); //Update the page
    }	
    
    //Update the page so that the user can see the data and what to do
    private void DisplayLine(LinkedList<Data_Line> lines) {
    	if(lines.peekFirst() == null){
    		// IF there is no more lines then reset the page like it was in the begining
    		displayStartTime.clear();
    		displayEndTime.clear();
	    	displayProject.clear();
	    	displayLCS.clear();
	    	displayEffort.clear();
	    	displayDesc.clear();
	    	displaySubS.clear();
	    	displayWeight.clear();
    		searchButton.setVisible(true);
    		deleteButton.setVisible(false);
    		editButton.setVisible(false);
    		nextButton.setVisible(false);
    		
    	}
    	else {
    		displayStartTime.setText(lines.getFirst().getTimeStart());
    		displayEndTime.setText(lines.getFirst().getTimeEnd());
	    	displayProject.setText(lines.getFirst().getProject());
	    	displayLCS.setText(lines.getFirst().getLifeCycleStep());
	    	displayEffort.setText(lines.getFirst().getEffortCatagory());
	    	displayDesc.setText(lines.getFirst().getDescription());
	    	displaySubS.setText(lines.getFirst().getSubSection());
	    	displayWeight.setText(Integer.toString(lines.getFirst().getWeight()));
	    	currentData =  lines.getFirst();
	    	lines.removeFirst(); // Remove the top line so that the new first is the next data line
	    	
    		searchButton.setVisible(false);
    		deleteButton.setVisible(true);
    		editButton.setVisible(true);
    		nextButton.setVisible(true);
    	}
    }
    
    @FXML
    void goToHome(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("HomePage.fxml");
    }

    @FXML
    void nextData(ActionEvent event) {
    	DisplayLine(lines);
    }

    // This is to make sure that the user wants to delete an item 
    @FXML
    void tryDelete(ActionEvent event) {
    	onDeleteNoButton.setVisible(true);
    	onDeletePrompt.setVisible(true);
    	onDeleteYesButton.setVisible(true);
    	nextButton.setVisible(false);
    	editButton.setVisible(false);
    }

    @FXML //The user did not want to delete
    void cancelDelete(ActionEvent event) {
    	onDeleteNoButton.setVisible(false);
    	onDeletePrompt.setVisible(false);
    	onDeleteYesButton.setVisible(false);
    	nextButton.setVisible(true);
    	editButton.setVisible(true);
    }
    
    @FXML //Delete the data that was on display
    void trueDelete(ActionEvent event) throws IOException {
    
    	deleteCurrent();
    	
    	// update buttons
    	cancelDelete(event);
    	//update fields
    	displayStartTime.clear();
    	displayEndTime.clear();
    	displayProject.clear();
    	displayProject.clear();
    	displayLCS.clear();
    	displayEffort.clear();
    	displayDesc.clear();
    	displaySubS.clear();
    	displayWeight.clear();
    	
    	nextData(event); 
    }
    
    @FXML
    void enableEdit(ActionEvent event) {
    	submitButton.setVisible(true);
    	deleteButton.setVisible(false);
    	nextButton.setVisible(false);
    	editButton.setVisible(false);
    	homeButton.setVisible(false);
    	
    	displayStartTime.setEditable(true);
    	displayEndTime.setEditable(true);
    	displayProject.setEditable(true);
    	displayLCS.setEditable(true);
    	displayDesc.setEditable(true);
    	displayEffort.setEditable(true);
    	displaySubS.setEditable(true);
    	displayWeight.setEditable(true);
    }
    
    // Write all data lines except the target one in a new file and then rearrange the file names so that it was how it was before
    private void deleteCurrent() throws IOException {
    	String lineToRemove = currentData.getFullLine();
    	
    	File inputFile = new File("Team_Tu15_Data.txt");
    	File tempFile = new File("myTempFile.txt");
    	
    	FileReader fileReader = new FileReader(inputFile);
    	FileWriter fileWriter = new FileWriter(tempFile);
    	BufferedReader reader = new BufferedReader(fileReader);
    	BufferedWriter writer = new BufferedWriter(fileWriter);

    	String currentLine;

    	while((currentLine = reader.readLine()) != null) {
    	    // trim newline when comparing with lineToRemove
    	    String trimmedLine = currentLine.trim();
    	    if(trimmedLine.equals(lineToRemove)) continue;
    	    writer.write(currentLine + System.getProperty("line.separator"));
    	}
    	
    	reader.close();
    	writer.flush();  //Make sure it writes before we close
    	writer.close();
    	
    	inputFile.delete();  //Delete the old file so that we can rename the new one to take its place
    	tempFile.renameTo(new File("Team_Tu15_Data.txt"));
    	
    	// Clear the list of data that was being tracked so that we can now start over
    	lines = new LinkedList<Data_Line>();
    	currentData = new Data_Line("");
    	nextData(null);
    }
    
    @FXML  // Action called from submitButton
    void saveData(ActionEvent event) {
    	if(checkIsSafe()) {
    		// Add the data that was inputed from the user
        	Data_Line data = new Data_Line(displayStartTime.getText(), displayEndTime.getText(), displayProject.getText(), displayLCS.getText(), displayEffort.getText(), displaySubS.getText(), displayDesc.getText(), Integer.parseInt(displayWeight.getText()));
        	if(data.equals(currentData)) {
        		// If the data is the same don't add it
        		nextData(event);
        	}
        	else {
        		// If different, add it and delete the previous version
        		data.inputInFile();
        		try {
        			deleteCurrent(); // Delete what was on the page before the user edited it
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        	
        	displayStartTime.setEditable(false);
        	displayEndTime.setEditable(false);
        	displayProject.setEditable(false);
        	displayLCS.setEditable(false);
        	displayDesc.setEditable(false);
        	displayEffort.setEditable(false);
        	displaySubS.setEditable(false);
        	displayWeight.setEditable(false);
        	
        	submitButton.setVisible(false);
        	homeButton.setVisible(true);
        	
        	timeErrorDisplay.setText(null);
        	fileCharErrorDisplay.setText(null);
        	weightErrorDisplay.setText(null);
    	}
    }
    
    // Make sure that the input will be safe and if not warn the user
    private boolean checkIsSafe() {
    	timeErrorDisplay.setText(null);
    	fileCharErrorDisplay.setText(null);
    	weightErrorDisplay.setText(null);
    	
    	boolean isSafe = true;
    	int sumTime = 0;
    	try {
    		sumTime = (int) (LocalDateTime.parse(displayStartTime.getText()).until(LocalDateTime.parse(displayEndTime.getText()), ChronoUnit.SECONDS));
    	}catch(Exception e){
    		
    	}
    	if(sumTime > 0) { // Make sure that the end time is after the start time
    	}
    	else {
    		isSafe = false;
    		timeErrorDisplay.setText("Make sure that the end time is after the start time.");
    	}
    	try {
			int i = Integer.valueOf(displayWeight.getText());
			if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 ) {
			}
			else {
				isSafe = false;
				weightErrorDisplay.setText("Make sure that your weight 0, 1, 2, 3 or 4.");
			}
		} catch (Exception w) {
			isSafe = false;
			weightErrorDisplay.setText("Make sure that your weight 0, 1, 2, 3 or 4.");
		}
		try {
			LocalDateTime.parse(displayStartTime.getText());
		} catch (Exception w) {
			isSafe = false;
			timeErrorDisplay.setText("Make sure that the times are in the correct format.");
		}
		try {
			LocalDateTime.parse(displayEndTime.getText());
		} catch (Exception w) {
			isSafe = false;
			timeErrorDisplay.setText("Make sure that the times are in the correct format.");
		}
		
		if(displayDesc.getText().contains("~")) {
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(displayEffort.getText().contains("~")) {
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(displayLCS.getText().contains("~")) {
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(displayProject.getText().contains("~")) {
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		if(displaySubS.getText().contains("~")) {
			fileCharErrorDisplay.setText("Make sure not to use ~ in anything.");
			isSafe = false;
		}
		
		return isSafe;
    }
}
