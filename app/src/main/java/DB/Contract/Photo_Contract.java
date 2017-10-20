package DB.Contract;

/**
 * Created by Raul on 19/10/2017.
 */


public class Photo_Contract {



    interface ColumnasPhoto {
        String ID_PHOTOS= "id_photos";
        String PATH= "path";
        String FECHA = "fecha";
        String ID_CURSANDO = "id_cursando";
        String ID_USUARIO = "id_usuario";
        String ID_MATERIA = "id_materia";
    }
    interface  ColumnasMateria
    {
        String ID_MATERIA = "id_materia";
        String NOMBRE = "nombre";
    }

    interface ColumnasUsuario{
        String ID_USUARIO = "id_usuario";
        String NOMBRE = "nombre";
        String CONTRA = "contra";
        String CORREO = "correo";
    }
    interface ColumnasCursando{
        String ID_CURSANDO = "id_cursando";
        String ID_USUARIO = "id_usuario";
        String ID_MATERIA = "id_materia";
        String NOMBRE = "nombre";
        String DESCRIPCION = "descripcion";
        String HORARIO = "horario";
        String HORA_ENTRADA = "horario_entrada";
        String HORARIO_SALIDA = "horario_salida";
    }

    public static class Photos implements ColumnasPhoto {
        // Métodos auxiliares
    }
    public static class Materia implements ColumnasMateria {
        // Métodos auxiliares
    }

    public static class Usuario implements ColumnasUsuario {
        // Métodos auxiliares
    }
    public static class Cursando implements ColumnasCursando {
        // Métodos auxiliares
    }




}