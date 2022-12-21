package gui;

import java.util.ArrayList;

import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.Command;

public class LoginFrameController extends FrameController {
	
	@FXML
	private TextField username_field;
	
	@FXML
	private PasswordField password_field;
	
	public void getLoginBtn(ActionEvent event) throws Exception {
		message_to_gui.setText("");
		String username = username_field.getText();
		String password = password_field.getText();
		
		if(username.trim().isEmpty() || password.trim().isEmpty()) {
			message_to_gui.setText("You must enter Username and Password in order to connect!");
		}
		else {
			ArrayList<String> info = new ArrayList<String>();
			info.add(username);
			info.add(password);
			Command cmd = new Command("login_attempt", info);
			EkrutClientUI.client_control.ekrut_client.handleMessageFromClientUI(cmd);
		}
	}

	@Override
	public void additionalChanges() {
		// Nothing to change in LoginFrameController
	}
}