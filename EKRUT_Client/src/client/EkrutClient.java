// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import logic.Command;
import logic.Customer;
import logic.Role;
import logic.User;
import logic.UserType;

import java.io.*;
import java.util.ArrayList;

import gui.CustomerMainScreenFrameController;
import javafx.application.Platform;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class EkrutClient extends AbstractClient {

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	EkrutClientController client_ctrl;
	
	public User user;

	private ArrayList<ArrayList<String>> subscriberTable;

	public boolean awaitResponse = false;

	public boolean isConnected = false;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ekrut client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public EkrutClient(String host, int port, EkrutClientController client_ctrl) throws IOException {
		super(host, port); // Call the superclass constructor
		this.client_ctrl = client_ctrl;
		openConnection();
	}

	// Instance methods ************************************************

	// Function that returns our current subscriber table
	public ArrayList<ArrayList<String>> getCurrSubscriberTable() {
		return subscriberTable;
	}

	/**
	 * This method handles all data that comes in from the server.
	 * 
	 * @param msg The message from the server.
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		// If the object message is a Command class ---> String to execute, The Table
		if (msg instanceof Command) {
			Command cmd = (Command) msg;
			if (cmd.getId().equals("user_check")) {
				if (cmd.getData() instanceof User) {
					
					Role role = ((User)cmd.getData()).getRole();
					switch(role) {
						case Customer:
							//user = new Customer((User) cmd.getData());
							CustomerMainScreenFrameController next = new CustomerMainScreenFrameController();
							Platform.runLater(()->{
								try {
									next.start(EkrutClientUI.frame_ctrl.primaryStage, UserType.Customer.toString());
								}
								catch (Exception e) {
									e.printStackTrace();
								}
							});
						default:
							break;
					}
				}
				else {
					client_ctrl.display("The Username or Password is incorrect");
				}
			}
			if (cmd.getId().equals("GetTableFromDB")) {
				subscriberTable = (ArrayList<ArrayList<String>>) cmd.getData();
				awaitResponse = false;
			}
			if (cmd.getId().equals("stopping_server")) {
				isConnected = false;
			}
		} else {
			System.out.println("EkrutClient/handleMessageFromServer: Object must be a Command class");
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param array The message from the UI.
	 */

	public void handleMessageFromClientUI(Object msg) {
		if (msg instanceof Command) {
			Command cmd = (Command) msg;

			if (cmd.getId().equals("connect")) {
				try {
					sendToServer(msg); // Sending the first message
					isConnected = true;
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
			}

			if (cmd.getId().equals("login_attempt")) {
				try {
					openConnection();
					sendToServer(cmd); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
			}

			if (cmd.getId().equals("update_table")) {
				Command updateTable = new Command("updateData", cmd.getData());
				try {
					openConnection(); // In order to send more than one message
					awaitResponse = true;
					sendToServer(updateTable);
				} catch (IOException e) {
					client_ctrl.display("Could not send message to server. Terminating client.");
					e.printStackTrace();
					quit();
				}
			}

			if (cmd.getId().equals("refresh_table")) {
				Command send = new Command("get_table", null);
				try {
					openConnection();
					sendToServer(send); // Sending the first message
				} catch (IOException e) {
					e.printStackTrace();
					client_ctrl.display("Could not send to server. Terminating client.");
					quit();
				}
			}
		}
	}

	/**
	 * This method terminates the client.
	 * 
	 * @throws IOException
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			System.out.println("IOException! No connection open");
		}
		System.exit(0);
	}
}
//End of EkrutClient class