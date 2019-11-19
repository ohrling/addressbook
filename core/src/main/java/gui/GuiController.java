package gui;

import dbWorker.SQLCreate;
import dbWorker.SQLRead;
import dbWorker.SQLUpdate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

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
    @FXML private VBox mainPane;

    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private Alert searchAlert = new Alert(Alert.AlertType.NONE);
    private Map<String,String> searchValues = null;
    private static Contact choosenContact = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    private void loadData() {
        // Laddar data från databasen baserat på searchValues
        contactsList.removeAll();
        addressBookListView.getItems().clear();
        contactsList.clear();

        SQLRead read = new SQLRead();
        setRightLabel(read.init());
        contactsList.addAll(read.read(searchValues));
        addressBookListView.setItems(contactsList);

        // Visar mer info när man klickar på post
        addressBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                choosenContact = addressBookListView.getSelectionModel().getSelectedItem();
                moreInfoTextArea.setText(choosenContact.fullInfo());
            }
        });
        setRightLabel(read.closeCon());
    }

    @FXML protected void handleSearch(ActionEvent event) {
        getSearchValues();
        if(searchValues.isEmpty()) {
            searchAlert.setAlertType(Alert.AlertType.ERROR);
            String content = "Ange vad du letar efter!";
            searchAlert.setContentText(content);
            searchAlert.show();
            searchValues = null;
            loadData();
        }
        else {
            loadData();
        }
    }

    @FXML protected void handleEdit(ActionEvent event) {
        SQLUpdate update = new SQLUpdate();
        try {
            setRightLabel(update.update(showContactDialog(false)));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        update.closeCon();
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
    protected void handleAddNewContact() {
        SQLCreate create = new SQLCreate();
        choosenContact = null;
        Map<String, String> values = showContactDialog(true);
        if(values == null || values.isEmpty()){
            // TODO: 2019-11-19 Ska något in här?
        } else {
            setRightLabel(
                create.create(
                    values.get("firstName"),
                    values.get("lastName"),
                    values.get("email"),
                    values.get("phoneNumber"),
                    values.get("company")
                )
            );
        }
        create.closeCon();
    }

    private Map<String,String> showContactDialog(boolean isNewContact) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/addnew.fxml"), GuiMain.bundle);
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            setRightLabel("Kunde inte ladda dialogrutan.");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.FINISH) {
            DialogController controller = fxmlLoader.getController();
            if(!isNewContact) {
                controller.setValues();
            }
            return controller.processInput();
        } else {
            setRightLabel("Åtgärd avbruten");
            return null;
        }
    }

    private void setRightLabel(String status) {
        rightLabel.setText(status);
    }

    static Contact getChoosenContact() {
        return choosenContact;
    }
}
