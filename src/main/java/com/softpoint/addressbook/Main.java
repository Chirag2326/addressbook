package com.softpoint.addressbook;

import com.softpoint.addressbook.config.SpringBoot;
import com.softpoint.addressbook.model.Person;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	private Stage window;

	private FXMLLoader loader;
	private AnchorPane contentPane;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		SpringBoot.init();
		
		this.window = primaryStage;
		initMainWindow();
		this.window.setTitle("Address Book Application");
		this.window.setResizable(false);
		this.window.initStyle(StageStyle.UNDECORATED);
		this.window.show();		
	}
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getWindow() {
		return window;
	}
	private void initMainWindow() {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("views/AddressBook.fxml"));
			contentPane = (AnchorPane) loader.load();
			AddressBookController controller = loader.getController();
			controller.setMain(this);
			Scene scene = new Scene(contentPane);
			window.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean initAddEditPerson(Person person) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("views/PersonAddEdit.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(window);
			stage.initStyle(StageStyle.UNDECORATED);
			Scene scene = new Scene(pane);
			stage.setScene(scene);

			PersonAddEditController controller = loader.getController();
			controller.setMain(this);
			controller.setDialogStage(stage);
			controller.setPerson(person);
			stage.showAndWait();
			return controller.isOkClick();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
