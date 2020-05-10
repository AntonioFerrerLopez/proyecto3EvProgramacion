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
    public TableColumn colPolice;
    public TableColumn colNifInfractor;
    public TableColumn colAmmount;
    public TableColumn colPointRetired;
    public Label lblTotalTraficFineAmmount;
    public JFXButton btnBack;
    
    private static final String SELECT_NAME = "NOMBRE" ;
    private static final String SELECT_PLATE_NUMBER = "NUMERO PLACA" ;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFilterSelector();
        setupLvPolicesList();
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
