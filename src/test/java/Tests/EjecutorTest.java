package Tests;

import Base.BaseTest;
import org.junit.jupiter.api.Test;
import service.RegistroService;
import data.DataSource;
import data.FakerDataSource;
import utils.DataGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EjecutorTest extends BaseTest {

    @Test
    void registrosMasivosConFaker() {

        DataSource fuente = new FakerDataSource(5);

        RegistroService service = new RegistroService(page);

        for (DataGenerator.UsuarioData usuario : fuente.obtenerDatos()) {

            service.ejecutarRegistro(usuario);
            assertTrue(
                    service.esRegistroExitoso(),
                    "El formulario no se envió correctamente"
            );
        }
    }
}