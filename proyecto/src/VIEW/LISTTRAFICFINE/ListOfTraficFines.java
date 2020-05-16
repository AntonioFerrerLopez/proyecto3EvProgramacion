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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
    private final ObservableList<TraficFineJoinPolice> finesListToPrint = FXCollections.observableArrayList();
    private Double totalAmount = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             allFinesOnDb = formatDate(TraficFineJoinPoliceDAO.instanceOf().obtainAllFinesRelatedPolices());
             finesListToPrint.setAll(allFinesOnDb);
             tableOfTraficFines.setItems(finesListToPrint);
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(),errorSql.getMessage());
        }
        setupFilterSelector();
        updateLvPolicesList();
        setupTableOfTraficFines();
        lvPolicesList.setDisable(true);
        calculateTotalAmountAllFines();
        lvPolicesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private List<TraficFineJoinPolice> formatDate(List<TraficFineJoinPolice> obtainAllFinesRelatedPolices) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        for(TraficFineJoinPolice fine : obtainAllFinesRelatedPolices){
            fine.setDateFineFormatted(fine.getDateFine().format(formatter));
        }
        return obtainAllFinesRelatedPolices;
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
          colDate.setCellValueFactory(new PropertyValueFactory("dateFineFormatted"));
          colInfractorNif.setCellValueFactory(new PropertyValueFactory("nifInfractorFine"));
          colInfraction.setCellValueFactory(new PropertyValueFactory("descriptionType"));
          colInfDescription.setCellValueFactory(new PropertyValueFactory("descriptionFine"));
          colAmount.setCellValueFactory(new PropertyValueFactory("ammountFine"));
          colCardPoints.setCellValueFactory(new PropertyValueFactory("drivingCardPoints"));
          colPoliceName.setCellValueFactory(new PropertyValueFactory("policeName"));
          colPolicePlate.setCellValueFactory(new PropertyValueFactory("policePlateNumber"));
          tableOfTraficFines.getSortOrder().add(colPoliceName);
    }

    public void filterChanged(ActionEvent actionEvent) {
        String filter = filterSelector.getSelectionModel().getSelectedItem();
        totalAmount = 0.0;
        if(filter.equals(SELECT_ALL)){
            lvPolicesList.setDisable(true);
            finesListToPrint.setAll(allFinesOnDb);
            tableOfTraficFines.refresh();
            calculateTotalAmountAllFines();
        }
        if(filter.equals(SELECT_NAME) || filter.equals(SELECT_PLATE_NUMBER) ){
            lvPolicesList.setDisable(false);
            updateLvPolicesList();
        }
    }

    private void updateLvPolicesList() {
        ObservableList<String> listOfPolices = FXCollections.observableArrayList();
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
        totalAmount = 0.0;
        filterAndUpdateBySelection();
    }

    private void calculateTotalAmountAllFines() {
        for(TraficFineJoinPolice fineOnDb : allFinesOnDb){
            updateTotalFineAmount(fineOnDb.getAmountFine());
        }
        lblTotalTraficFineAmmount.setText(totalAmount + " €");
    }

    private void filterAndUpdateBySelection() {
        String typeOfSelection = filterSelector.getSelectionModel().getSelectedItem();
        finesListToPrint.clear();
        for(TraficFineJoinPolice fine : allFinesOnDb){
            if(typeOfSelection.equals(SELECT_NAME)){
                if(existsInList(fine)){
                    finesListToPrint.add(fine);
                    updateTotalFineAmount(fine.getAmountFine());
                }
            }
            if(typeOfSelection.equals(SELECT_PLATE_NUMBER)){
                if(existsInList(fine)){
                    finesListToPrint.add(fine);
                    updateTotalFineAmount(fine.getAmountFine());
                }
            }
        }
        lblTotalTraficFineAmmount.setText(totalAmount + " €");
        tableOfTraficFines.getSortOrder().add(colPoliceName);
        tableOfTraficFines.refresh();
    }

    private boolean existsInList(TraficFineJoinPolice fine){
        ObservableList<String> selection = lvPolicesList.getSelectionModel().getSelectedItems();
        return (selection.contains(fine.getPoliceName()) || selection.contains(fine.getPolicePlateNumber()) );
    }

    private void updateTotalFineAmount(Double amount) {
        totalAmount += amount;
    }

    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
