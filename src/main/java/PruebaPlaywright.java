import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PruebaPlaywright {
    public static void main(String[] args) {
        //declaracion de los identificadores de los elementos,
        // se declaran como final para evitar posibles modificaciones no intencionales
        final String campoNombre = "#firstName";
        final String campoApellido = "#lastName";
        final String campoEmail = "#userEmail";
        final String etiquetaGeneroMasculino = "label[for='gender-radio-1']";
        final String etiquetaGeneroFemenino = "label[for='gender-radio-2']";
        final String etiquetaGeneroOtro = "label[for='gender-radio-3']";
        final String campoCelular = "#userNumber";
        final String campoFechaNacimiento = "#dateOfBirthInput";
        final String campoMaterias = "#subjectsInput";
        //se queda en bucle infinito si no se da click al label
        final String etiquetaHobbyDeportes = "label[for='hobbies-checkbox-1']";
        final String etiquetaHobbyLectura = "label[for='hobbies-checkbox-2']";
        final String etiquetaHobbyMusica="label[for='hobbies-checkbox-3']";
        final String inputSubirFoto = "#uploadPicture";
        final String campoDireccionActual = "#currentAddress";
        final String listaDesplegableEstado = "#state";
        final String listaDesplegableCiudad = "#city";
        final String botonEnviar = "#submit";
        final String modalExito = ".modal-content";
        //Datos de prueba 1
        String nombre= "Juan Gabriel";
        String apellido="Jimenez Gonzales";
        String email="daniel.bautista@segurosbolivar.com";
        String celular="3204958369";
        String direccion="Calle 26 #68-12";
        String Genero= "Other";
        String[] hobbies={"Sports","Music"};
        String[] Materias={"Computer Science", "Commerce", "Economics"};
        String fecha="12 June 2025";
        String estado="Uttar Pradesh";
        String ciudad="Lucknow";
        //mapeo para relacionar estados con ciudades
        Map<String, String[]> mapaEstados = new HashMap<>();
        mapaEstados.put("NCR", new String[]{"Delhi", "Gurgaon", "Noida"});
        mapaEstados.put("Uttar Pradesh", new String[]{"Agra", "Lucknow", "Merrut"});
        mapaEstados.put("Haryana", new String[]{"Karnal", "Panipat"});
        mapaEstados.put("Rajasthan", new String[]{"Jaipur", "Jaiselmer"});

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("https://demoqa.com/automation-practice-form");
            System.out.println("Titulo de la pagina: " + page.title());

            page.locator(campoNombre).fill(nombre);
            page.locator(campoApellido).fill(apellido);
            page.locator(campoEmail).fill(email);
            page.locator(campoCelular).fill(celular);
            page.locator(campoDireccionActual).fill(direccion);

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

            page.locator(campoFechaNacimiento).click();
            String teclaControl = System.getProperty("os.name").toLowerCase().contains("mac") ? "Meta" : "Control";
            page.keyboard().press(teclaControl + "+A");
            page.locator(campoFechaNacimiento).pressSequentially(fecha);
            page.keyboard().press("Enter");

            for (String materia : Materias) {
                page.locator(campoMaterias).pressSequentially(materia);
                page.keyboard().press("Enter");
            }


            page.locator(listaDesplegableEstado).click();
            page.keyboard().type(estado);
            page.keyboard().press("Enter");

            // --- SELECCIONAR CIUDAD ---
            page.locator(listaDesplegableCiudad).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            page.locator(listaDesplegableCiudad).click();
            page.keyboard().type(ciudad);
            page.keyboard().press("Enter");

            String nombreArchivo = "foto_prueba.jpg";
            try {
                Path path = Paths.get(nombreArchivo);
                if (!Files.exists(path)) {
                    Files.createFile(path);
                    System.out.println("Archivo de prueba creado: " + nombreArchivo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo crear el archivo temporal para la prueba.");
            }
            page.locator(inputSubirFoto).setInputFiles(Paths.get(nombreArchivo));
            assertThat(page.locator(inputSubirFoto)).not().isEmpty();

            page.locator(botonEnviar).scrollIntoViewIfNeeded();
            page.locator(botonEnviar).click();

            Locator modal = page.locator(modalExito);
            modal.waitFor();

            page.waitForTimeout(3000);

            browser.close();
        }
    }
}