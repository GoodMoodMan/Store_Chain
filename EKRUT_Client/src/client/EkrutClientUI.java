package client;

import javafx.application.Application;

import javafx.stage.Stage;
import logic.Command;

import java.util.ArrayList;
import java.util.Vector;

import gui.FrameController;
import gui.StartFrameController;
import gui.SubscriberFrameController;

public class EkrutClientUI extends Application {
	public static FrameController frame_ctrl = null;
	public static EkrutClientController client_control; //Only one instance

	//Run As Java Application
	public static void main(String args[]) throws Exception {
		launch(args);
	} //End main

	@Override
	public void start(Stage primaryStage) throws Exception {
		frame_ctrl = new StartFrameController(); // Create StudentFrame
		frame_ctrl.start(primaryStage, "/gui/StartFrame.fxml");
	}
	
	/*
	public static void showTable(ArrayList<ArrayList<String>> updateArr) {
		start_frame_ctrl.showSubscribersTableInGUI(updateArr); // getting table data and showing it in controller
	}
	*/
	
	public static void Connect() {
		client_control.ekrut_client.awaitResponse = true;
		client_control.accept();
		while(client_control.ekrut_client.awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*
		start_frame_ctrl.showSubscribersTableInGUI(client_control.getTable());
		*/
	}
}