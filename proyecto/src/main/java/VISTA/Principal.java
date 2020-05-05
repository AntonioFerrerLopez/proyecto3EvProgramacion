package VISTA;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Principal {

    public Label lbltoday;

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
