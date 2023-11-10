/*
 * Author: Kai Reataza
 * Title: Login
 * Last update: 11/1/2023     2:17 PM
 * 
 * Description:
 */

package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
public class Login {
	
	public Login() {
		
	}
	
	@FXML
	private Button button;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;				// declaring FXML objects
	@FXML
	private Label wrongUNInput;
	@FXML
	private PasswordField password;
	@FXML
	private Label wrongPWInput;
	
	int passWord;	// declare password variable
	
	public void userLogin(ActionEvent event) throws IOException {
		checkLogin();
	}
	
	private void checkLogin() throws IOException {		// verifies login information
		wrongPWInput.setText(null);		
		wrongLogin.setText(null);
		Main m = new Main();	// declare var m to use changeScene
		
		if (username.getText().isEmpty() || password.getText().isEmpty()) {			// display error msg if input is missing and escape method
			wrongLogin.setText("Please enter you data.");				
			return;
		}
		
		try {		// only accept password as integer
		passWord = Integer.parseInt(password.getText());	
		}
		catch (NumberFormatException e) {
			wrongPWInput.setText("Enter only numbers please");
			return;
		}
		
		
		if (username.getText().toString().equals("EffortLogger") && passWord == 123) {		// if username and password are valid go to next scene
			wrongLogin.setText("Success!");
			
			m.changeScene("HomePlaceHolder.fxml");
			
		}
		
		else {		// display error message if username or password is valid
			wrongLogin.setText("Wrong username or password");
		}
		return;
	}
}



