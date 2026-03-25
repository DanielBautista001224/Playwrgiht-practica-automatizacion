package runner;

import com.microsoft.playwright.*;
import data.DataSource;
import data.FakerDataSource;
import data.GoogleSheetsDataSource;
import service.RegistroService;
import utils.ConfigManager;
import utils.DataGenerator;

public class MainRunner {

    public static void main(String[] args) {

        System.out.println("Modo configurado: " + ConfigManager.get("modo"));
        System.out.println("=== INICIO AUTOMATIZACION ===");

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            RegistroService service = new RegistroService(page);

            DataSource fuente;

            String modo = ConfigManager.get("modo");

            if ("FAKER".equalsIgnoreCase(modo)) {
                int cantidad = Integer.parseInt(ConfigManager.get("cantidadPruebas"));
                fuente = new FakerDataSource(cantidad);
            } else {
                fuente = new GoogleSheetsDataSource();
            }

            for (DataGenerator.UsuarioData usuario : fuente.obtenerDatos()) {

                System.out.println("Ejecutando usuario: " + usuario.nombre);

                try {
                    service.ejecutarRegistro(usuario);

                    if (service.esRegistroExitoso()) {
                        System.out.println("✔ Registro exitoso");
                    } else {
                        System.out.println("✖ Registro fallido");
                    }

                } catch (Exception e) {
                    System.out.println("✖ Error en ejecución: " + e.getMessage());
                }
            }

            browser.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=== FIN AUTOMATIZACION ===");
    }
}