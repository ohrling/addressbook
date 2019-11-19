package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Contact;

import java.util.HashMap;
import java.util.Map;

public class DialogController {
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;
    @FXML private TextField company;

    private Map<String, String> fields = new HashMap<>();
    private Contact updatingContact;

    void setValues() {
        updatingContact = GuiController.getChoosenContact();
        firstName.setText(updatingContact.getFirstName());
        lastName.setText(updatingContact.getLastName());
        email.setText(updatingContact.getEmail());
        phoneNumber.setText(updatingContact.getPhoneNumber());
        company.setText(updatingContact.getCompany());
    }

    @FXML
    Map<String, String> processInput() {
        if(updatingContact != null){
            fields.put("id", String.valueOf(updatingContact.getIdNr()));
            TextField[] temp = new TextField[] {firstName, lastName, email, phoneNumber, company};
            for (TextField field :
                    temp) {
                if(field.getText() == null) {
                    System.out.println(field.getId() + " var tomt.");
                } else {
                    fields.put(field.getId(), field.getText());
                }
            }
            return fields;
        } else {
            fields.put("firstName", firstName.getText());
            fields.put("lastName", lastName.getText());
            fields.put("email", email.getText());
            fields.put("phoneNumber", phoneNumber.getText());
            fields.put("company", company.getText());
            return fields;
        }
    }
}
