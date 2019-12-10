package core.main;

import com.github.javafaker.Faker;
import dbWorker.SQLCreate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGui extends Application {
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
            addSampleContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addSampleContacts()  {

        SQLCreate creator = new SQLCreate();
        for (int i = 0; i<100; i++ ){
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String phoneNumber = faker.phoneNumber().phoneNumber();
            String company = faker.company().name();

            creator.create(firstName, lastName, email, phoneNumber, company);
        }
    }
}

