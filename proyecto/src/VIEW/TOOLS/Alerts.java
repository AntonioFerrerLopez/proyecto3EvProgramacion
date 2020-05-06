package VIEW.TOOLS;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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
        Alert warning = new Alert(Alert.AlertType.ERROR);
        warning.setContentText(msj);
        warning.showAndWait();
    }

    public  void generateWarningWithErrorCode(Integer errorCode , String msj  ) {
        Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Fallo");
        warning.setHeaderText("Código de error " + errorCode);
        warning.setContentText(msj);
        warning.showAndWait();
    }

    public void generateConfirmation(String msj) {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setContentText(msj);
        warning.showAndWait();
    }

    public boolean generateConfirmation(String msjAction, String msjQuery){
        boolean confirmation = false ;
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Porfavor confirme la orden");
        confirmationAlert.setHeaderText(msjAction);
        confirmationAlert.setContentText(msjQuery);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            confirmation = true;
        }
        return confirmation;
    }

}
