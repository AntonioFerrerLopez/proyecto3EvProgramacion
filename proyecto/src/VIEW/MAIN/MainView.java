package VIEW.MAIN;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainView implements Initializable{

    public Label lbltoday;

    public void initialize(URL location, ResourceBundle resources) {
        lbltoday.setText("Valencia a " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    public void openViewInsertPenalty(ActionEvent actionEvent) {
        openModalWidow("../INSERTPENALTIES/InsertPenalty.fxml", "No se ha podido acceder a La lista de multas");
    }

    public void openViewListOfPenalties(ActionEvent actionEvent) {
        openModalWidow("../LISTPENALTIES/ListOfPenalties.fxml", "No se ha podido acceder a la Inserción de Multas ");
    }

    public void openViewDataPolice(ActionEvent actionEvent) {
        openModalWidow("../DATAPOLICE/DataPolice.fxml", "No se ha podido acceder a los datos policiales ");
    }

    public void endProgram(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void openModalWidow(String fxmlResource , String msjError ) {
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource(fxmlResource));
        try {
            Parent parentLoad = viewLoader.load();
            Scene viewScene = new Scene(parentLoad);
            Stage viewStage = new Stage();
            viewStage.initStyle(StageStyle.UNDECORATED);
            viewStage.initModality(Modality.APPLICATION_MODAL);
            viewStage.setScene(viewScene);
            viewStage.showAndWait();
        } catch (IOException e) {
            System.out.println(msjError);//TODO : Añadir Alert
        }
    }


}
