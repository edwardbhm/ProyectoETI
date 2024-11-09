package DAMeti;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

public class Admin2Controller {

    @FXML
    private Button gestionarUsuarioButton;
    
    @FXML
    private Button gestionarLibroButton;
    
    @FXML
    private Button gestionarPeticionButton;
    
    @FXML
    private Button gestionarPrestamoButton;

    @FXML
    private Button backButton;
    
    @FXML
    private Button inicioButton;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleGestionarUsuarioButtonAction(ActionEvent event) {
        try {
            // Cargar el FXML de AlumnoGestion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DAM/ETI/alumnoGestion.fxml")); 
            Parent root = loader.load();

            // Obtener el controlador de AlumnoGestionController
            AlumnoGestionController controller = loader.getController();
            controller.cargarDatos(); 
            
            // Crear una nueva escena y mostrarla en un nuevo escenario
            Stage stage = new Stage();
            stage.setTitle("Gestionar Alumnos");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            closeWindow(event);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    private void handleGestionarLibroButtonAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void handleGestionarPeticionButtonAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void handleGestionarPrestamoButtonAction(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
    	App.changeScene((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/admin1.fxml");

    }

    @FXML
    private void handleInicioButtonAction(ActionEvent event) {
    	App.changeScene((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/inicio.fxml");
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}