package gui;

import client.EkrutClientUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomerMainScreenFrameController extends FrameController {
	
	@FXML
	private ImageView view_catalog;
	
	@FXML
	private ImageView create_new_order;
	
	@FXML
	private ImageView manage_cart;
	
	@FXML
	private ImageView collect_order;
	
	@Override
	public void additionalChanges() {
		view_catalog.setImage(new Image(this.getClass().getResourceAsStream("/view_catalog.png")));
		create_new_order.setImage(new Image(this.getClass().getResourceAsStream("/create_new_order.png")));
		manage_cart.setImage(new Image(this.getClass().getResourceAsStream("/manage_cart.png")));
		collect_order.setImage(new Image(this.getClass().getResourceAsStream("/collect_order.png")));
		full_name.setText(EkrutClientUI.client_control.ekrut_client.user.getFirstName() + " " + EkrutClientUI.client_control.ekrut_client.user.getLastName());
		/*
		if(EkrutClientUI.client_control.ekrut_client.user.) {
			
		}
		*/
	}
}