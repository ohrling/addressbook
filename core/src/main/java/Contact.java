import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class Contact {
    private IntegerProperty idNr;
    private StringProperty firstName, lastName, email, phoneNr, relation;

    public Contact(int id, String firstName, String lastName, String email, String phoneNr, String relation) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNr(phoneNr);
        setRelation(relation);
    }

    public static Callback<Contact, Observable[]> extractor() {
        return param -> new Observable[]{param.idNr, param.firstName};
    }

    private void setId(int id) {
        idProperty().set(id);
    }

    public int getId() {
        return idNr.get();
    }

    public IntegerProperty idProperty() {
        if(idNr == null)
            idNr = new SimpleIntegerProperty(this, "idNr");
        return idNr;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        if(firstName == null)
            firstName = new SimpleStringProperty(this, "firstName");
        return firstName;
    }

    public void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        if(lastName == null)
            lastName = new SimpleStringProperty(this, "lastName");
        return lastName;
    }

    public void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        if(email == null)
            email = new SimpleStringProperty(this, "email");
        return email;
    }

    public void setEmail(String email) {
        emailProperty().set(email);
    }

    public String getPhoneNr() {
        return phoneNr.get();
    }

    public StringProperty phoneNrProperty() {
        if(phoneNr == null)
            phoneNr = new SimpleStringProperty(this, "phoneNr");
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        phoneNrProperty().set(phoneNr);
    }

    public String getRelation() {
        return relation.get();
    }

    public StringProperty relationProperty() {
        if(relation == null)
            relation = new SimpleStringProperty(this, "relation");
        return relation;
    }

    public void setRelation(String relation) {
        relationProperty().set(relation);
    }
}
