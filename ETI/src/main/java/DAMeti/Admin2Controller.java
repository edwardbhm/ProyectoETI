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
        // Puedes agregar inicializaciones si es necesario
    }

    @FXML
    private void handleGestionarUsuarioButtonAction(ActionEvent event) {
        try {
            // Cargar el FXML de AlumnoGestion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DAM/ETI/alumnoGestion.fxml")); 
            Parent root = loader.load();

            // Obtener el controlador de AlumnoGestionController
            AlumnoGestionController controller = loader.getController();
            controller.cargarDatos(); // Asegúrate de que este método sea público en el controlador de AlumnoGestion

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
        // Lógica para gestionar libros (similar a gestionar usuarios)
        closeWindow(event);
    }

    @FXML
    private void handleGestionarPeticionButtonAction(ActionEvent event) {
        // Lógica para gestionar peticiones
        closeWindow(event);
    }

    @FXML
    private void handleGestionarPrestamoButtonAction(ActionEvent event) {
        // Lógica para gestionar préstamos
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

    // Método para cerrar la ventana
    private void closeWindow(ActionEvent event) {
        // Obtiene el stage (ventana actual)
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        // Cierra la ventana
        stage.close();
    }
}