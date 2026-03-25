package service;

import com.microsoft.playwright.Page;
import pages.RegistrationPage;
import utils.DataGenerator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegistroService {

    private Page page;

    public RegistroService(Page page) {
        this.page = page;
    }

    public void ejecutarRegistro(DataGenerator.UsuarioData usuario) {

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
    }

    private String crearArchivoDummy(String nombre) {
        try {
            Path tempPath = Paths.get(System.getProperty("java.io.tmpdir"), nombre);

            if (!Files.exists(tempPath)) {
                Files.write(tempPath, "dummy file".getBytes());
            }

            System.out.println("Archivo dummy en: " + tempPath.toAbsolutePath());

            return tempPath.toAbsolutePath().toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo crear archivo dummy");
        }
    }
    public boolean esRegistroExitoso() {
        RegistrationPage registroPage = new RegistrationPage(page);
        return registroPage.esRegistroExitoso();
    }
}