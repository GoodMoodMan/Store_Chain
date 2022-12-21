package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import logic.Command;
import logic.User;
import ocsf.server.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EkrutServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ekrut server.
	 *
	 * @param port The port number to connect on.
	 */
	public EkrutServer(int port) {
		super(port);
		mysqlConnection.awaitResponse = true;
		mysqlConnection.ConnectDb(
				"jdbc:mysql://localhost/" + EkrutServerUI.serverFrameCtrl.getDbName() + "?serverTimezone=IST",
				EkrutServerUI.serverFrameCtrl.getDbUsername(), EkrutServerUI.serverFrameCtrl.getDbPass());
		while (mysqlConnection.awaitResponse) {
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (mysqlConnection.isDriverSuccssed) {
			EkrutServerUI.display("Driver definition succeed");
		}
		else {
			EkrutServerUI.display("Driver definition failed");
		}
		if (mysqlConnection.isMySqlConnectSucceed) {
			EkrutServerUI.display("SQL connection succeed");
		}
		else {
			EkrutServerUI.display("SQL connection failed");
		}
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof Command) {
			Command cmd = (Command) msg;
			if (cmd.getId().equals("connect")) {
				// First connection
				Command send_table = new Command("GetTableFromDB", (Object) this.GetTable());
				try {
					client.sendToClient((Object) send_table);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Client " + client.getId() + " Connected successfully");
				EkrutServerUI.display("Client " + client.getId() + " Connected successfully");
			}
			if (cmd.getId().equals("login_attempt")) {
				Command send_back = mysqlConnection.checkUsernameAndPassword(cmd.getData());
				try {
					if (send_back.getData() instanceof User) {
						System.out.println("Login info is verified and sent back to the client " + client.getId());
						EkrutServerUI.display("Login info is verified and sent back to the client " + client.getId());
					}
					else {
						System.out.println("Login info is denied and sent back to the client " + client.getId());
						EkrutServerUI.display("Login info is denied and sent back to the client " + client.getId());
					}
					client.sendToClient((Object) send_back);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (cmd.getId().equals("updateData")) {
				// Updating the new changed table into the DB
				mysqlConnection.updateTable(cmd.getData());
				System.out.println("Table was updated successfully in DB");
				EkrutServerUI.display("Table was updated successfully in DB");
			}
			if (cmd.getId().equals("get_table")) {
				// send updated table
				Command send_table = new Command("GetTableFromDB", (Object) this.GetTable());
				try {
					client.sendToClient((Object) send_table);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Table sent to client " + client.getId() + " successfully");
				EkrutServerUI.display("Table sent to client " + client.getId() + " successfully");
			}
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		EkrutServerUI.display("Server listening for connections on port " + getPort());
	}

	@SuppressWarnings("unchecked")
	protected ArrayList<ArrayList<String>> GetTable() {
		ArrayList<ArrayList<String>> table = null;
		table = mysqlConnection.retrieveTableFromDB_ToServer();
		return table;
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server stopped listening for connections");
		EkrutServerUI.display("Server stopped listening for connections");
		Command stop = new Command("stopping_server", null);
		sendToAllClients(stop);
	}

	public static String getIP() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip.toString();
	}

	public static String getHost() {
		InetAddress ip = null;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return hostname;
	}
}
//End of EkrutServer class