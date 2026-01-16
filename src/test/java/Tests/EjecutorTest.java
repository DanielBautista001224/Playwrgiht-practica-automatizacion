package Tests;
import Base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.RegistrationPage;
import utils.DataGenerator;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EjecutorTest extends BaseTest {
    @Test
    void registroUsuarioValidoCompleto() {
        ejecutarRegistro(DataGenerator.generarUsuarioValido());
    }

    @Test
    void registroSinHobbies() {
        DataGenerator.UsuarioData usuario = DataGenerator.generarUsuarioValido();
        usuario.hobbies.clear();
        ejecutarRegistro(usuario);
    }

    @Test
    void registroSinFechaDeNacimiento() {
        DataGenerator.UsuarioData usuario = DataGenerator.generarUsuarioValido();
        usuario.fechaNacimiento = "";
        ejecutarRegistro(usuario);
    }

    @Test
    void registroConGeneroOther() {
        DataGenerator.UsuarioData usuario = DataGenerator.generarUsuarioValido();
        usuario.genero = "Other" ;
        ejecutarRegistro(usuario);
    }

    @Test
    void registroFechaLimiteInferior() {
        DataGenerator.UsuarioData usuario = DataGenerator.generarUsuarioValido();
        usuario.fechaNacimiento = "01 January 1900"; // valor límite
        ejecutarRegistro(usuario);
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
}