package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;
import model.SearchPerformer;

import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML private TextArea moreInfoTextArea;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNr;
    @FXML private TextField company;
    @FXML private ChoiceBox relationChoiceBox;
    @FXML private Button searchBtn;
    @FXML private ListView<Contact> addressBookListView;

    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private SearchPerformer searchPerformer;
    private Alert searchAlert = new Alert(Alert.AlertType.NONE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        contactsList.removeAll();
        //try {
            searchPerformer = new SearchPerformer();
            contactsList.addAll(new Contact(1,"Anders","Andersson","anders.andersson@siemens.se","0739922270","Siemens", true),
                    new Contact(2,"Simon","Simonsson","simon.simonsson@sony.se","0709842371","Sony", true),
                    new Contact(3,"Fadime","Molin","fadime.molin@volvo.se","0729242531","Volvo", true)
                    //searchPerformer.getAllContacts()
                    );
            addressBookListView.getItems().addAll(contactsList);

            addressBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    Contact contact = addressBookListView.getSelectionModel().getSelectedItem();
                    moreInfoTextArea.setText(contact.fullInfo());
                }
            });

        /*} catch (SQLException e) {
            e.printStackTrace();
            searchAlert.setAlertType(Alert.AlertType.ERROR);
            searchAlert.setContentText("Kunde inte ansluta till databasen!");
            searchAlert.show();
        }*/
    }

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
            loadData();
        }
    }
}
