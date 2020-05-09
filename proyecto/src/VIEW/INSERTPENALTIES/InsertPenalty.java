package VIEW.INSERTPENALTIES;

import DATA.DAO.PoliceDAO;
import DATA.DAO.TraficFineTypesDAO;
import DATA.DAO.TraficFineDAO;
import MODEL.Police;
import MODEL.TraficFineTypes;
import MODEL.TraficFine;
import VIEW.TOOLS.Alerts;
import com.jfoenix.controls.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Spinner<Double> priceSelector;
    public JFXCheckBox chDiscount;
    public JFXComboBox<String> cmbTypeOfPenalty;
    public JFXDatePicker dateChooser;
    public JFXTextField infractorsName;
    public JFXTextField infractionDescription;
    public JFXTextField infractorsNif;
    public AnchorPane anchorPolice;
    public JFXComboBox cmDepartmentFilter;


    private List<Police> policesList;
    ObservableList<Police> observablePolicesList;
    Police policeSelected;
    private List<TraficFineTypes> traficFineTypesList;
    private Map<String,Double> fineTypesMap = new HashMap();
    private Double priceBase = 0.0 ;
    private Double totalPriceFine ;

    private static final String NO_FILTER = "Todos";
    private final String POLICE_IMAGES_ROUTE = "src/resources/Images/policeImages/";
    private final String TYPE_JPG = ".jpg";
    private final String NO_IMAGE_POLICE = "dgpNoImage";
    private final String EURO_SYMBOL = " €";
    private final Integer DISCOUNT_PERCENTAGE = 20;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (obtainListOfPolicesAndTraficFineTypes()) {
            setupTableList();
            setupCombos();
            setupFineTypesMap();
            priceBase = getfineTypeAmmount();
        } else {
            Alerts.instanceOf().generateError("La lista de policias no esta disponible");
        }
        anchorPoliceCard.setVisible(false);
        anchorPenalty.setVisible(false);

        SpinnerValueFactory.DoubleSpinnerValueFactory setUpSpinner =  new SpinnerValueFactory.DoubleSpinnerValueFactory(1,100000,priceBase,1);
        priceSelector.setValueFactory(setUpSpinner);
        priceBase = priceSelector.getValue().doubleValue();
        totalPriceFine = priceBase;
        updateTotalPenaliy(totalPriceFine);
    }

    private boolean obtainListOfPolicesAndTraficFineTypes() {
        boolean listObtained = false;
        try {
            policesList = PoliceDAO.instanceOf().obtainAll();
            traficFineTypesList = TraficFineTypesDAO.instanceOf().obtainAll();
            listObtained = true;
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(), errorSql.getMessage());
        }
        return listObtained;
    }

    private void setupCombos() {
        ObservableList<String> observableTraficFineTypesList = FXCollections.observableArrayList();
        ObservableList<String> observableDepartments = FXCollections.observableArrayList();
        observableDepartments.add(NO_FILTER);
        for(TraficFineTypes traficFineType : traficFineTypesList){
            observableTraficFineTypesList.add(traficFineType.getDescription());
        }
        for(Police policeOnList : policesList){
            if(!observableDepartments.contains(policeOnList.getDepartment())){
                observableDepartments.add(policeOnList.getDepartment());
            }
        }
        cmbTypeOfPenalty.setItems(observableTraficFineTypesList);
        cmbTypeOfPenalty.getSelectionModel().selectFirst();
        cmDepartmentFilter.setItems(observableDepartments);
        cmDepartmentFilter.getSelectionModel().selectFirst();
    }
    private void setupTableList() {
        observablePolicesList = FXCollections.observableArrayList();
        numPlateCol.setCellValueFactory(new PropertyValueFactory("policePlateNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        chargeTableListOfPolices();
    }

    public void departmentFilterChanged(ActionEvent actionEvent) {
        String departmentSelected = cmDepartmentFilter.getSelectionModel().getSelectedItem().toString();
        try {
            if(departmentSelected.equals(NO_FILTER)){
                    policesList = PoliceDAO.instanceOf().obtainAll();
                    chargeTableListOfPolices();
            }else {
                policesList = PoliceDAO.instanceOf().obtainListByDepartment(departmentSelected);
                chargeTableListOfPolices();
            }
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode(), errorSql.getMessage());
        }
    }

    private void chargeTableListOfPolices() {
        observablePolicesList.clear();
        observablePolicesList.setAll(policesList);
        tvPoliceSelector.setItems(observablePolicesList);
        tvPoliceSelector.refresh();

    }

    private void setupFineTypesMap() {
        for (TraficFineTypes trafilFineType : traficFineTypesList){
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
        policeSelected = this.tvPoliceSelector.getSelectionModel().getSelectedItem();
        fillPoliceCard(policeSelected);
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
            try{
             imgPolice = new File( POLICE_IMAGES_ROUTE + NO_IMAGE_POLICE + TYPE_JPG);
             InputStream imgStream = new FileInputStream(imgPolice);
             this.imgPolice.setImage(new Image(imgStream));
            }catch (Exception imgError){
                Alerts.instanceOf().generateError("Fallo al cargar la imagen del policia");
            }
        }
    }

    public void typeOfFineSelected(ActionEvent actionEvent) {
       priceBase =  getfineTypeAmmount();
       updateTotalPenaliy(priceBase);
       priceSelector.getValueFactory().setValue(priceBase);
       checkifDiscountState();
    }

    public void priceChangedClick(MouseEvent mouseEvent) {
        priceBase = priceSelector.getValue().doubleValue();
        updateTotalPenaliy(priceBase);
        checkifDiscountState();
    }

    public void discountChanged(ActionEvent actionEvent) {
        checkifDiscountState();
    }

    private void checkifDiscountState() {
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

    public void registerPenaltyOnDb(ActionEvent actionEvent) {
        if(validateAllFields()){
            try {
               TraficFine trf=  generateTraficFineObject();
                System.out.println(trf);
                TraficFineDAO.instanceOf().insert(trf);
                Alerts.instanceOf().generateConfirmation("MULTA REGISTRADA EN SISTEMA");
                cleanAll(actionEvent);
            } catch (SQLException errorSql) {
                Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode() , errorSql.getMessage());
            }

        }else{
            Alerts.instanceOf().generateError("Fallo al registrar la multa. Revise los campos");
        }
    }

    private boolean validateAllFields() {
        return dateChooser.getValue() != null &&
                !infractorsName.getText().equals("") &&
                !infractionDescription.getText().equals("") &&
                !infractorsNif.getText().equals("");
    }

    private TraficFine generateTraficFineObject() {
        Long fineId = null;
        LocalDateTime localDateFine = dateChooser.getValue().atTime(LocalTime.now());
        try {
            String  typeSelected = cmbTypeOfPenalty.getSelectionModel().getSelectedItem();
            fineId = TraficFineTypesDAO.instanceOf().obtainIdFirterByDescription(typeSelected);
            System.out.println(fineId + "ID DE RETORNO");
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateWarningWithErrorCode(errorSql.getErrorCode() , errorSql.getMessage());
        }

        return new TraficFine(
                infractionDescription.getText(),
                localDateFine,
                totalPriceFine,
                policeSelected.getId(),
                infractorsNif.getText(),
                fineId);
    }

    public void cleanAll(ActionEvent actionEvent) {
            dateChooser.setValue(LocalDate.now());
            infractorsName.setText("");
            infractionDescription.setText("");
            infractorsNif.setText("");
    }

    public void btnClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    public void userSelectsDate(ActionEvent actionEvent) {
        if (dateChooser.getValue().equals(LocalDate.now().plusDays(1))) {
            Alerts.instanceOf().generateError("No Puede seleccionar una fecha posterior a la actual");
           dateChooser.setValue(LocalDate.now());
        }

    }

}