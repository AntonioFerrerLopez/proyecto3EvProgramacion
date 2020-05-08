package VIEW.INSERTPENALTIES;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertPenalty implements Initializable {

    public AnchorPane AnchorAdvise;
    public JFXButton btnClose;
    public AnchorPane anchorPoliceCard;
    public TableView tvPoliceSelector;
    public TableColumn numPlateCol;
    public TableColumn nameCol;
    public AnchorPane anchorPenalty;
    public JFXButton btnAccept;
    public JFXButton btnClean;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void registerPenaltyOnDb(ActionEvent actionEvent) {
    }

    public void cleanAll(ActionEvent actionEvent) {
    }

    public void btnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
