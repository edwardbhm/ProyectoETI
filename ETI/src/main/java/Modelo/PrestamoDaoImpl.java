package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDaoImpl implements PrestamoDao {

    private Connection connection;

    public PrestamoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crearPrestamo(Prestamo prestamo) {
        String sql = "INSERT INTO prestamo (id_alumno, id_libro, nombre_alumno, titulo_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdAlumno());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setString(3, prestamo.getNombreAlumno());
            stmt.setString(4, prestamo.getTituloLibro());
            stmt.setDate(5, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(6, prestamo.getFechaDevolucion() != null ? new java.sql.Date(prestamo.getFechaDevolucion().getTime()) : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificarPrestamo(Prestamo prestamo) {
        String sql = "UPDATE prestamo SET id_alumno = ?, id_libro = ?, nombre_alumno = ?, titulo_libro = ?, fecha_prestamo = ?, fecha_devolucion = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdAlumno());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setString(3, prestamo.getNombreAlumno());
            stmt.setString(4, prestamo.getTituloLibro());
            stmt.setDate(5, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(6, prestamo.getFechaDevolucion() != null ? new java.sql.Date(prestamo.getFechaDevolucion().getTime()) : null);
            stmt.setInt(7, prestamo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarPrestamo(int id) {
        String sql = "DELETE FROM prestamo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prestamo obtenerPrestamo(int id) {
        String sql = "SELECT * FROM prestamo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("id_alumno"),
                    rs.getInt("id_libro"),
                    rs.getString("nombre_alumno"),
                    rs.getString("titulo_libro"),
                    rs.getDate("fecha_prestamo"),
                    rs.getDate("fecha_devolucion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Préstamo no encontrado
    }

    @Override
    public List<Prestamo> listarPrestamos() {
        List<Prestamo> listaPrestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamo";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                listaPrestamos.add(new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("id_alumno"),
                    rs.getInt("id_libro"),
                    rs.getString("nombre_alumno"),
                    rs.getString("titulo_libro"),
                    rs.getDate("fecha_prestamo"),
                    rs.getDate("fecha_devolucion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPrestamos;
    }

    @Override
    public List<Prestamo> listarPrestamosPorAlumno(int idAlumno) {
        List<Prestamo> listaPrestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamo WHERE id_alumno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                listaPrestamos.add(new Prestamo(
                    rs.getInt("id"),
                    rs.getInt("id_alumno"),
                    rs.getInt("id_libro"),
                    rs.getString("nombre_alumno"),
                    rs.getString("titulo_libro"),
                    rs.getDate("fecha_prestamo"),
                    rs.getDate("fecha_devolucion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPrestamos;
    }
}


