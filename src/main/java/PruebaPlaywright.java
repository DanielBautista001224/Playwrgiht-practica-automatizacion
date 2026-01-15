import com.microsoft.playwright.*;

public class PruebaPlaywright {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("https://demoqa.com/automation-practice-form");
            System.out.println("Titulo de la pagina: " + page.title());

            page.waitForTimeout(3000);

            browser.close();
        }
    }
}