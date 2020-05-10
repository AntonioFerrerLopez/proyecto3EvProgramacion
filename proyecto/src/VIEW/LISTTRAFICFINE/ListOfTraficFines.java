package VIEW.LISTTRAFICFINE;


import DATA.DAO.PoliceDAO;
import DATA.DAO.TraficFineJoinPoliceDAO;
import MODEL.Police;
import MODEL.TraficFineJoinPolice;
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
import java.util.List;
import java.util.ResourceBundle;

public class ListOfTraficFines implements Initializable {

    private static final String SELECT_ALL = "Todos";
    public JFXComboBox<String> filterSelector;
    public JFXListView<String> lvPolicesList;
    public TableView<TraficFineJoinPolice> tableOfTraficFines;
    public TableColumn colDate;
    public TableColumn colInfractorNif;
    public TableColumn colInfraction;
    public TableColumn colInfDescription;
    public TableColumn colAmount;
    public TableColumn colCardPoints;
    public TableColumn colPoliceName;
    public TableColumn colPolicePlate;
    public Label lblTotalTraficFineAmmount;
    public JFXButton btnBack;

    private static final String SELECT_NAME = "NOMBRE" ;
    private static final String SELECT_PLATE_NUMBER = "NUMERO PLACA" ;

    private List<TraficFineJoinPolice> allFinesOnDb;
    private ObservableList<String> listOfPolices = FXCollections.observableArrayList();
    private ObservableList<TraficFineJoinPolice> finesListToPrint = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             allFinesOnDb = TraficFineJoinPoliceDAO.instanceOf().obtainAllFinesRelatedPolices();
             finesListToPrint.setAll(allFinesOnDb);
             tableOfTraficFines.setItems(finesListToPrint);
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(),errorSql.getMessage());
        }
        setupFilterSelector();
        updateLvPolicesList();
        setupTableOfTraficFines();
        lvPolicesList.setDisable(true);

    }

    private void setupFilterSelector() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add(SELECT_ALL);
        options.add(SELECT_NAME);
        options.add(SELECT_PLATE_NUMBER);
        filterSelector.setItems(options);
        filterSelector.getSelectionModel().selectFirst();

    }

    private void setupTableOfTraficFines() {
          colDate.setCellValueFactory(new PropertyValueFactory("dateFine"));
          colInfractorNif.setCellValueFactory(new PropertyValueFactory("nifInfractorFine"));
          colInfraction.setCellValueFactory(new PropertyValueFactory("descriptionType"));
          colInfDescription.setCellValueFactory(new PropertyValueFactory("descriptionFine"));
          colAmount.setCellValueFactory(new PropertyValueFactory("ammountFine"));
          colCardPoints.setCellValueFactory(new PropertyValueFactory("drivingCardPoints"));
          colPoliceName.setCellValueFactory(new PropertyValueFactory("policeName"));
          colPolicePlate.setCellValueFactory(new PropertyValueFactory("policePlateNumber"));
    }


    public void filterChanged(ActionEvent actionEvent) {
        String filter = filterSelector.getSelectionModel().getSelectedItem();
        if(filter.equals(SELECT_ALL)){
            lvPolicesList.setDisable(true);
            finesListToPrint.setAll(allFinesOnDb);
            tableOfTraficFines.refresh();
        }
        if(filter.equals(SELECT_NAME) || filter.equals(SELECT_PLATE_NUMBER) ){
            lvPolicesList.setDisable(false);
            updateLvPolicesList();
        }
    }

    private void updateLvPolicesList() {
        listOfPolices = FXCollections.observableArrayList();
        lvPolicesList.setItems(listOfPolices);
        try {
            List<Police> policesFromDb = PoliceDAO.instanceOf().obtainAll();
            for(Police police : policesFromDb){
                if (filterSelector.getSelectionModel().getSelectedItem().equals(SELECT_NAME) || filterSelector.getSelectionModel().getSelectedItem().equals(SELECT_ALL)) {
                    listOfPolices.add(police.getName());
                } else {
                    listOfPolices.add(police.getPolicePlateNumber());
                }
            }
            lvPolicesList.refresh();
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(),errorSql.getMessage());
        }
    }

    public void hasSelection(MouseEvent mouseEvent) {
        filterAndUpdateBySelection(filterSelector.getSelectionModel().getSelectedItem());
    }

    private void filterAndUpdateBySelection(String selectedItem) {
        String typeOfSelection = filterSelector.getSelectionModel().getSelectedItem();
        finesListToPrint.clear();
        for(TraficFineJoinPolice fine : allFinesOnDb){
            if(typeOfSelection.equals(SELECT_NAME)){
                if(fine.getPoliceName().equals(lvPolicesList.getSelectionModel().getSelectedItem())) finesListToPrint.add(fine);
            }
            if(typeOfSelection.equals(SELECT_PLATE_NUMBER)){
                if(fine.getPolicePlateNumber().equals(lvPolicesList.getSelectionModel().getSelectedItem())) finesListToPrint.add(fine);
            }
        }
       tableOfTraficFines.refresh();
    }

    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

    }

}
