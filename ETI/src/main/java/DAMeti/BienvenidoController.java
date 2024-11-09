package DAMeti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class BienvenidoController {

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/alumno1.fxml");
    }

    @FXML
    private void handleInicioButtonAction(ActionEvent event) {
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/inicio.fxml");
    }

    @FXML
    private void handleConsultarCuentaAction(ActionEvent event) {
        // Código para navegar a la pantalla de "Consultar mi cuenta"
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/consultarCuenta.fxml");
    }

    @FXML
    private void handlePedirLibroAction(ActionEvent event) {
        // Código para navegar a la pantalla de "Pedir libro"
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/pedirLibro.fxml");
    }

    // Método para cambiar la escena
    private void cambiarEscena(Stage stage, String rutaFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarError("Error de carga", "No se pudo cargar la pantalla especificada.");
            e.printStackTrace();
        }
    }

    // Muestra errores críticos
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
