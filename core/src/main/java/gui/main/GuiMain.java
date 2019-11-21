package gui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GuiMain extends Application {
    private static final Locale locale = new Locale("sv","SE");
    static final ResourceBundle bundle = ResourceBundle.getBundle("fxml.Bundle", locale); // För att kunna använda olika språk i applikationen

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addressbook.fxml"), bundle);
            Stage main = loader.load();
            main.initOwner(primaryStage);
            main.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
