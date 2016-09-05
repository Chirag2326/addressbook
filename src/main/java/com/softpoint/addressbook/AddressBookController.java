package com.softpoint.addressbook;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.softpoint.addressbook.config.SpringBoot;
import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.model.PersonAccount;
import com.softpoint.addressbook.model.SocialAccount;
import com.softpoint.addressbook.service.PersonAccountService;
import com.softpoint.addressbook.service.PersonService;
import com.softpoint.addressbook.service.SocialAccountSevice;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

public class AddressBookController implements Initializable {

	private Main main;
	private PersonService personService;
	private PersonAccountService personAccountService;
	private SocialAccountSevice socialAccountService;

	private ObservableList<Person> listPersons;
	private ObjectProperty<Person> objPropPerson;
	
	private PersonAccount personAccount = null;

	@FXML
	private Button btnClose, btnMinimize, btnAddPerson, btnDeletePerson, btnEditPerson;
	@FXML
	private TextField txtSearch;
	@FXML
	private ListView<Person> listViewPerson;

	@FXML
	private AnchorPane paneDetail;
	@FXML
	private ImageView imageViewPhoto;
	@FXML
	private Label lblName, lblMobile, lblOfficeMo, lblAddrLine1, lblAddrLine2;
	@FXML
	private GridPane gridAccounts;
	@FXML
	private Button btnAddAccount;
	@FXML
	private AnchorPane paneAddAccount;
	@FXML
	private ComboBox<SocialAccount> cboxSocialAccount;
	@FXML
	private TextField txtAccountUrl;
	@FXML
	private Button btnSaveUpdateAccount, btnCloseAddAccount;

	public AddressBookController() {
		personService = SpringBoot.getContext().getBean(PersonService.class);
		personAccountService = SpringBoot.getContext().getBean(PersonAccountService.class);
		socialAccountService = SpringBoot.getContext().getBean(SocialAccountSevice.class);
		listPersons = FXCollections.observableArrayList();
		objPropPerson = new SimpleObjectProperty<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnClose.setOnAction(e -> {
			Platform.exit();
		});
		btnMinimize.setOnAction(e -> {
			main.getWindow().setIconified(true);
		});
		btnAddPerson.setOnAction(e -> {
			if (main.initAddEditPerson(null)) {
				getAllPersonAndLoad();
			}
		});

		listViewPerson.setCellFactory(list -> {
			return new ListCell<Person>() {
				@Override
				protected void updateItem(Person item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty)
						setText(null);
					else
						setText(item.getFirstName() + " " + item.getLastName());
				}
			};
		});
		objPropPerson.bind(listViewPerson.getSelectionModel().selectedItemProperty());
		objPropPerson.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				displayLabelData(newValue);
				paneDetail.setVisible(true);
			}
		});

		btnEditPerson.disableProperty().bind(objPropPerson.isNull());
		btnEditPerson.setOnAction(event -> {
			main.initAddEditPerson(objPropPerson.get());
		});
		btnDeletePerson.disableProperty().bind(objPropPerson.isNull());
		btnDeletePerson.setOnAction(e -> {

		});
		
		getAllSocialAccounts();
		
		btnAddAccount.setOnAction(event -> {
			clearAddAccountControls();
			paneAddAccount.setVisible(true);
		});
		btnCloseAddAccount.setOnAction(event -> {
			paneAddAccount.setVisible(false);
		});
		btnSaveUpdateAccount.setOnAction(event -> {
			if(btnSaveUpdateAccount.getText().equals("SAVE")){
				personAccount = new PersonAccount();
				personAccount.setCreatedAt(LocalDate.now());
				personAccount.setUrl(txtAccountUrl.getText());
				personAccount.setPerson(objPropPerson.getValue());
				personAccount.setSocialAccount(cboxSocialAccount.getSelectionModel().getSelectedItem());
				if(personAccountService.savePersonAccount(personAccount)){
					System.out.println("Person Account Saved");
					paneAddAccount.setVisible(false);
					displaySocialAccounts(objPropPerson.getValue());
				}else{
					System.err.println("Error in person account save");
				}
			}else{
				personAccount.setCreatedAt(LocalDate.now());
				personAccount.setUrl(txtAccountUrl.getText());
				personAccount.setSocialAccount(cboxSocialAccount.getSelectionModel().getSelectedItem());
				if(personAccountService.updatePersonAccount(personAccount)){
					System.out.println("Person Account Updated");
					paneAddAccount.setVisible(false);
					displaySocialAccounts(objPropPerson.getValue());
				}else{
					System.err.println("Error in person account update");
				}
			}
		});
	}

	private void clearAddAccountControls() {
		txtAccountUrl.setText("");
		cboxSocialAccount.getSelectionModel().clearSelection();
		btnSaveUpdateAccount.setText("SAVE");
	}

	private void getAllSocialAccounts() {
		List<SocialAccount> socialAccounts = socialAccountService.getAll();
		cboxSocialAccount.setCellFactory((comboBox) -> {
			return new ListCell<SocialAccount>(){
				@Override
				protected void updateItem(SocialAccount item, boolean empty) {
					super.updateItem(item, empty);
					if(item == null || empty){
						setText(null);
					}else{
						setText(item.getAccountName());
					}
				}
			};
		});
		cboxSocialAccount.setConverter(new StringConverter<SocialAccount>() {
			
			@Override
			public String toString(SocialAccount object) {
				return object.getAccountName();
			}
			
			@Override
			public SocialAccount fromString(String string) {
				return null;
			}
		});
		cboxSocialAccount.setItems(FXCollections.observableArrayList(socialAccounts));
	}

	private void displayLabelData(Person person) {
		if(person.getPhotoPath() != null) {
			File photo = new File(person.getPhotoPath());
			if(photo != null && photo.exists()){
				imageViewPhoto.setImage(new Image(photo.toURI().toString(), 100, 125, false, false));
			}else{
				imageViewPhoto.setImage(new Image(new File("file:resources/photo/dummy.jpg").toURI().toString(), 100, 125, false, false));
			}
			Rectangle rect = new Rectangle(imageViewPhoto.getFitWidth(), imageViewPhoto.getFitHeight());
			rect.setArcWidth(10.0);
			rect.setArcHeight(10.0);
			imageViewPhoto.setClip(rect);
		}
		
		lblName.setText(person.getFirstName() + " " + person.getLastName());
		lblMobile.setText(person.getMobileNo());
		lblOfficeMo.setText(person.getOfficeNo());

		Address addr = person.getAddress();
		if (addr != null) {
			lblAddrLine1.setText(addr.getStreet() + ", " + addr.getCity() + "-" + addr.getPincode());
			lblAddrLine2.setText(addr.getDistrict() + " " + addr.getState());
		}
		
		displaySocialAccounts(person);
	}

	private void displaySocialAccounts(Person person) {
		List<PersonAccount> accounts = personAccountService.getPersonAccounts(person);
		if(accounts != null && accounts.size() > 0){
			int row = 0;
			gridAccounts.getChildren().clear();
			gridAccounts.getRowConstraints().clear();
			for (PersonAccount personAccount : accounts) {
				Label lblAccountName = new Label(personAccount.getSocialAccount().getAccountName() +":");
				gridAccounts.add(lblAccountName, 0, row);
				Label lblAccountUrl = new Label(personAccount.getUrl());
				gridAccounts.add(lblAccountUrl, 1, row);
				Button btnEditAccount = new Button("", new ImageView(new Image(getClass().getResource("views/img/edit.png").toString(), 16, 16, true, true)));
				btnEditAccount.getStyleClass().add("context-button");
				btnEditAccount.setOnAction(e -> {
					this.personAccount = personAccount;
					setAddAccountControl();
					paneAddAccount.setVisible(true);
				});
				gridAccounts.add(btnEditAccount, 2, row);
				Button btnDeleteAccount = new Button("", new ImageView(new Image(getClass().getResource("views/img/delete.png").toString(), 16, 16, true, true)));
				btnDeleteAccount.getStyleClass().add("context-button");
				btnDeleteAccount.setOnAction(e -> {
					if(personAccountService.deletePersonAccount(personAccount)){
						System.out.println("Person Account Deleted");
						displaySocialAccounts(objPropPerson.getValue());
					}else{
						System.err.println("Error in person account delete");
					}
				});
				gridAccounts.add(btnDeleteAccount, 3, row);
				gridAccounts.getRowConstraints().add(new RowConstraints(25.0));
				row++;
			}
		}
	}

	private void setAddAccountControl() {
		txtAccountUrl.setText(personAccount.getUrl());
		cboxSocialAccount.getSelectionModel().select(personAccount.getSocialAccount());
		btnSaveUpdateAccount.setText("UPDATE");
	}

	private void getAllPersonAndLoad() {
		listPersons = FXCollections.observableArrayList(personService.getAllPerson());
		listViewPerson.setItems(listPersons);
		
//		System.out.println("Calling remote..........");
//		List<Person> listP = personService.getAllPerson();
//		Map<String, String> map = new HashMap<>();
//		Client client = ClientFactory.newClient();
//		javax.ws.rs.client.Entity<Person> entity = javax.ws.rs.client.Entity.entity(listP.get(0), MediaType.APPLICATION_XML);
//		Response resp = client.target("http://192.168.168.10:8080/AddressBookWeb/").path("persons").request().post(entity);
//		System.out.println(resp);
//		System.out.println("call comp......");
	}

	public void setMain(Main main) {
		this.main = main;
		getAllPersonAndLoad();
	}

	public ObservableList<Person> getListPersons() {
		return listPersons;
	}
}
