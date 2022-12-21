package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.EkrutServer;

import java.io.IOException;
import java.util.Vector;

import gui.ServerFrameController;

public class EkrutServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;

	private static EkrutServer Eserver;

	public static ServerFrameController serverFrameCtrl;
	
	public static boolean isServerOpen = false;

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	public static EkrutServer getServer() {
		return Eserver;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		serverFrameCtrl = new ServerFrameController();
		serverFrameCtrl.start(primaryStage);
	}

	public static void runServer(String p) {
		int port = 5555; //Port is setted by default to 5555
		boolean isNumber = true;
		try {
			port = Integer.parseInt(p);
		}
		catch (Throwable t) {
			System.out.println("Port must be a number");
			display("Port must be a number");
			isNumber = false;
		}
		if(isNumber) {
			Eserver = new EkrutServer(port);
			isServerOpen = true;
			if (mysqlConnection.isMySqlConnectSucceed) {
				try {
					Eserver.listen(); // Start listening for connections
				}
				catch (Exception ex) {
					System.out.println("ERROR - Could not listen for clients!");
					display("ERROR - Could not listen for clients!");
				}
			}
			else {
				System.out.println("ERROR - Could not connect to database");
				EkrutServerUI.display("ERROR - Could not connect to database");
			}
		}
	}

	public static void display(String msg) {
		serverFrameCtrl.displayConsoleMessage(msg);
	}

	public static void endServer() {
		if(isServerOpen) {
			try {
				Eserver.stopListening();
				Eserver.close();
				isServerOpen = false;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}