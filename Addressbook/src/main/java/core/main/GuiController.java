package core.main;

import com.github.javafaker.Faker;
import core.singletons.ContactArrayContainer;
import core.singletons.ObjectPasser;
import dbWorker.SQLCreate;
import dbWorker.SQLDelete;
import dbWorker.SQLRead;
import dbWorker.SQLUndoLastDelete;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    @FXML private TreeItem<Object> results;
    @FXML private TextField searchField;
    @FXML private TreeView<Object> treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContactArrayContainer.getContacts().addListener((ListChangeListener<Contact>) change -> {
            for (Contact c :
                    ContactArrayContainer.getContacts()) {
                Button editBtn = new Button("Ändra");
                editBtn.setOnAction(event -> {
                    ObjectPasser.contact = c;
                    System.out.println(ObjectPasser.contact.fullInfo());
                    showContactDialog();
                });
                Button deleteBtn = new Button("Ta bort");
                deleteBtn.setOnAction(event -> {
                    ObjectPasser.contact = c;
                    System.out.println(ObjectPasser.contact.fullInfo());
                    handleDelete();
                });
                TreeItem<Object> info = new TreeItem<>(c.getFirstName() + " " + c.getLastName());
                info.getChildren().add(new TreeItem<>("Företag: " + c.getCompany()));
                info.getChildren().add(new TreeItem<>("Telefonnummer: " + c.getPhoneNumber()));
                info.getChildren().add(new TreeItem<>("E-mail: " + c.getEmail()));
                info.getChildren().add(new TreeItem<>(deleteBtn, editBtn));
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
        read.read(searchField.getText());
        read.closeCon();
    }

    @FXML
    protected void handleSearch(ActionEvent event) {
        treeView.getRoot().getChildren().clear();
        loadData();
    }

    @FXML
    private void showContactDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addnew.fxml"), MainGui.bundle);
        try {
            Parent parent = loader.load();
            treeView.getRoot().getChildren().clear();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setOnHiding(windowEvent -> loadData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete() {
        SQLDelete delete = new SQLDelete();
        delete.delete(ObjectPasser.contact);
        delete.closeCon();
        treeView.getRoot().getChildren().clear();
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
        treeView.getRoot().getChildren().clear();
        loadData();
    }

    @FXML private void addSampleContacts()  {
        SQLCreate creator = new SQLCreate();
        creator.init();
        for (int i = 0; i < 20; i++ ){
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            String company = faker.company().name();

            creator.create(firstName, lastName, email, phoneNumber, company);
            System.out.println("Skapade " + firstName + " " + lastName);
        }
        creator.closeCon();
        treeView.getRoot().getChildren().clear();
        loadData();
    }
}