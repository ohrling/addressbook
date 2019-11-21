package gui.main;

import dbWorker.SQLDelete;
import dbWorker.SQLRead;
import dbWorker.SQLUndoLastDelete;
import gui.singletons.MessageContainer;
import gui.singletons.ObjectPasser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

// Ritar upp och hanterar händelser i main-fönstret utifrån addressbook.fxml
public class GuiController implements Initializable {

    @FXML private Label rightLabel;
    @FXML private Label leftLabel;
    @FXML private TextArea moreInfoTextArea;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNr;
    @FXML private TextField company;
    @FXML private ListView<Contact> addressBookListView;

    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private Map<String,String> searchValues = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MessageContainer.getRightLabelMessage().addListener((observable, oldValue, newValue) -> rightLabel.setText(newValue));
        loadData();
    }

    private void loadData() {
        // Laddar data från databasen baserat på searchValues
        contactsList.removeAll();
        addressBookListView.getItems().clear();
        contactsList.clear();

        SQLRead read = new SQLRead();
        read.init(); // Initierar och skapar databasen om denne inte finns
        contactsList.addAll(read.read(searchValues));
        addressBookListView.setItems(contactsList);

        // Visar mer info när man klickar på post
        addressBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                ObjectPasser.contact = addressBookListView.getSelectionModel().getSelectedItem();
                moreInfoTextArea.setText(ObjectPasser.contact.fullInfo());
            }
        });
        read.closeCon();
    }

    @FXML protected void handleSearch(ActionEvent event) {
        getSearchValues();
        loadData();
    }

    private void getSearchValues() {
        // Genererar sökvärdena
        searchValues = new HashMap<>();
        if(!firstName.getText().isEmpty()) {
               searchValues.put("firstName", firstName.getText().trim());
        }
        if(!lastName.getText().isEmpty()){
            searchValues.put("lastName", lastName.getText().trim());
        }
        if(!email.getText().isEmpty()){
            searchValues.put("email", email.getText().trim());
        }
        if(!phoneNr.getText().isEmpty()) {
            searchValues.put("phoneNumber", phoneNr.getText().trim());
        }
        if(!company.getText().isEmpty()) {
            searchValues.put("company", company.getText().trim());
        }
    }

    @FXML
    private void showContactDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addnew.fxml"), GuiMain.bundle);
        try {
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        SQLDelete delete = new SQLDelete();
        delete.delete(ObjectPasser.contact);
        delete.closeCon();
        loadData();
    }

    @FXML
    private void addNewContact(ActionEvent event) {
        if(ObjectPasser.contact != null)
            ObjectPasser.contact = null;
        showContactDialog();
    }

    @FXML
    private void undoLastDelete(ActionEvent event) {
        SQLUndoLastDelete undoLast = new SQLUndoLastDelete();
        undoLast.undo();
        undoLast.closeCon();
        loadData();
    }
}