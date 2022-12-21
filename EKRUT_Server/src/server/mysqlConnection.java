package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gui.ServerFrameController;
import logic.Command;
import logic.Customer;
import logic.Role;
import logic.User;

public class mysqlConnection {

	static Connection connect;

	public static boolean awaitResponse = false;
	public static boolean isMySqlConnectSucceed = false;
	public static boolean isDriverSuccssed = false;

	private static void printSqlError(SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
		EkrutServerUI.display("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		EkrutServerUI.display("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		EkrutServerUI.display("VendorError: " + ex.getErrorCode());
	}

	public static void ConnectDb(String dbUrl, String dbUsername, String dbPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
			isDriverSuccssed = true;
		}
		catch (Exception ex) {
			System.out.println("Driver definition failed");
			isDriverSuccssed = false;
		}
		try {
			Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			System.out.println("SQL connection succeed");
			connect = conn;
			isMySqlConnectSucceed = true;
		}
		catch (SQLException ex) {
			printSqlError(ex);
			isMySqlConnectSucceed = false;
		}
		awaitResponse = false;
	}

	public static Command checkUsernameAndPassword(Object info) {
		if (info == null) {
			return new Command("user_check", false);
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> checkInfo = (ArrayList<String>) info;
		User user;
		try {
			Statement stmt = connect.createStatement();
			// Import users result set
			ResultSet resSet = stmt.executeQuery("SELECT * FROM users");
			while (resSet.next()) {
				// Checking for username
				if (resSet.getString(1).equals(checkInfo.get(0))) {
					// Checking if password is ok
					if (resSet.getString(2).equals(checkInfo.get(1))) {
						user = new User(resSet.getString(1), resSet.getString(3), resSet.getString(4),
								resSet.getString(5), Role.valueOf(resSet.getString(6)), resSet.getString(7),
								resSet.getString(8), resSet.getInt(9));
						resSet.close();
						Role role = user.getRole();
						/*
						switch(role) {
							case Customer:
								resSet = stmt.executeQuery("SELECT * FROM customer");
								User customer = new Customer(user);
						}
						*/
						resSet.close();
						return new Command("user_check", user);
					}
				}
			}
			resSet.close();
		}
		catch (SQLException ex) {
			printSqlError(ex);
		}
		return new Command("user_check", false);
	}

	public static void changeUserLoginStatus(Object info) {
		User user = (User) info;
		try {
			PreparedStatement ps = mysqlConnection.connect.prepareStatement(
					"SELECT username, isLoggedIn FROM users WHERE username = '" + user.getUsername() + "'");
			ResultSet resSet = ps.executeQuery();
			ps = mysqlConnection.connect.prepareStatement("UPDATE users SET isLoggedIn = ? WHERE username = ?");
			//User is connected, logging out
			if (resSet.getInt(2) == 1) {
				ps.setInt(1, 0);
				ps.setString(2, user.getUsername());
				ps.executeUpdate();
			} 
			//User is disconnected, logging in
			else {
				ps.setInt(1, 1);
				ps.setString(2, user.getUsername());
				ps.executeUpdate();
			}
			resSet.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failure: User login status can't be changed");
		}
	}

	// Method that returns 2D StringArray (Mat) of given ResultSet
	private static ArrayList<ArrayList<String>> convertResultSetToTable(ResultSet rs) {
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<String>();
				for (int i = 1; i <= numberOfColumns; i++)
					row.add(rs.getString(i));
				table.add(row);
			}
		} catch (SQLException ex) { // Handle any errors
			printSqlError(ex);
		}
		return table;
	}

	// Method that retrieve table from database and saves it in a
	// ArrayList<ArrayList<String>> variable
	public static ArrayList<ArrayList<String>> retrieveTableFromDB_ToServer() {
		ArrayList<ArrayList<String>> table = null;
		try {
			// select statement
			Statement stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// import result set
			ResultSet resSet = stmt.executeQuery("SELECT * FROM subscribers");
			table = convertResultSetToTable(resSet);
			resSet.close();
		} catch (SQLException ex) {/* handle any errors */
			printSqlError(ex);
		}
		return table;
	}

	// Method that updates the table according to the user new input data
	public static void updateTable(Object data) {
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> updateTable = (ArrayList<ArrayList<String>>) data;
		ResultSet resSet = null;
		try {
			// Select statement
			Statement stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// Import result set
			resSet = stmt.executeQuery("SELECT * FROM subscribers");

		} catch (SQLException ex) {/* handle any errors */
			printSqlError(ex);
		}

		if (updateTable == null) {
			System.out.println("Table does not exists in database");
			EkrutServerUI.display("Table does not exists in database");
		} else {
			try {
				while (resSet.next()) {
					for (int i = 0; i < updateTable.get(0).size(); i++) {
						if (i == 2) {
							continue;
						}
						resSet.updateString(i + 1, updateTable.get(resSet.getRow() - 1).get(i));
					}
					resSet.updateRow();

				}
				resSet.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			resSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}