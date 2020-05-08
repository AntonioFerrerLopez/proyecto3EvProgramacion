package VIEW.INSERTPENALTIES;

import DATA.DAO.PoliceDAO;
import MODEL.Police;
import VIEW.TOOLS.Alerts;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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

    private List<Police> policesList ;
    private ObservableList<Police> observablePolicesList ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(obtainListOfPolicesOnDb()){
            setupTableList();
        }else{
            Alerts.instanceOf().generateError("La lista de policias no esta disponible");
        }
    }

    private void setupTableList() {
        observablePolicesList = FXCollections.observableArrayList();
        numPlateCol.setCellValueFactory(new PropertyValueFactory("policePlateNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        for (Police police: policesList){
            observablePolicesList.add(police);
        }
        tvPoliceSelector.setItems(observablePolicesList);
    }

    private boolean obtainListOfPolicesOnDb() {
        boolean listObteined = false;
        try {
            policesList = PoliceDAO.instanceOf().obtainAll();
            listObteined = true;
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode() ,errorSql.getMessage());
        }
        return listObteined;
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
