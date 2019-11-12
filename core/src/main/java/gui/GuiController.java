package gui;

import dbWorker.DataBaseConnector;
import dbWorker.DataLoader;
import dbWorker.GetSQLData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    @FXML private TextArea moreInfoTextArea;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private TextField phoneNr;
    @FXML private TextField company;
    @FXML private Button searchBtn;
    @FXML private ListView<Contact> addressBookListView;

    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private Alert searchAlert = new Alert(Alert.AlertType.NONE);
    private Map<String,String> searchValues = null;
    private DataBaseConnector db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db = new DataBaseConnector();
        loadData();
    }

    private void loadData() {
        contactsList.removeAll();
        contactsList.clear();
        try {
            db.init();
            Connection con = db.getConnection();
            DataLoader getData = new GetSQLData();
            contactsList.addAll(getData.getData(con, searchValues));
            addressBookListView.getItems().addAll(contactsList);

            // Visar mer info när man klickar på post
            addressBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    Contact contact = addressBookListView.getSelectionModel().getSelectedItem();
                    moreInfoTextArea.setText(contact.fullInfo());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            searchAlert.setAlertType(Alert.AlertType.ERROR);
            searchAlert.setContentText("Kunde inte ansluta till databasen!");
            searchAlert.show();
        }
    }

    @FXML protected void handleSearch(ActionEvent event) {
        // TODO: 2019-11-04 Tillfälligt, för att visa att sökvärdena är tomma
        getSearchValues();
        if(searchValues.isEmpty()) {
            searchAlert.setAlertType(Alert.AlertType.ERROR);
            String content = "Ange vad du letar efter!";
            searchAlert.setContentText(content);
            searchAlert.show();
        }
        else {
            loadData();
        }
    }

    private void getSearchValues() {
        searchValues = new HashMap<>();
        if(!firstName.getText().isEmpty()) {
               searchValues.put("firstName", firstName.getText());
        }
        if(!lastName.getText().isEmpty()){
            searchValues.put("lastName", lastName.getText());
        }
        if(!email.getText().isEmpty()){
            searchValues.put("email", email.getText());
        }
        if(!phoneNr.getText().isEmpty()) {
            searchValues.put("phoneNumber", phoneNr.getText());
        }
        if(!company.getText().isEmpty()) {
            searchValues.put("company", company.getText());
        }
    }
}
