package gui;

import java.util.ArrayList;

import client.EkrutClientController;
import client.EkrutClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.SubscriberModel;

public class SubscriberFrameController {

	@FXML
	private Button connectBtn = null;
	
	@FXML
	private Button exitBtn = null;

	@FXML
	private Button updtBtn = null;
	
	@FXML
	private ImageView background;

	@FXML
	private TableView<SubscriberModel> tbl;

	@FXML
	private TableColumn<SubscriberModel, String> tbl_fname;

	@FXML
	private TableColumn<SubscriberModel, String> tbl_lname;
	
	@FXML
	private TableColumn<SubscriberModel, String> tbl_id;
	
	@FXML
	private TableColumn<SubscriberModel, String> tbl_phone;

	@FXML
	private TableColumn<SubscriberModel, String> tbl_email;
	
	@FXML
	private TableColumn<SubscriberModel, String> tbl_credit;

	@FXML
	private TableColumn<SubscriberModel, String> tbl_sub;

	@FXML
	private TextArea messageToGUI;
	
	@FXML
	private TextField Ip_Field;
	
	@FXML
	private TextField Port_field;
	
	private static ObservableList<SubscriberModel> subscriberModels = FXCollections.observableArrayList();

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SubscriberFrame.fxml"));
		loader.setController(this); 
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/ekrut.png")));
		primaryStage.setTitle("Subscriber Managment Tool");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setupColumns() {
		// sets the cell factory to use EditCell which will handle key presses
		// and firing commit events
		tbl_fname.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_fname.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow()))
					.setFirstName(value);
			tbl.refresh();
		});
		tbl_lname.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_lname.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow()))
					.setLastName(value);
			tbl.refresh();
		});
		tbl_id.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_id.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow())).setId(value);
			tbl.refresh();
		});
		tbl_phone.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_phone.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPhone(value);
			tbl.refresh();
		});
		tbl_email.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_email.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow())).setEmail(value);
			tbl.refresh();
		});
		tbl_credit.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_credit.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCredit(value);
			tbl.refresh();
		});
		tbl_sub.setCellFactory(TextFieldTableCell.forTableColumn());
		tbl_sub.setOnEditCommit(event -> {
			final String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
			((SubscriberModel) event.getTableView().getItems().get(event.getTablePosition().getRow()))
					.setSubscriber(value);
			tbl.refresh();
		});
	}

	public void showSubscribersTableInGUI(ArrayList<ArrayList<String>> arr) {
		subscriberModels.clear();
		if (arr == null) {
			System.out.println("SubscriberFrameController/showSubscribersTableInGUI");
		}
		for (ArrayList<String> row : arr) {
			subscriberModels.add(new SubscriberModel(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4),
					row.get(5), row.get(6)));
		}
		tbl.setItems(subscriberModels);
		tbl.refresh();
	}

	public ArrayList<ArrayList<String>> getChangedSubscribersTableFromGUI() {
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		for (SubscriberModel s : subscriberModels) {
			ArrayList<String> currUpdatedTableData = new ArrayList<String>();
			currUpdatedTableData.add(s.getFirstName());
			currUpdatedTableData.add(s.getLastName());
			currUpdatedTableData.add(s.getId());
			currUpdatedTableData.add(s.getPhone());
			currUpdatedTableData.add(s.getEmail());
			currUpdatedTableData.add(s.getCredit());
			currUpdatedTableData.add(s.getSubscriber());
			arr.add(currUpdatedTableData);
		}
		return arr;
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Exit EKrut client");
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			EkrutClientUI.client_control.quit();
		}
		System.exit(0);
	}
	
	public void getConnectBtn(ActionEvent event) throws Exception {		
		// setup data table
		tbl.setEditable(true);
		setupColumns();
		tbl_fname.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("firstName"));
		tbl_lname.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("lastName"));
		tbl_id.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("id"));
		tbl_phone.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("phone"));
		tbl_email.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("email"));
		tbl_credit.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("credit"));
		tbl_sub.setCellValueFactory(new PropertyValueFactory<SubscriberModel, String>("subscriber"));
		
		// create new ClientController
		// try to connect
		int port = 5555; // Setting port to default
		try {
			port = Integer.parseInt(Port_field.getText()); //Getting other port
		} catch (Throwable t) {
			System.out.println("Port must be a number, setting port by default to 5555");
			messageToGUI.setText("Port must be a number\n");
			messageToGUI.appendText("Setting port by default to 5555");
			Port_field.setText("" + port);
		}
		try {
			EkrutClientUI.client_control = new EkrutClientController(Ip_Field.getText(), port); // Create connection
			EkrutClientUI.Connect();
			messageToGUI.setText("Connected to server!");
		}
		catch(Exception e) {
			System.out.println("Error: Can't setup connection! Must open connection in server first");
			messageToGUI.setText("Error: Can't setup connection! Must open connection in server first");
		}
	}

	public void getUpdtBtn(ActionEvent event) throws Exception {
		if(EkrutClientUI.client_control.ekrut_client.isConnected) {
			ArrayList<ArrayList<String>> arr = getChangedSubscribersTableFromGUI();
			EkrutClientUI.client_control.updateTable(arr);
			messageToGUI.setText("Table updated successfully."); // Printing to client
			EkrutClientUI.client_control.refreshTable();
		}
		else{
			System.out.println("Error: You must connect to server before updating!");
			messageToGUI.setText("Error: You must connect to server before updating!"); // Printing to client
		}
	}
}