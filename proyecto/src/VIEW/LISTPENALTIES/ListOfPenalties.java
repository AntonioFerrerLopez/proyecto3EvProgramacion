package VIEW.LISTPENALTIES;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ListOfPenalties {
    public void closeView(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
