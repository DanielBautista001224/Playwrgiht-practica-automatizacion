package pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.nio.file.Paths;
import java.util.List;

public class RegistrationPage {
    private final Page page;

    // Selectores finales (Basados en tu análisis e IDs)
    //declaracion de los identificadores de los elementos,
    // se declaran como final para evitar posibles modificaciones no intencionales
    private final String campoNombre = "#firstName";
    private final String campoApellido = "#lastName";
    private final String campoEmail = "#userEmail";
    private final String etiquetaGeneroMasculino = "label[for='gender-radio-1']";
    private final String etiquetaGeneroFemenino = "label[for='gender-radio-2']";
    private final String etiquetaGeneroOtro = "label[for='gender-radio-3']";
    private final String campoCelular = "#userNumber";
    private final String campoFechaNacimiento = "#dateOfBirthInput";
    private final String campoMaterias = "#subjectsInput";
    //se queda en bucle infinito si no se da click al label
    private final String etiquetaHobbyDeportes = "label[for='hobbies-checkbox-1']";
    private final String etiquetaHobbyLectura = "label[for='hobbies-checkbox-2']";
    private final String etiquetaHobbyMusica="label[for='hobbies-checkbox-3']";
    private final String inputSubirFoto = "#uploadPicture";
    private final String campoDireccionActual = "#currentAddress";
    private final String listaDesplegableEstado = "#state";
    private final String listaDesplegableCiudad = "#city";
    private final String botonEnviar = "#submit";
    private final String modalExito = ".modal-content";

    public RegistrationPage(Page page) { this.page = page; }

    public void navegar() { page.navigate("https://demoqa.com/automation-practice-form"); }

    public void llenarDatosPersonales(String nombre, String apellido, String email, String movil, String direccion) {
        page.locator(campoNombre).fill(nombre);
        page.locator(campoApellido).fill(apellido);
        page.locator(campoEmail).fill(email);
        page.locator(campoCelular).fill(movil);
        page.locator(campoDireccionActual).fill(direccion);
    }

    public void seleccionarGenero(String Genero) {
        switch (Genero) {
            case "Male":
                page.locator(etiquetaGeneroMasculino).click();
                break;
            case "Female":
                page.locator(etiquetaGeneroFemenino).click();
                break;
            case "Other":
                page.locator(etiquetaGeneroOtro).click();
                break;
            default:
                throw new IllegalArgumentException("Género no reconocido: " + Genero);
        }
    }

    public void seleccionarFechaNacimiento(String fecha) {
        page.locator(campoFechaNacimiento).click();
        String teclaControl = System.getProperty("os.name").toLowerCase().contains("mac") ? "Meta" : "Control";
        page.keyboard().press(teclaControl + "+A");
        page.locator(campoFechaNacimiento).pressSequentially(fecha);
        page.keyboard().press("Enter");
    }

    public void seleccionarMaterias(List<String> Materias) {
        for (String materia : Materias) {
            page.locator(campoMaterias).pressSequentially(materia);
            page.keyboard().press("Enter");
        }
    }

    public void seleccionarHobbies(List<String> hobbies) {
        for (String hobby : hobbies) {
            switch (hobby) {
                case "Sports":
                    page.locator(etiquetaHobbyDeportes).click();
                    break;
                case "Reading":
                    page.locator(etiquetaHobbyLectura).click();
                    break;
                case "Music":
                    page.locator(etiquetaHobbyMusica).click();
                    break;
            }
        }
    }

    public void subirFoto(String ruta) {
        page.locator(inputSubirFoto).setInputFiles(Paths.get(ruta));
        assertThat(page.locator(inputSubirFoto)).not().isEmpty();
    }

    public void seleccionarEstadoYCiudad(String estado, String ciudad) {
        page.locator(listaDesplegableEstado).click();
        page.keyboard().type(estado);
        page.keyboard().press("Enter");

        page.locator(listaDesplegableCiudad).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator(listaDesplegableCiudad).click();
        page.keyboard().type(ciudad);
        page.keyboard().press("Enter");
    }

    public void enviarFormulario() {
        page.locator(botonEnviar).scrollIntoViewIfNeeded();
        page.locator(botonEnviar).click();
    }

    public boolean esRegistroExitoso() {
        Locator modal = page.locator(modalExito);
        modal.waitFor();
        return modal.isVisible() && modal.innerText().contains("Thanks for submitting");
    }
}