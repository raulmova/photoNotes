package DB.Modelo;

/**
 * Created by Raul on 19/10/2017.
 */

public class Usuario {

    private int id_user;
    private String nombre;
    private String contra;
    private String correo;

    public Usuario(int id_user, String nombre, String contra, String correo) {
        this.id_user = id_user;
        this.nombre = nombre;
        this.contra = contra;
        this.correo = correo;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContra() {
        return contra;
    }

    public String getCorreo() {
        return correo;
    }
}
