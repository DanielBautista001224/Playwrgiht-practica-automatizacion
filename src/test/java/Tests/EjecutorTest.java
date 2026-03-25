package Tests;
import Base.BaseTest;
import data.DataSource;
import data.FakerDataSource;
import data.GoogleSheetsDataSource;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import utils.ConfigManager;
import utils.DataGenerator;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class  EjecutorTest extends BaseTest {

    @Test
    void registrosMasivos() {
        DataSource fuente = obtenerFuenteDesdeConfig();

        for (DataGenerator.UsuarioData usuario : fuente.obtenerDatos()) {
            ejecutarRegistro(usuario);
        }
    }


    private void ejecutarRegistro(DataGenerator.UsuarioData usuario) {
        RegistrationPage registroPage = new RegistrationPage(page);

        String rutaDummy = crearArchivoDummy("foto_prueba.jpg");

        registroPage.navegar();
        registroPage.llenarDatosPersonales(
                usuario.nombre,
                usuario.apellido,
                usuario.email,
                usuario.celular,
                usuario.direccion
        );
        registroPage.seleccionarGenero(usuario.genero);

        registroPage.seleccionarFechaNacimiento(usuario.fechaNacimiento);

        registroPage.seleccionarMaterias(usuario.materias);

        registroPage.seleccionarEstadoYCiudad(usuario.estado, usuario.ciudad);

        registroPage.seleccionarHobbies(usuario.hobbies);

        registroPage.subirFoto(rutaDummy);

        registroPage.enviarFormulario();

        assertTrue(registroPage.esRegistroExitoso(),
                "El formulario no se envió correctamente");
    }

    private String crearArchivoDummy(String nombre) {
        try {
            Path path = Paths.get("src/test/resources/" + nombre);
            String route = "src/test/resources/" + nombre;
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("Archivo de prueba creado: " + nombre);
            }
            return route;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo crear el archivo temporal para la prueba.");
        }

    }
    private DataSource obtenerFuenteDesdeConfig() {

        String modo = ConfigManager.get("modo");

        if ("FAKER".equalsIgnoreCase(modo)) {
            int cantidad = Integer.parseInt(ConfigManager.get("cantidadPruebas"));
            return new FakerDataSource(cantidad);
        }

        if ("GOOGLE_SHEETS".equalsIgnoreCase(modo)) {
            return new GoogleSheetsDataSource();
        }

        throw new RuntimeException("Modo no soportado");
    }
}