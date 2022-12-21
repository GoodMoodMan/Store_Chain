package gui;

import client.EkrutClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class FrameController {
	
	@FXML
	protected Label full_name;
	
	@FXML
	protected Label status;
	
	@FXML
	private Button exit_btn;
	
	@FXML
	private Button about_btn;
	
	@FXML
	private ImageView ekrut_logo;
	
	@FXML
	private ImageView about_logo;
	
	@FXML
	private ImageView home_logo;
	
	@FXML
	private ImageView logout_logo;
	
	@FXML
	protected Label message_to_gui;
		
	public Stage primaryStage;
		
	public void start(Stage primaryStage, String resource) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ekrut_logo.setImage(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		about_logo.setImage(new Image(this.getClass().getResourceAsStream("/about.png")));
		home_logo.setImage(new Image(this.getClass().getResourceAsStream("/home.png")));
		logout_logo.setImage(new Image(this.getClass().getResourceAsStream("/logout.png")));
		additionalChanges();
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("Ekrut Client");
		primaryStage.setScene(scene);
		primaryStage.show();
		EkrutClientUI.frame_ctrl = this;
		this.primaryStage = primaryStage;
	}
	
	public abstract void additionalChanges();
	
	public void displayMessage(String msg) {
		Platform.runLater(()->{message_to_gui.setText(msg);});
	}
	
	public void getExitBtn(ActionEvent event) throws Exception{
		System.out.println("Exit EKRUT Client");
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			EkrutClientUI.client_control.quit();
		}
		System.exit(0);
	}
	
	public void getAboutBtn(ActionEvent event) throws Exception{
		//TODO Later! Need an implementation
	}
}