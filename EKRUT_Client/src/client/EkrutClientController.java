package client;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import logic.Command;

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class EkrutClientController {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	public EkrutClient ekrut_client;
	
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public EkrutClientController(String host, int port) {
		try {
			ekrut_client = new EkrutClient(host, port, this);
			System.out.println("Connection Succeed");
		}
		catch (IOException exception) {}
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public void accept() {
		// Ask ClientUI to connect the server and import DB
		ekrut_client.handleMessageFromClientUI(new Command("connect", null)); // First connection
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) {
		System.out.println("> " + message);
		EkrutClientUI.frame_ctrl.displayMessage(message);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> getTable() {
		return ekrut_client.getCurrSubscriberTable();
	}

	public void updateTable(ArrayList<ArrayList<String>> updateArr) {
		if (updateArr != null) {
			ekrut_client.handleMessageFromClientUI(new Command("update_table", updateArr));
		}
	}
	
	public void refreshTable() {
		Command cmd = new Command("refresh_table", null);
		ekrut_client.awaitResponse = true;
		ekrut_client.handleMessageFromClientUI(cmd);
		while(ekrut_client.awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//EkrutClientUI.showTable(getTable());
	}

	public void quit() {
		ekrut_client.quit();
	}
}
//End of ConsoleChat class