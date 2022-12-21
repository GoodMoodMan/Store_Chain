package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.EkrutServer;
import server.EkrutServerUI;
import server.mysqlConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ServerFrameController {

	@FXML
	private Button exit_btn = null;
	
	@FXML
	private Button connect_btn = null;
	
	@FXML
	private Button disconnect_btn = null;
	
	@FXML
	private ImageView ekrut_logo;
	
	@FXML
	private ImageView about_logo;
	
	@FXML
	private Label status;
	
	@FXML
	private Label host_ip;
	
	@FXML
	private TextField port_field;
	
	@FXML
	private TextField db_name;
	
	@FXML
	private TextField db_username;

	@FXML
	private PasswordField db_password;
	
	@FXML
	private TextArea message_to_gui;
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerFrame.fxml"));
		loader.setController(this); // You need to set this instance as the controller.
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ekrut_logo.setImage(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		about_logo.setImage(new Image(this.getClass().getResourceAsStream("/about.png")));
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("EKRUT Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		message_to_gui.setText("Please enter port number (default: 5555)\n");
	}
	
	public String getPort() {
		return port_field.getText();
	}
	
	public String getDbName() {
		return db_name.getText();
	}
	
	public String getDbUsername() {
		return db_username.getText();
	}
	
	public String getDbPass() {
		return db_password.getText();
	}

	public void getConnectBtn(ActionEvent event) throws Exception {
		String port, ip;
		port = getPort();
		if (port.trim().isEmpty()) {
			System.out.println("You must enter a port number.\n");
			message_to_gui.setText("You must enter a port number.\n");
		}
		else {
			EkrutServerUI.runServer(port);
			if(EkrutServerUI.isServerOpen && mysqlConnection.isMySqlConnectSucceed) {
				ip = EkrutServerUI.getServer().getIP();
				host_ip.setText(ip);
				host_ip.setStyle("-fx-text-fill: #00FF00");
				status.setText("Online"); // Printing to client
				status.setStyle("-fx-text-fill: #00FF00");
				connect_btn.setDisable(true);
				disconnect_btn.setDisable(false);
			}
		}
	}
	
	public void getDisconnectBtn(ActionEvent event) throws Exception{
		EkrutServerUI.endServer();
		host_ip.setText("Not Connected");
		host_ip.setStyle("-fx-text-fill: #FF3131");
		status.setText("Offline"); // Printing to client
		status.setStyle("-fx-text-fill: #FF3131");
		connect_btn.setDisable(false);
		disconnect_btn.setDisable(true);
	}
	
	public void displayConsoleMessage(String msg) {
		message_to_gui.appendText(msg + "\n");
	}
	
	/*
	public void displayPortNumber(String msg) {
		port_field.setText(msg);
	}
	*/
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKRUT Server");
		EkrutServerUI.endServer();
		System.exit(0);
	}
	
	public void getAboutBtn(ActionEvent event) throws Exception{
		//TODO Later! Need an implementation
	}
}