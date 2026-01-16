import com.microsoft.playwright.*;

public class PruebaPlaywright {
    public static void main(String[] args) {
        //declaracion de los identificadores de los elementos,
        // se declaran como final para evitar posibles modificaciones no intencionales
        final String campoNombre = "#firstName";
        final String campoApellido = "#lastName";
        final String campoEmail = "#userEmail";
        final String etiquetaGeneroMasculino = "label[for='gender-radio-1']";
        final String etiquetaGeneroFemenino = "label[for='gender-radio-2']";
        final String campoCelular = "#userNumber";
        final String campoFechaNacimiento = "#dateOfBirthInput";
        final String campoMaterias = "#subjectsInput";
        final String etiquetaHobbyDeportes = "label[for='hobbies-checkbox-1']";
        final String etiquetaHobbyLectura = "label[for='hobbies-checkbox-2']";
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

            page.waitForTimeout(3000);

            browser.close();
        }
    }
}