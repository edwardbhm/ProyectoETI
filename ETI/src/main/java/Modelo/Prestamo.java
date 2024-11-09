package Modelo;

import java.util.Date;
public class Prestamo {
   private int id;
   private int idAlumno;
   private int idLibro;
   private String nombreAlumno;
   private String tituloLibro;
   private Date fechaPrestamo;
   private Date fechaDevolucion;
   public Prestamo(int id, int idAlumno, int idLibro, String nombreAlumno, String tituloLibro, Date fechaPrestamo, Date fechaDevolucion) {
       this.id = id;
       this.idAlumno = idAlumno;
       this.idLibro = idLibro;
       this.nombreAlumno = nombreAlumno;
       this.tituloLibro = tituloLibro;
       this.fechaPrestamo = fechaPrestamo;
       this.fechaDevolucion = fechaDevolucion;
   }
   // Getters y Setters
   public int getId() { return id; }
   public void setId(int id) { this.id = id; }
   public int getIdAlumno() { return idAlumno; }
   public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }
   public int getIdLibro() { return idLibro; }
   public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
   public String getNombreAlumno() { return nombreAlumno; }
   public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }
   public String getTituloLibro() { return tituloLibro; }
   public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
   public Date getFechaPrestamo() { return fechaPrestamo; }
   public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
   public Date getFechaDevolucion() { return fechaDevolucion; }
   public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}
