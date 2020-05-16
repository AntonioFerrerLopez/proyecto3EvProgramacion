package VIEW.DATAPOLICE;

import DATA.DAO.FilesOfPoliceDAO;
import DATA.DAO.PoliceDAO;
import MODEL.Police;
import VIEW.TOOLS.Alerts;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class DataPolice {

    List<Police> policesOnFile = null;
    List<Police> policesNotInserted = null;
    public JFXButton btnClose;
    public JFXButton btnLoad;
    public AnchorPane rootPane;
    public JFXSnackbar snackInfo;

    public void loadAgents(ActionEvent actionEvent){
        FilesOfPoliceDAO  filesOfPoliceDAO = new FilesOfPoliceDAO();
        File file = obtainFilePathWhithFileChooser();
        if(file!= null){
           policesOnFile = filesOfPoliceDAO.setFilePath(file.getAbsolutePath());
           sendListToDAO();
        }else{
            generateSnackBar("ERROR AL CARGAR EL ARCHIVO");
        }
    }

    private void sendListToDAO() {
        try {
            PoliceDAO policeSql = PoliceDAO.instanceOf();
            policesNotInserted = policeSql.insertFromList(policesOnFile);
            compareListAndNotify();
        } catch (SQLException sqlError) {
            Alerts.instanceOf().generateWarningWithErrorCode(sqlError.getErrorCode(),sqlError.getMessage());
        }
    }

    private void compareListAndNotify() {
        if(policesNotInserted.size() <= 0){
            generateSnackBar("Se han insertado TODOS los registros.TOTAL: " + policesOnFile.size() );
        } else{
            Alerts.instanceOf().generateError(formatErrorNotInsertedList());
        }
    }

    private File obtainFilePathWhithFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./src/resources/PoliceData"));
        return fileChooser.showOpenDialog(null);
    }

    private String formatErrorNotInsertedList() {
        String msjError = "Hay " + policesNotInserted.size()  + " de " + policesOnFile.size() + " registros que NO SE HAN INSERTADO" + "\n";
        for(Police policeNotInserted : policesNotInserted){
          msjError = msjError.concat(policeNotInserted.getName() + "\n");
        }
        return msjError;
    }

    public void updatePhotoLink(ActionEvent actionEvent) {
        try {
            PoliceDAO.instanceOf().updatePhotoLinkAllPolices();
            generateSnackBar("Referencias de fotografías ACTUALIZADAS CORRECTAMENTE");
        } catch (SQLException errorSql) {
            Alerts.instanceOf().generateError("NO se han podido actualizar las referencias de las fotografías");
        }
    }

    private void generateSnackBar(String msj) {
        JFXSnackbar bar = new JFXSnackbar(rootPane);
        bar.show(msj , 3000);
    }

    public void closeView(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}