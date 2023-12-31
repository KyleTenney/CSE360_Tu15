/*
 * Author: Kai Reataza
 * Title: LoginController
 * 
 * Description: This controller makes sure the input is correct and then leads the user to the home page.
 * 		Currently the possible user names are the first names of the people in our group but can be easily changed.
 * 		The password for everyone is 123.
 */

package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
public class LoginController {
	HashMap<String,String> logininfo = new HashMap<String,String>();
	public LoginController() {
			logininfo.put("Kai", "123");
			logininfo.put("Kyle", "123");
			logininfo.put("Wejdan", "123");
			logininfo.put("Mohammed", "123");
			logininfo.put("Khalid", "123");
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
		
		if (logininfo.containsKey(username.getText()) && logininfo.get(username.getText()).equals(password.getText())) {		// if username and password are valid go to next scene
			wrongLogin.setText("Success!");
			
			m.changeScene("HomePage.fxml");
			
		}
		
		else {		// display error message if username or password is valid
			wrongLogin.setText("Wrong username or password");
		}
		return;
	}
}



