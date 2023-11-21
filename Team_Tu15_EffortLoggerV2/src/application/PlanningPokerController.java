/*
* Author: Kyle Tenney
* Title: PlanningPokerCrontroller
* Last update: 11/20/2023   9:00 PM
*
* Description: This is the controller for the PlanningPokerPage. It collects what was inputed from the user
* 	to narrow down what activitys to look at and then calculates the average weight and time of all of those.
* 	It also displays the planning poker card that should be used.
*/
package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PlanningPokerController {
@FXML
private Label amountOfData;
@FXML
private Label averageTimeDisplay;
@FXML
private Label averageWeightDisplay;
@FXML
private Label cardDisplay;
@FXML
private TextField effortCatagory;
@FXML
private TextField lifeCycleStep;
@FXML
private TextField project;
@FXML
private TextField subSection;
@FXML
void calculate(ActionEvent event) throws FileNotFoundException {
	String proj = project.getText();
	String lCS = lifeCycleStep.getText();
	String effort = effortCatagory.getText();
	String subS = subSection.getText();
	
	double[] information = getAverages(proj, lCS, effort, subS);
	
	amountOfData.setText("There was " + information[0] + " data inputs found");
	averageWeightDisplay.setText("The average weight was: " + information[1]);
	averageTimeDisplay.setText("The average time was: " + information[2] + " years, " + information[3] + " months, " + information[4] + " days, " + information[5] + " hours, " + information[6] + " minutes, and "+ information[7] + " seconds");
	
	cardDisplay.setText("Recomened importance is " + Math.round(information[1]));
}
@FXML
void goToHome(ActionEvent event) throws IOException {
	Main m = new Main();
	m.changeScene("HomePlaceHolder.fxml");
}
private static double[] getAverages(String proj, String lCS, String effort, String subS) throws FileNotFoundException {
	double amount = 0;
	double seconds = 0;
	double minutes = 0;
	double hours = 0;
	double days = 0;
	double months = 0;
	double years = 0;
	double averageWeight = 0;
	double[] information = new double[8];
	double sumTime = 0;
	int sumWeight = 0;
	
	File myFile = new File("Team_Tu15_Input_Testing.txt");
	try {
			myFile.createNewFile();
	} catch (IOException e) {
		 System.out.println("An error occurred while making the file from planning poker.");
	 }
	
	Scanner scan = new Scanner(myFile);
		
	// Narrow down the data from fields chosen from the user
	while(scan.hasNextLine()) {
		Data_Line dataFromFile = new Data_Line(scan.nextLine());
		if(dataFromFile.getProject().equals(proj) || proj.equals("")) {
			if(dataFromFile.getLifeCycleStep().equals(lCS) || lCS.equals("")) {
				if(dataFromFile.getEffortCatagory().equals(effort) || effort.equals("")) {
					if(dataFromFile.getSubSection().equals(subS) || subS.equals("")) {
						if(dataFromFile.getWeight() != 9) {
							amount++; // Update how many activities found
							sumWeight = sumWeight + dataFromFile.getWeight(); // Update the sum of the weights
							try{
								sumTime = sumTime + LocalDateTime.parse(dataFromFile.getTimeStart()).until(LocalDateTime.parse(dataFromFile.getTimeEnd()), ChronoUnit.SECONDS); // Update how many seconds between activities
							}catch(Exception e) {
								
							}
						}
					}
				}
			}
		}
		
	}
	scan.close();
	try {
			seconds = sumTime/amount;
			averageWeight = sumWeight/amount;
	} catch (Exception e) {
		System.out.println("There was no data in file");
	}
	
	minutes = Math.floor(seconds/60);
	seconds = seconds%60;
	hours = Math.floor(minutes/60);
	minutes = minutes%60;
	days = Math.floor(hours/24);
	hours = hours%24;
	months = Math.floor(days/(365.25/12));
	days = Math.floor(days%(365.25/12));
	years = Math.floor(months/12);
	months = months%12;
	
	
	information[0] = amount;
	information[1] = Math.round(averageWeight * 100.0)/100.0;
	information[7] = Math.round(seconds * 100.0)/100.0;
	information[6] = Math.round(minutes * 100.0)/100.0;
	information[5] = Math.round(hours * 100.0)/100.0;
	information[4] = Math.round(days * 100.0)/100.0;
	information[3] = Math.round(months * 100.0)/100.0;
	information[2] = Math.round(years * 100.0)/100.0;
	
	
	
	return information;
}
}
