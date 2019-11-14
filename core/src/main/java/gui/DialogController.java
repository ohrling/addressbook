package gui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {

    @FXML private TextField shortDescription;
    @FXML private TextArea detailsArea;
    @FXML private DatePicker deadLinePicker;

    public void processInput() {
        String shortDesc = shortDescription.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadLinePicker.getValue();


    }
}
