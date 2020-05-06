import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("VIEW/MAIN/MainView.fxml"));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("./resources/Images/dgpLogo.jpg")));
        primaryStage.setTitle("GESTOR DE MULTAS Y DATOS POLICIALES");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
