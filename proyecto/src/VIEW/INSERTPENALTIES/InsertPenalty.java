package VIEW.INSERTPENALTIES;

import DATA.DAO.PoliceDAO;
import DATA.DAO.PoliceRelatedTraficFineDAO;
import MODEL.Police;
import MODEL.PoliceRelatedTraficFine;
import VIEW.TOOLS.Alerts;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InsertPenalty implements Initializable {

    public AnchorPane AnchorAdvise;
    public JFXButton btnClose;
    public AnchorPane anchorPoliceCard;
    public TableView<Police> tvPoliceSelector;
    public TableColumn numPlateCol;
    public TableColumn nameCol;
    public AnchorPane anchorPenalty;
    public JFXButton btnAccept;
    public JFXButton btnClean;
    public JFXTextField lblPoliceName;
    public JFXTextField lblPolicePlate;
    public JFXTextField lblPoliceDepartment;
    public ImageView imgPolice;
    public Label totalPenalty;
    public Spinner<Integer> priceSelector;
    public JFXCheckBox chDiscount;
    public JFXComboBox<String> cmbTypeOfPenalty;

    private List<Police> policesList;
    private List<PoliceRelatedTraficFine> traficFineTypesList;
    private Map<String,Double> fineTypesMap = new HashMap();
    private Double priceBase = 0.0 ;
    private Double totalPriceFine ;

    private final String POLICE_IMAGES_ROUTE = "src/resources/Images/policeImages/";
    private final String TYPE_JPG = ".jpg";
    private final String NO_IMAGE_POLICE = "dgpNoImage";
    private final String EURO_SYMBOL = " €";
    private final Integer DISCOUNT_PERCENTAGE = 20;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (obtainListOfPolicesAndTraficFineTypes()) {
            setupTableList();
            setupComboTraficFineTypes();
            setupFineTypesMap();
            priceBase = getfineTypeAmmount();
        } else {
            Alerts.instanceOf().generateError("La lista de policias no esta disponible");
        }
        anchorPoliceCard.setVisible(false);
        anchorPenalty.setVisible(false);

        SpinnerValueFactory.IntegerSpinnerValueFactory setUpSpinner =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100000,priceBase.byteValue(),1);
        priceSelector.setValueFactory(setUpSpinner);
        priceBase = priceSelector.getValue().doubleValue();
        totalPriceFine = priceBase;
        updateTotalPenaliy(totalPriceFine);
    }

    private boolean obtainListOfPolicesAndTraficFineTypes() {
        boolean listObtained = false;
        try {
            policesList = PoliceDAO.instanceOf().obtainAll();
            traficFineTypesList = PoliceRelatedTraficFineDAO.instanceOf().obtainAll();
            listObtained = true;
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(), errorSql.getMessage());
        }
        return listObtained;
    }

    private void setupTableList() {
        ObservableList<Police> observablePolicesList = FXCollections.observableArrayList();
        numPlateCol.setCellValueFactory(new PropertyValueFactory("policePlateNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        observablePolicesList.addAll(policesList);
        tvPoliceSelector.setItems(observablePolicesList);
    }

    private void setupComboTraficFineTypes() {
        ObservableList<String> observableTraficFineTypesList = FXCollections.observableArrayList();
        for(PoliceRelatedTraficFine traficFineType : traficFineTypesList){
            observableTraficFineTypesList.add(traficFineType.getDescription());
        }
        cmbTypeOfPenalty.setItems(observableTraficFineTypesList);
        cmbTypeOfPenalty.getSelectionModel().selectFirst();
    }

    private void setupFineTypesMap() {
        for (PoliceRelatedTraficFine trafilFineType : traficFineTypesList){
            fineTypesMap.put(trafilFineType.getDescription() , trafilFineType.getAmmount());
        }
    }
    private Double getfineTypeAmmount() {
        String valueSelected = cmbTypeOfPenalty.getSelectionModel().getSelectedItem();
        return fineTypesMap.get(valueSelected);
    }

    public void policeIsSelected(MouseEvent mouseEvent) {
        anyPoliceIsSelected();
    }

    public void policeSelectedByKeyboard(KeyEvent keyEvent) {
        anyPoliceIsSelected();
    }

    private void anyPoliceIsSelected() {
        if(!anchorPoliceCard.isVisible()){
            anchorPoliceCard.setVisible(true);
        }
        if(!anchorPenalty.isVisible()){
            anchorPenalty.setVisible(true);
        }
        Police policeSelected = this.tvPoliceSelector.getSelectionModel().getSelectedItem();
        fillPoliceCard(policeSelected);
    }

    public void typeOfFineSelected(ActionEvent actionEvent) {
       priceBase =  getfineTypeAmmount();
       updateTotalPenaliy(priceBase);
       checkCHState();
    }

    public void priceChangedClick(MouseEvent mouseEvent) {
        priceBase = priceSelector.getValue().doubleValue();
        updateTotalPenaliy(priceBase);
        checkCHState();
    }

    public void discountChanged(ActionEvent actionEvent) {
        checkCHState();
    }

    private void checkCHState() {
        if(chDiscount.isSelected()){
            totalPriceFine = priceBase-((priceBase * DISCOUNT_PERCENTAGE)/100);
            updateTotalPenaliy(totalPriceFine);
        }else{
            updateTotalPenaliy(priceBase);
        }
    }

    private void updateTotalPenaliy(Double priceBase) {
        totalPenalty.setText(priceBase + EURO_SYMBOL);
    }

    private void calculateTotalFinePrice() {
    }

    public void registerPenaltyOnDb(ActionEvent actionEvent) {
        //COMPROBAR TODOS LOS CAMPOS
        //ENVIAR A BBDD SI ESTAN OK
    }

    public void cleanAll(ActionEvent actionEvent) {
    }

    private void fillPoliceCard(Police policeSelected) {
        File imgPolice;
        lblPoliceName.setText(policeSelected.getName());
        lblPolicePlate.setText(policeSelected.getPolicePlateNumber());
        lblPoliceDepartment.setText(policeSelected.getDepartment());
        if(policeSelected.getPhotoLink() == null || policeSelected.getPhotoLink().equals("NULL")){
           imgPolice = new File( POLICE_IMAGES_ROUTE + NO_IMAGE_POLICE + TYPE_JPG);
        }else{
           imgPolice = new File( POLICE_IMAGES_ROUTE + policeSelected.getPhotoLink() + TYPE_JPG);
        }
       try{
           InputStream imgStream = new FileInputStream(imgPolice);
           this.imgPolice.setImage(new Image(imgStream));
       } catch (Exception e) {
            Alerts.instanceOf().generateError("Fallo al cargar la imagen del policia");
       }
    }

    public void btnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}