package data;

import java.util.List;
import utils.DataGenerator;

public interface DataSource {
    List<DataGenerator.UsuarioData> obtenerDatos();
}