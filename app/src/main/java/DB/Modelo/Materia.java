package DB.Modelo;

/**
 * Created by Raul on 19/10/2017.
 */
public class Materia {
    public int id_materia;
    public String id;
    private String nombre;



    public Materia(int id_materia, String nombre) {
        this.id_materia = id_materia;
        this.nombre = nombre;

    }
    public Materia(String nombre){
        this.nombre=nombre;
    }

    public int getId_materia() {
        return id_materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
