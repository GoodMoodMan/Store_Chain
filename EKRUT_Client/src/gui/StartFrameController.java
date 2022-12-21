package gui;

import client.EkrutClientController;
import client.EkrutClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StartFrameController extends FrameController {

	@FXML
	private TextField ip_field;

	@FXML
	private TextField port_field;

	//Override because we can't check: EkrutClientUI.client_control.ekrut_client.isConnected ---> No ClientController!!!
	@Override
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKRUT Client");
		System.exit(0);
	}

	public void getConnectBtn(ActionEvent event) throws Exception {
		String ip = ip_field.getText();
		String port = port_field.getText();
		boolean isNumber = true;
		if (ip.trim().isEmpty() || port.trim().isEmpty()) {
			message_to_gui.setText("You must enter IP and Port in order to connect!");
		} 
		else {
			try {
				Integer.parseInt(port); // Getting other port
			}
			catch (Throwable t) {
				isNumber = false;
			}
			if (!isNumber) {
				System.out.println("Port must be a number");
				message_to_gui.setText("Port must be a number");
			}
			else {
				try {
					EkrutClientUI.client_control = new EkrutClientController(ip, Integer.parseInt(port)); // Create connection
					EkrutClientUI.Connect();
					System.out.println("Connected to server!");
					LoginFrameController next = new LoginFrameController();
					next.start(primaryStage, "/gui/LoginFrame.fxml");
				} catch (Exception e) {
					System.out.println("Error: Can't setup connection! Must open connection in server first");
					message_to_gui.setText("Error: Can't setup connection!");
				}
			}
		}
	}
	
	@Override
	public void additionalChanges() {
		// Nothing to change in StartFrameController
	}
}