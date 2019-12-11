package core.singletons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.util.List;

public class ContactArrayContainer {
    private static ContactArrayContainer instance = null;
    private static final ObservableList<Contact> contacts = FXCollections.observableArrayList();

    public static ContactArrayContainer getInstance() {
        if(instance == null)
            instance = new ContactArrayContainer();
        return instance;
    }

    public static void setContactsList(List<Contact> contactsList) {
        contacts.setAll(contactsList);
    }

    public static ObservableList<Contact> getContacts() {
        return contacts;
    }
}
