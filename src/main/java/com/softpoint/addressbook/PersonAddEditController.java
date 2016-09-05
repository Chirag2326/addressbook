package com.softpoint.addressbook;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;

import com.softpoint.addressbook.config.SpringBoot;
import com.softpoint.addressbook.model.Address;
import com.softpoint.addressbook.model.Person;
import com.softpoint.addressbook.service.PersonService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PersonAddEditController implements Initializable {

	private Main main;
	private Stage dialogStage;
	private boolean okClick;
	private Person person;
	private Address address;

	private String photoPath;
	private PersonService personService;

	@FXML
	private Button btnClose, btnAddPhoto, btnSaveUpdate;
	@FXML
	private TextField txtFirstName, txtLastName, txtMlobileNo, txtOfficeNo, txtStreet, txtCity, txtPincode,
			txtDistrict, txtState;
	@FXML
	private ImageView imageViewPhoto;

	public PersonAddEditController() {
		personService = SpringBoot.getContext().getBean(PersonService.class);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnClose.setOnAction(e -> {
			okClick = false;
			dialogStage.close();
		});
		btnAddPhoto.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files Only", "*.png", "*.jpg", "*.JPG"));
			File photo = fileChooser.showOpenDialog(dialogStage);
			if (photo != null) {
				photoPath = "resources/photo/" + UUID.randomUUID().toString().replaceAll("-", "") + "."
						+ photo.getName().substring(photo.getName().lastIndexOf(".") + 1);
				File dest = new File(photoPath);
				if (dest != null) {
					try {
						Files.copy(photo.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				setupImagePhoto(dest);
			}
		});
		btnSaveUpdate.setOnAction(e -> {
			if (person == null) {
				person = new Person();
				getPersonData();
				address = new Address();
				getAddressData();
				person.setAddress(address);
				address.setPerson(person);

				if (savePerson()) {
					okClick = true;
					dialogStage.close();
					System.out.println("Person Saved");
				} else {
					System.out.println("Error in person save");
				}
			} else {
				getPersonData();
				address = getAddressForPerson();
				if (address == null)
					address = new Address();
				getAddressData();
				person.setAddress(address);
				address.setPerson(person);

				if (updatePerson()) {
					okClick = true;
					dialogStage.close();
					System.out.println("Person Updated");
				} else {
					System.out.println("Error in person update");
				}
			}
		});
	}

	private boolean updatePerson() {
		try {
			return personService.updatePerson(person);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean savePerson() {
		try {
			return personService.insertPerson(person);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Address getAddressForPerson() {
		return person.getAddress();
	}

	private void getAddressData() {
		try {
			address.setStreet(txtStreet.getText().trim());
			address.setCity(txtCity.getText().trim());
			address.setPincode(txtPincode.getText().trim());
			address.setDistrict(txtDistrict.getText().trim());
			address.setState(txtState.getText().trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPersonData() {
		try {
			person.setFirstName(txtFirstName.getText().trim());
			person.setLastName(txtLastName.getText().trim());
			person.setMobileNo(txtMlobileNo.getText().trim());
			person.setOfficeNo(txtOfficeNo.getText().trim());
			person.setPhotoPath(photoPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(Person person) {
		this.person = person;
		if (person != null) {
			loadValues();
		}
	}

	private void loadValues() {
		txtFirstName.setText(person.getFirstName());
		txtLastName.setText(person.getLastName());
		txtMlobileNo.setText(person.getMobileNo());
		txtOfficeNo.setText(person.getOfficeNo());

		setupImagePhoto(person.getPhotoPath() != null ? new File(person.getPhotoPath()) : null);

		Address addr = person.getAddress();
		txtStreet.setText(addr.getStreet());
		txtCity.setText(addr.getCity());
		txtPincode.setText(addr.getPincode());
		txtState.setText(addr.getState());
		txtDistrict.setText(addr.getDistrict());
	}

	private void setupImagePhoto(File file) {
		try {
			if (file != null && file.exists()) {
				imageViewPhoto.setImage(new Image(file.toURI().toString(), 100, 125, false, false));				
			}else {
				imageViewPhoto.setImage(new Image(new File("resources/photo/dummy.jpg").toURI().toString(), 100, 125, false, false));
			}
			Rectangle rect = new Rectangle(imageViewPhoto.getFitWidth(), imageViewPhoto.getFitHeight());
			rect.setArcWidth(10.0);
			rect.setArcHeight(10.0);
			imageViewPhoto.setClip(rect);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean isOkClick() {
		return okClick;
	}
}
