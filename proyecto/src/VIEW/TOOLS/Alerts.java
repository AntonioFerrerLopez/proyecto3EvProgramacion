package VIEW.TOOLS;

import javafx.scene.control.Alert;

public class Alerts {
    private  static Alerts instanceOf = null ;

    private Alerts(){ }

    public static Alerts instanceOf(){
        if(instanceOf == null ){
            instanceOf = new Alerts();
        }
        return instanceOf;
    }

    public void generateError(String msj) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setContentText(msj);
        error.showAndWait();
    }

    public  void generateWarningWithErrorCode(Integer errorCode , String msj  ) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Fallo");
        warning.setHeaderText("Código de error " + errorCode);
        warning.setContentText(msj);
        warning.showAndWait();
    }

    public void generateConfirmation(String msj) {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("INFORMACIÓN");
        confirmation.setHeaderText("");
        confirmation.setContentText(msj);
        confirmation.showAndWait();
    }
}
