package data;

import utils.DataGenerator;
import java.util.ArrayList;
import java.util.List;

public class FakerDataSource implements DataSource {

    private int cantidad;

    public FakerDataSource(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public List<DataGenerator.UsuarioData> obtenerDatos() {
        List<DataGenerator.UsuarioData> lista = new ArrayList<>();

        for (int i = 0; i < cantidad; i++) {
            lista.add(DataGenerator.generarUsuarioValido());
        }

        return lista;
    }
}