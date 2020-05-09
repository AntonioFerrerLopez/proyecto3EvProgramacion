package VIEW.MAIN;

import DATA.DATABASE.DbConnector;
import VIEW.TOOLS.Alerts;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainView implements Initializable{

    public Label lbltoday;

    public void initialize(URL location, ResourceBundle resources) {
        lbltoday.setText("Valencia a " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    public void openViewInsertPenalty(ActionEvent actionEvent) {
        openModalWidow("../INSERTTRAFICFINES/InsertTraficFine.fxml", "No se ha podido acceder a La lista de multas");
    }

    public void openViewListOfPenalties(ActionEvent actionEvent) {
        openModalWidow("../LISTTRAFICFINE/ListOfTraficFines.fxml", "No se ha podido acceder a la Inserción de Multas ");
    }

    public void openViewDataPolice(ActionEvent actionEvent) {
        openModalWidow("../DATAPOLICE/DataPolice.fxml", "No se ha podido acceder a los datos policiales ");
    }

    public void endProgram(ActionEvent actionEvent) {
        finaliceProgram();
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
            e.printStackTrace();
            Alerts.instanceOf().generateError(msjError + e.getMessage());
            finaliceProgram();
        }
    }

    private void finaliceProgram() {



        Stage stage = (Stage) lbltoday.getScene().getWindow();
        stage.close();
    }

}
