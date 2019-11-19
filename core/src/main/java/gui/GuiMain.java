package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GuiMain extends Application {
    private static Locale locale = new Locale("sv","SE");
    static ResourceBundle bundle = ResourceBundle.getBundle("fxml.Bundle", locale);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addressbook.fxml"), bundle);
            Stage test = loader.load();
            test.initOwner(primaryStage);
            test.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
