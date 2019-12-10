package core.editcontact;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import dbWorker.SQLCreate;
import dbWorker.SQLUpdate;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static core.singletons.ObjectPasser.contact;

// Ritar upp och behandlar data för att uppdatera eller skapa en ny kontakt
public class DialogController implements Initializable {
    @FXML private Button doneBtn;
    @FXML private Button cancelBtn;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNumber;
    @FXML private TextField company;

    private final static String errorCSSStyle = "-fx-border-color: red; ";
    private final static String defaultCSSStyle = "-fx-border-width: 0px ;";
    private static final int FIRST_NAME = 1, LAST_NAME = 2, EMAIL = 3, PHONE_NUMBER = 4;

    private Map<String, String> fields = new HashMap<>();

    private void focusState(TextField tf, boolean isFocused, int field) {
        if (isFocused) {
            tf.setStyle(defaultCSSStyle);
        } else {
            switch (field) {
                case FIRST_NAME:
                    if ((tf.getText().length() > 0)) {
                        tf.setStyle(defaultCSSStyle);
                    } else {
                        //Alla kontakter måste ha ett förnamn
                        tf.setStyle(errorCSSStyle);
                    }
                    break;
                case LAST_NAME:
                    //Alla kontakter måste inte ha ett efternamn
                    break;
                case EMAIL:
                    if ((tf.getText().length() == 0) || EmailValidator.getInstance().isValid(tf.getText())) {
                        tf.setStyle(defaultCSSStyle);
                    } else if (! EmailValidator.getInstance().isValid(tf.getText())) {
                        tf.setStyle(errorCSSStyle);
                    }
                    break;
                case PHONE_NUMBER:
                    if (tf.getText().length() == 0) {
                        tf.setStyle(defaultCSSStyle);
                    } else {
                        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                        Phonenumber.PhoneNumber enteredNumber = null;
                        try {
                            enteredNumber = phoneUtil.parse(tf.getText(), PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL.toString());
                        } catch (NumberParseException e) {
                            tf.setStyle(errorCSSStyle);
                            break;
                        } finally {
                            if(phoneUtil.isPossibleNumber(enteredNumber)) {
                                tf.setStyle(defaultCSSStyle);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        Stage stage = (Stage) tf.getScene().getWindow();
        stage.setScene(tf.getScene());
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (contact != null) {
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            email.setText(contact.getEmail());
            phoneNumber.setText(contact.getPhoneNumber());
            company.setText(contact.getCompany());
        }
        firstName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                focusState(firstName, newValue, FIRST_NAME));

        lastName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                focusState(lastName, newValue, LAST_NAME));

        email.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                focusState(email, newValue, EMAIL));

        phoneNumber.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                focusState(phoneNumber, newValue, PHONE_NUMBER));
    }

    public void dialogCancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void dialogDone(ActionEvent event) {
        if (contact != null) {
            fields.put("id", String.valueOf(contact.getIdNr()));
            TextField[] temp = new TextField[]{firstName, lastName, email, phoneNumber, company};
            for (TextField field :
                    temp) {
                if (field.getText() == null) {
                    System.out.println(field.getId() + " var tomt.");
                } else {
                    if (! field.getText().isEmpty())
                        fields.put(field.getId(), field.getText());
                }
            }
            System.out.println(fields.toString());
            SQLUpdate update = new SQLUpdate();
            update.update(fields);
            update.closeCon();
        } else {
            if (firstName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Kontakten måste åtminstone ha ett förnamn");
                alert.show();
            } else {
                fields.put("firstName", firstName.getText());
                fields.put("lastName", lastName.getText());
                fields.put("email", email.getText());
                fields.put("phoneNumber", phoneNumber.getText());
                fields.put("company", company.getText());
                SQLCreate create = new SQLCreate();
                create.create(
                        firstName.getText().trim(),
                        lastName.getText().trim(),
                        email.getText().trim(),
                        phoneNumber.getText().trim(),
                        company.getText().trim()
                );
                create.closeCon();
            }
        }
        Stage stage = (Stage) doneBtn.getScene().getWindow();
        stage.close();
    }
}
