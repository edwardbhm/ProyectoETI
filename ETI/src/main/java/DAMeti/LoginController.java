package DAMeti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContra;
    
    private static final String URL = "jdbc:mysql://localhost:3306/eti"; 
    private static final String USUARIO = "root"; 
    private static final String CONTRASEÑA = ""; 
    
    // Método para establecer la conexión a la base de datos
    public static Connection dameConexion() throws SQLException {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión establecida.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
            throw e;
        }
        return conexion;
    }

    private boolean verificarCredenciales(String usuario, String contraseña) {
        String sql = "SELECT * FROM alumnos WHERE usuario = ? AND contrasena = ?"; 
        
        try (Connection connection = dameConexion();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet resultSet = stmt.executeQuery();
            
            return resultSet.next(); 
        } catch (SQLException e) {
            mostrarError("Error de conexión a la base de datos", "No se pudo conectar a la base de datos.");
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contraseña = txtContra.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Campos vacíos", "Por favor, ingrese tanto el usuario como la contraseña.");
        } else {
            boolean autenticado = verificarCredenciales(usuario, contraseña);

            if (autenticado) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DAM/ETI/bienvenidoAlumno.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    mostrarError("Error al cargar la pantalla", "No se pudo cargar la pantalla de bienvenida.");
                    e.printStackTrace();
                }
            } else {
                mostrarAlerta(AlertType.ERROR, "Error de inicio de sesión", "Usuario o contraseña incorrectos. Inténtalo de nuevo.");
            }
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/alumno1.fxml");
    }

    @FXML
    private void handleInicioButtonAction(ActionEvent event) {
        cambiarEscena((Stage) ((Node) event.getSource()).getScene().getWindow(), "/DAM/ETI/inicio.fxml");
    }

    @FXML
    private void handleOlvidoContrasenaAction(ActionEvent event) {
        String usuario = txtUsuario.getText();

        if (usuario.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Falta el usuario", "Por favor, ingrese su usuario.");
            return;
        }

        TextField txtNombrePadreMadre = new TextField();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recuperación de contraseña");
        alert.setHeaderText("Introduce el nombre del padre/madre asociado a tu cuenta");
        alert.getDialogPane().setContent(txtNombrePadreMadre);

        alert.showAndWait().ifPresent(response -> {
            String nombrePadreMadre = txtNombrePadreMadre.getText();

            if (verificarPadreMadre(usuario, nombrePadreMadre)) {
                cambiarContrasena(usuario);
            } else {
                mostrarAlerta(AlertType.ERROR, "Error de verificación", "El nombre del padre/madre no coincide con el registrado. Inténtalo de nuevo.");
            }
        });
    }

    // Verifica el nombre del padre/madre en la base de datos
    private boolean verificarPadreMadre(String usuario, String nombrePadreMadre) {
        String sql = "SELECT * FROM alumnos WHERE usuario = ? AND nombre_madre_padre = ?";

        try (Connection connection = dameConexion();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, nombrePadreMadre);
            ResultSet resultSet = stmt.executeQuery();
            
            return resultSet.next();
        } catch (SQLException e) {
            mostrarError("Error de conexión a la base de datos", "No se pudo conectar a la base de datos.");
            e.printStackTrace();
            return false;
        }
    }

    // Cambia la contraseña en la base de datos
    private void cambiarContrasena(String usuario) {
        PasswordField txtNuevaContrasena = new PasswordField();
        PasswordField txtConfirmarContrasena = new PasswordField();

        VBox vbox = new VBox(10);
        txtNuevaContrasena.setPromptText("Nueva contraseña");
        txtConfirmarContrasena.setPromptText("Confirmar contraseña");
        vbox.getChildren().addAll(txtNuevaContrasena, txtConfirmarContrasena);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recuperación de contraseña");
        alert.setHeaderText("Introduce tu nueva contraseña dos veces");
        alert.getDialogPane().setContent(vbox);

        alert.showAndWait().ifPresent(response -> {
            String nuevaContrasena = txtNuevaContrasena.getText();
            String confirmarContrasena = txtConfirmarContrasena.getText();

            if (!nuevaContrasena.isEmpty() && nuevaContrasena.equals(confirmarContrasena)) {
                actualizarContrasena(usuario, nuevaContrasena);
                mostrarAlerta(AlertType.INFORMATION, "Contraseña actualizada", "La contraseña se ha actualizado correctamente. Se enviará un correo a su padre/madre con la nueva contraseña.");
            } else {
                mostrarAlerta(AlertType.ERROR, "Error de confirmación", "Las contraseñas no coinciden. Inténtalo de nuevo.");
            }
        });
    }

    private void actualizarContrasena(String usuario, String nuevaContrasena) {
        String sql = "UPDATE alumnos SET contrasena = ? WHERE usuario = ?";

        try (Connection connection = dameConexion();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuevaContrasena);
            stmt.setString(2, usuario);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                mostrarAlerta(AlertType.ERROR, "Error al actualizar", "No se pudo actualizar la contraseña");
            }
        } catch (SQLException e) {
            mostrarError("Error de conexión a la base de datos", "No se pudo actualizar la contraseña.");
            e.printStackTrace();
        }
    }

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

    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        mostrarAlerta(AlertType.ERROR, titulo, mensaje);
    }
}
