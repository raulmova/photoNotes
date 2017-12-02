package DB.Modelo;

/**
 * Created by Raul on 19/10/2017.
 */


public class Photo {

    private int id_photos;
    private int id_cursando;
    private int id_usuario;
    private int id_materia;
    private String path;
    private String fecha;

/*Azure String*/
    public String id;
    
    public Photo(String path, String fecha) {
        this.path = path;
        this.fecha = fecha;
    }

    public Photo(int id_photos, int id_cursando, int id_usuario, int id_materia, String path, String fecha) {
        this.id_photos = id_photos;
        this.id_cursando = id_cursando;
        this.id_usuario = id_usuario;
        this.id_materia = id_materia;
        this.path = path;
        this.fecha = fecha;
    }

    public int getId_photos() {
        return id_photos;
    }

    public int getId_cursando() {
        return id_cursando;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId_materia() {
        return id_materia;
    }

    public String getPath() {
        return path;
    }

    public String getFecha() {
        return fecha;
    }
}
