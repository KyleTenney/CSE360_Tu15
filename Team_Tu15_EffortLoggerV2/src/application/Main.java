/*
 * Author: Kai Reataza
 * Title: Login
 * Last update: 11/4/2023   5:10 PM
 * 
 * Description: This main controller starts the user on the log in page and holds a method to change scenes 
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


public class Main extends Application {
	
	private static Stage stg;
	
	@Override
	public void start(Stage primaryStage) throws Exception {							// Sets the stage for the login screen
			stg = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
			primaryStage.setTitle("EffortLogger");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();	
	}
	
	public void changeScene(String fxml) throws IOException {							// Allows program to switch from scene to scene
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	public static void main(String[] args) {		// launches application from main
		launch(args);
	}
}




