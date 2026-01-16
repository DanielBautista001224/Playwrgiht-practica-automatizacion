package Base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import java.nio.file.*;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        // headless: false para que sea visible la ejecucion
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    //cierra el navegador al finalizar las pruebas
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContext() {
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get("Evidencias/videos/"))
        );
        page = context.newPage();
    }


    @AfterEach
    //cierra la pagina tras cada prueba
    void closeContext() {
        //toma la fecha y hora actual
        ZonedDateTime ahora = ZonedDateTime.now(ZoneId.of("America/Bogota"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Evidencias/Capturas/Captura_"+ahora.format(formato)+".png"))
                .setFullPage(true));
        context.close();
        try {

            if (page.video() != null) {
                //Se asegura de que existe el video
                Path carpetaVideos = Paths.get("Evidencias/videos");
                Files.createDirectories(carpetaVideos);

                //le cambia el nombre al video para que sea la fecha y hora actuales
                Path videoOriginal = page.video().path();
                Path nuevoNombre = carpetaVideos.resolve(
                        "video_" + ahora.format(formato) + ".webm"
                );
                Files.move(videoOriginal, nuevoNombre, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.err.println("⚠ No se pudo guardar el video:");
            e.printStackTrace();
        }
    }
}

