import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GuiController {
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNr;
    public TextField company;
    public ChoiceBox relationChoiceBox;
    public Button searchBtn;
    public ListView addressBookListView;

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
            int id = 0;
            ObservableList<Contact> contacts = FXCollections.observableArrayList();
            contacts.add(new Contact(id++, firstName.getText(), lastName.getText(), email.getText(), phoneNr.getText(), "test"));

            addressBookListView = new ListView<>();
            addressBookListView.setCellFactory(listview -> new ListCell<Contact>(){
                @Override
                protected void updateItem(Contact item, boolean empty) {
                    super.updateItem(item, empty);

                    if(empty || item == null || item.getFirstName() == null) {
                        setText(null);
                    } else {
                        setText(item.getFirstName() + item.getLastName());
                    }
                }
            });

            addressBookListView.setItems(contacts);

            addressBookListView.setVisible(true);
        }
        addressBookListView.refresh();
    }
}
