import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiController {
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNr;
    public TextField company;
    public ChoiceBox relationChoiceBox;
    public Button searchBtn;
    public TableView<Contact> addressBookTableView = new TableView<>();

    private Alert searchAlert = new Alert(Alert.AlertType.NONE);

    @FXML
    protected void handleSearch(ActionEvent event) {
        // TODO: 2019-11-04 Tillfälligt, för att visa att sökvärdena är tomma
        if(firstName.getText().isEmpty() && lastName.getText().isEmpty() && email.getText().isEmpty() && phoneNr.getText().isEmpty() && company.getText().isEmpty()) {
            searchAlert.setAlertType(Alert.AlertType.ERROR);
            String content = "Ange vad du letar efter!";
            searchAlert.setContentText(content);
            searchAlert.show();
        }
        else {
            // TODO: 2019-11-04 Tillfälliga fält
            int id = 1;
            ObservableList<Contact> contacts = FXCollections.observableArrayList(Contact.extractor());
            addressBookTableView.setItems(contacts);
            Contact contact = new Contact(id, firstName.getText(), lastName.getText(), email.getText(), phoneNr.getText(), "test");
            contacts.add(contact);
            contact.setLastName("Pettersson");
            TableColumn<Contact,String> lastNameCol = new TableColumn<Contact,String>("Last Name");
            lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        }
        addressBookTableView.refresh();
    }
}
