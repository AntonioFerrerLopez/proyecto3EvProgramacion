package VIEW.LISTTRAFICFINE;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ListOfTraficFines implements Initializable {

    public JFXComboBox filterSelector;
    public JFXListView lvPolicesList;
    public TableView tableOfTraficFines;
    public TableColumn colDate;
    public TableColumn colPolice;
    public TableColumn colNifInfractor;
    public TableColumn colAmmount;
    public TableColumn colPointRetired;
    public Label lblTotalTraficFineAmmount;
    public JFXButton btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void filterChanged(ActionEvent actionEvent) {
    }


    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

    }
}
