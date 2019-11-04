import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("addressbook.fxml"));
        primaryStage.setTitle("Adressbok v0.3");
        primaryStage.setScene(new Scene(root,900,600));
        primaryStage.show();
    }
}
