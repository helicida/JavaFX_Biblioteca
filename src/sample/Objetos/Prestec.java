package sample.Objetos;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 46465442z on 24/01/16.
 */
@Entity
public class Prestec implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private Llibre llibre;
    private Soci soci;
    private Date dataInici;
    private Date dataFinal;

    // Constructor
    public Prestec() {

    }

    // Getters

    public Llibre getLlibre() {return llibre;}

    public Soci getSoci() {return soci;}

    public Date getDataInici() {return dataInici;}

    public Date getDataFinal() {return dataFinal;}

    // Setters

    public void setLlibre(Llibre llibre) {this.llibre = llibre;}

    public void setSoci(Soci soci) {this.soci = soci;}

    public void setDataInici(Date dataInici) {this.dataInici = dataInici;}

    public void setDataFinal(Date dataFinal) {this.dataFinal = dataFinal;}

    public String toString() {
        return "     Data Inici: " + dataInici +
                "\n     Data Final: " + dataFinal +
                "\n     Soci: " + soci.toString() +
                "\n     LLibre: " + llibre.toString();
    }
}
