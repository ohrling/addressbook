package core.main;

import core.singletons.ContactArrayContainer;
import core.singletons.ObjectPasser;
import dbWorker.SQLDelete;
import dbWorker.SQLRead;
import dbWorker.SQLUndoLastDelete;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Ritar upp och hanterar händelser i main-fönstret utifrån addressbook.fxml
public class GuiController implements Initializable {

    @FXML private TextField searchField;
    @FXML private TreeView<String> treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> results = new TreeItem<>("Kontakter");

        ContactArrayContainer.getContacts().addListener((ListChangeListener<Contact>) change -> {
            for (Contact c :
                    ContactArrayContainer.getContacts()) {
                System.out.println(c.fullInfo());
                TreeItem<String> info = new TreeItem<>(c.getFirstName() + " " + c.getLastName());
                info.getChildren().add(new TreeItem<>("Företag: " + c.getCompany()));
                info.getChildren().add(new TreeItem<>("Telefonnummer: " + c.getPhoneNumber()));
                info.getChildren().add(new TreeItem<>("E-mail: " + c.getEmail()));
                results.getChildren().add(info);
            }
            treeView.setRoot(results);
        });
        loadData();
    }

    private void loadData() {
        SQLRead read = new SQLRead();
        // Initierar och skapar databasen om denne inte finns
        read.init();
        read.genericDatabaseSearch(searchField.getText());
/*
        // Visar mer info när man klickar på post
        addressBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                ObjectPasser.contact = addressBookListView.getSelectionModel().getSelectedItem();
                moreInfoFirstNameLabelInfo.setText(ObjectPasser.contact.getFirstName());
                moreInfoLastNameLabelInfo.setText(ObjectPasser.contact.getLastName());
                moreInfoEmailLabelInfo.setText(ObjectPasser.contact.getEmail());
                moreInfoPhoneNrLabelInfo.setText(ObjectPasser.contact.getPhoneNumber());
                moreInfoCompanyLabelInfo.setText(ObjectPasser.contact.getCompany());
            }
        });*/
        read.closeCon();
    }

    @FXML protected void handleSearch(ActionEvent event) {
        treeView.setRoot(null);
        loadData();
    }

    @FXML
    private void showContactDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addnew.fxml"), MainGui.bundle);
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

    public void closeApplication(ActionEvent event) {
        Platform.exit();
    }
}