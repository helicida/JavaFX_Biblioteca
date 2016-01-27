package sample.Objetos;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 46465442z on 24/01/16.
 */
@Entity
public class Llibre implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String titol;
    private String autor;
    private String nombreExemplars;
    private String editorial;
    private String nombrePagines;
    private String anyEdicio;

    // Constructor
    public Llibre() {

    }

    // Getters
    
    public String getTitol() {return titol;}

    public String getAutor() {return autor;}

    public String getNombreExemplars() {return nombreExemplars;}

    public String getEditorial() {return editorial;}

    public String getNombrePagines() {return nombrePagines;}

    public String getAnyEdicio() {return anyEdicio;}


    // Setters

    public void setTitol(String titol) {this.titol = titol;}

    public void setAutor(String autor) {this.autor = autor;}

    public void setNombreExemplars(String numExemplars) {this.nombreExemplars = numExemplars;}

    public void setEditorial(String editorial) {this.editorial = editorial;}

    public void setNombrePagines(String numPagines) {this.nombrePagines = numPagines;}

    public void setAnyEdicio(String anyEdicio) {this.anyEdicio = anyEdicio;}

    public String toString() {
        return "\n     Titol: " + titol +
                "\n     Autor: "+ autor +
                "\n     Nombre Exemplars: " + nombreExemplars +
                "\n     Editorial: " + editorial +
                "\n     Nombre Pagines: " + nombrePagines +
                "\n     Any Edició: " + anyEdicio;
    }
}
