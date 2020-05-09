package VIEW.LISTTRAFICFINE;

import DATA.DAO.PoliceDAO;
import MODEL.Police;
import VIEW.TOOLS.Alerts;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ListOfTraficFines implements Initializable {

    public JFXComboBox<String> filterSelector;
    public JFXListView lvPolicesList;
    public TableView tableOfTraficFines;
    public TableColumn colDate;
    public TableColumn colPolice;
    public TableColumn colNifInfractor;
    public TableColumn colAmmount;
    public TableColumn colPointRetired;
    public Label lblTotalTraficFineAmmount;
    public JFXButton btnBack;
    private static final String SELECT_NAME = "NOMBRE" ;
    private static final String SELECT_PLATE_NUMBER = "NUMERO PLACA" ;
    private List<Police> policesList;
    private ObservableList<String> listForLv = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupFilterSelector();
        filterSelector.getSelectionModel().selectFirst();
        obtainListOfPolices();
        chargePolicesListFiltered(filterSelector.getSelectionModel().getSelectedItem());
    }

    private void setupFilterSelector() {
        ObservableList<String> listoOfFilterKeys = FXCollections.observableArrayList();
        listoOfFilterKeys.add(SELECT_NAME);
        listoOfFilterKeys.add(SELECT_PLATE_NUMBER);
        filterSelector.setItems(listoOfFilterKeys);
    }

    private void obtainListOfPolices() {
        try {
            policesList = PoliceDAO.instanceOf().obtainAll();
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(),errorSql.getMessage());
        }
    }

    private void chargePolicesListFiltered(String filter) {
        if(filter == SELECT_NAME) {
            Collections.sort(policesList);
            listForLv.clear();
            for (Police policeOnList : policesList ){
                listForLv.add(policeOnList.getName());
            }
        }
        if(filter == SELECT_PLATE_NUMBER){
            listForLv.clear();
            for (Police policeOnList : policesList ){
                listForLv.add(policeOnList.getPolicePlateNumber());
            }
        }
        lvPolicesList.setItems(listForLv);
    }


    public void filterChanged(ActionEvent actionEvent) {
        chargePolicesListFiltered(filterSelector.getSelectionModel().getSelectedItem());
    }


    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

    }
}
