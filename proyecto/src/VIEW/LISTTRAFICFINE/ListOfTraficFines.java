package VIEW.LISTTRAFICFINE;

import DATA.DAO.PoliceDAO;
import DATA.DAO.TraficFineDAO;
import MODEL.Police;
import MODEL.TraficFine;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private List<TraficFine> traficFineList;
    private ObservableList<String> observableListView ;
    private ObservableList<TraficFine> observableTableview = FXCollections.observableArrayList();
    private String filterSelected ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableList();
        setupFilterSelector();
        filterSelector.getSelectionModel().selectFirst();
        filterSelected = filterSelector.getSelectionModel().getSelectedItem();
        obtainListOfPolicesAndTraficFines();
        chargePolicesListInOrder(filterSelected);
    }

    private void setupTableList() {
        observableListView = FXCollections.observableArrayList();
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colPolice.setCellValueFactory(new PropertyValueFactory("idPolice"));
        colNifInfractor.setCellValueFactory(new PropertyValueFactory("nifInfractor"));
        colAmmount.setCellValueFactory(new PropertyValueFactory("ammount"));
        colPointRetired.setCellValueFactory(new PropertyValueFactory("description"));
    }

    private void setupFilterSelector() {
        ObservableList<String> listoOfFilterKeys = FXCollections.observableArrayList();
        listoOfFilterKeys.add(SELECT_NAME);
        listoOfFilterKeys.add(SELECT_PLATE_NUMBER);
        filterSelector.setItems(listoOfFilterKeys);
    }

    private void obtainListOfPolicesAndTraficFines() {
        try {
            policesList = PoliceDAO.instanceOf().obtainAll();
            traficFineList = TraficFineDAO.instanceOf().obtainAll();
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(),errorSql.getMessage());
        }
    }


    private void chargePolicesListInOrder(String filter) {
        observableListView.clear();
        if(filter == SELECT_NAME) {
            Collections.sort(policesList);
            for (Police policeOnList : policesList ){
                observableListView.add(policeOnList.getName());
            }
        }
        if(filter == SELECT_PLATE_NUMBER){
            observableListView.clear();
            for (Police policeOnList : policesList ){
                observableListView.add(policeOnList.getPolicePlateNumber());
            }
        }
        lvPolicesList.setItems(observableListView);
    }

    public void filterChanged(ActionEvent actionEvent) {
        filterSelected = filterSelector.getSelectionModel().getSelectedItem();
        chargePolicesListInOrder(filterSelected);
    }

    public void hasSelection(MouseEvent mouseEvent) {
       List<TraficFine> listToUpdateTable = filterTraficList(lvPolicesList.getSelectionModel().getSelectedItem().toString());
        updateTableOfTraficFines(listToUpdateTable);
    }

    private List<TraficFine> filterTraficList(String filter) {
        for(TraficFine fine : traficFineList){

        }
        return null;
    }

    private void updateTableOfTraficFines(List<TraficFine> listOfFinesFiltered) {
         observableTableview.clear();
        for(TraficFine fine : listOfFinesFiltered) {
          observableTableview.add(fine);
        }
        tableOfTraficFines.setItems(observableTableview);

    }

    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

    }

}
