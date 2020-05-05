package VISTA;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Principal implements Initializable{

    public Label lbltoday;

    public void initialize(URL location, ResourceBundle resources) {
        lbltoday.setText("Valencia a " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    public void openViewInsertPenalty(ActionEvent actionEvent) {
    }

    public void openViewListOfPenalties(ActionEvent actionEvent) {
    }

    public void openViewDataPolice(ActionEvent actionEvent) {
    }

    public void endProgram(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
