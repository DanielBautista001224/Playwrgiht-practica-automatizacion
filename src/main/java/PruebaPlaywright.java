import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import pages.RegistrationPage;
import utils.DataGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PruebaPlaywright {
    public static void main(String[] args) {

        DataGenerator.UsuarioData usuario = DataGenerator.generarUsuarioValido();
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            RegistrationPage registroPage = new RegistrationPage(page);

            String nombreArchivo = "foto_prueba.jpg";
            String rutadummy= crearArchivoDummy(nombreArchivo);

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
            registroPage.seleccionarHobbies(usuario.hobbies);
            registroPage.seleccionarEstadoYCiudad(usuario.estado, usuario.ciudad);
            registroPage.subirFoto(rutadummy);
            registroPage.enviarFormulario();
            if(registroPage.esRegistroExitoso()){
                System.out.println("Formulario Enviado Exitosamente");
            }

            page.waitForTimeout(3000);

            browser.close();
        }
    }
    private static String crearArchivoDummy(String nombre) {
        try {
            Path path = Paths.get("src/test/resources/"+nombre);
            String route="src/test/resources/"+nombre;
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