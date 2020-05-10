package VIEW.LISTTRAFICFINE;


import DATA.DAO.PoliceDAO;
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

    public JFXComboBox<String> filterSelector;
    public JFXListView lvPolicesList;
    public TableView tableOfTraficFines;
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

    private ObservableList<TraficFineJoinPolice> finesListToPrint = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFilterSelector();
        setupLvPolicesList();
        setupTableOfTraficFines();

    }

    private void setupFilterSelector() {
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add(SELECT_NAME);
        options.add(SELECT_PLATE_NUMBER);
        filterSelector.setItems(options);
        filterSelector.getSelectionModel().selectFirst();
    }
    private void setupLvPolicesList() {
        ObservableList<String> listOfPolices = FXCollections.observableArrayList();
        lvPolicesList.setItems(listOfPolices);
        try {
            List<Police> policesFromDb = PoliceDAO.instanceOf().obtainAll();
            for(Police police : policesFromDb){
                if ((filterSelector.getSelectionModel().getSelectedItem().equals(SELECT_NAME))) {
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
        setupLvPolicesList();
    }

    public void hasSelection(MouseEvent mouseEvent) {

    }


    public void BackToMain(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();

    }

}
