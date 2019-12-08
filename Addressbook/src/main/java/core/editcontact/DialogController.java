package core.editcontact;

import dbWorker.SQLCreate;
import dbWorker.SQLRead;
import dbWorker.SQLUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private Map<String, String> fields = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(contact != null) {
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            email.setText(contact.getEmail());
            phoneNumber.setText(contact.getPhoneNumber());
            company.setText(contact.getCompany());
        }
    }

    public void dialogCancel(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void dialogDone(ActionEvent event) {
        if(contact != null){
            fields.put("id", String.valueOf(contact.getIdNr()));
            TextField[] temp = new TextField[] {firstName, lastName, email, phoneNumber, company};
            for (TextField field :
                    temp) {
                if(field.getText() == null) {
                    System.out.println(field.getId() + " var tomt.");
                } else {
                    if(!field.getText().isEmpty())
                        fields.put(field.getId(), field.getText());
                }
            }
            System.out.println(fields.toString());
            SQLUpdate update = new SQLUpdate();
            update.update(fields);
            update.closeCon();
        } else {
            if(firstName.getText().isEmpty()) {
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
        // Refresh the Observable list
        // TODO: 2019-12-04 Vart ska denna vara för att både ändring och skapande ska fungera? Skapande ska varna när firstname saknas...
        //  Ska sedan skapad och ändrad contact hämtas från senast ändrad i db och visas i ObjectPasser?
        SQLRead read = new SQLRead();
        read.read(null);
        read.closeCon();
        Stage stage = (Stage) doneBtn.getScene().getWindow();
        stage.close();
    }
}
