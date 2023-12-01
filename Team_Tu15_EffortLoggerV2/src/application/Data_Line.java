/*
 * Author: Kyle Tenney
 * Title: Data_Line
 * 
 * Description: This is a class to hold the information of a single effort activity.
 *  This will be in charge of managing the layout of the file and the data being put in
 *  This will be used to hold and format the data to be put in the file.
 *  The fullLine is what is written in the file.
 *  
 */

package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Data_Line {
	// These are the variables contained for each data input
	private String fullLine;
	// The times are strings instead of LocalDateTime types because Strings are easier to make sure of the length.
	private String timeStart;   
	private String timeEnd;     
	private String project;
	private String lifeCycleStep;
	private String effortCatagory;
	private String subSection;
	private String description;
	private int weight;
	
	// This constructor is for making the object all at once 
	public Data_Line(String timeStart, String timeEnd, String project, String lifeCycleStep,
					String effortCatagory, String subSection, String description, int weight) {
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.project = project;
		this.lifeCycleStep = lifeCycleStep;
		this.effortCatagory = effortCatagory;
		this.subSection = subSection;
		this.description = description;
		this.weight = weight;
		this.setFullLine();
	}
	
	// This constructor is to make the object as soon as the start button is selected 
	public Data_Line(LocalDateTime timeStart) {
		this.timeStart = timeStart.toString();
		this.timeEnd = "";
		this.project = "";
		this.lifeCycleStep = "";
		this.effortCatagory = "";
		this.subSection = "";
		this.description = "";
		this.weight = 9;
		this.setFullLine();
	}
	
	// This constructor is for when taking a line from the file and filling out the rest of the data
	public Data_Line(String fullLine) {
		this.fullLine = fullLine;
		makeDatas(fullLine);
	}
	
	// These are getters so that the other parts of the code has to go through here to get the values
	// instead of being able to access it themselves. 
	public String getFullLine() {
		return this.fullLine;
	}
	public String getTimeStart() {
		return this.timeStart;
	}
	public String getTimeEnd() {
		return this.timeEnd;
	}
	public String getProject() {
		return this.project;
	}
	public String getLifeCycleStep() {
		return this.lifeCycleStep;
	}
	public String getEffortCatagory() {
		return this.effortCatagory;
	}
	public String getSubSection() {
		return this.subSection;
	}
	public String getDescription() {
		return this.description;
	}
	public int getWeight() {
		return this.weight;
	}
	
	// Setters
	private void setFullLine() {
		this.fullLine = timeStart+"~"+timeEnd+"~"+project+"~"+lifeCycleStep+"~"+effortCatagory+"~"+subSection+"~"+description+"~"+weight+"~";
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public void setLifeCycleStep(String lifeCycleStep) {
		this.lifeCycleStep = lifeCycleStep;
	}
	public void setEffortCatagory(String effortCatagory) {
		this.effortCatagory = effortCatagory;
	}
	public void setSubSection(String subSection) {
		this.subSection = subSection;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	// When from reading the file, this will fill in the rest of the data from the fullString
	private void makeDatas(String fullString) {
		String subString = fullString;
		int dataStep = 1;
		while(!(subString.length() == 0)) {
			switch(dataStep) {
			case 1:
				this.timeStart = subString.substring(0, subString.indexOf("~"));
				break;
			case 2:
				this.timeEnd = subString.substring(0, subString.indexOf("~"));
				break;
			case 3:
				this.project = subString.substring(0, subString.indexOf("~"));
				break;
			case 4:
				this.lifeCycleStep = subString.substring(0, subString.indexOf("~"));
				break;
			case 5:
				this.effortCatagory = subString.substring(0, subString.indexOf("~"));
				break;
			case 6: 
				this.subSection = subString.substring(0, subString.indexOf("~"));
				break;
			case 7:
				this.description = subString.substring(0, subString.indexOf("~"));
				break;
			case 8:
				this.weight = Integer.parseInt(subString.substring(0, subString.indexOf("~")));
				break;
			}
			dataStep++;
			subString = subString.replaceFirst(subString.substring(0, subString.indexOf("~")).concat("~"), ""); //This is to cut off the part that was just put in
		}
	}
	
	// Write the data in the file
	public void inputInFile() {
		File myFile = new File("Team_Tu15_Data.txt");
		try {
		      if (myFile.createNewFile()) {
		        //System.out.println("File created: " + myFile.getName());
		      } else {
		        //System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		try {
			  FileWriter myWriter = new FileWriter("Team_Tu15_Data.txt", true);
		      myWriter.write(this.fullLine + "\n");
		      myWriter.flush();  //Make sure to write to the file before closing
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	public boolean equals(Data_Line x) {
		boolean flag;
		if(this.fullLine.equals(x.fullLine)) {
			flag = true;
		}
		else {
			flag = false;
		}
		return flag;
	}
	
}



