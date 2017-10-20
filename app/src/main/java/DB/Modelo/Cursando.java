package DB.Modelo;

/**
 * Created by Raul on 19/10/2017.
 */

public class Cursando {

    private int id_cursando;
    private int id_usuario;
    private int id_materia;
    private String nombre;
    private String descripcion;
    private String horario;
    private String hora_entrada;
    private String hora_salida;


    public Cursando(int id_cursando, int id_usuario, int id_materia, String nombre, String descripcion, String horario, String hora_entrada, String hora_salida) {
        this.id_cursando = id_cursando;
        this.id_usuario = id_usuario;
        this.id_materia = id_materia;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horario = horario;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public String getHora_salida() {
        return hora_salida;
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
}