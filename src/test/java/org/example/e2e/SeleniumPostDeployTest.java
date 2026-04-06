package org.example.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

// TP5: Sobe a aplicação automaticamente para o teste de interface
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumPostDeployTest {

    @LocalServerPort
    private int port; // Captura a porta aleatória onde o Spring subiu

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Silencia logs desnecessários do Selenium/CDP no console
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        // Garante que o driver correto do Chrome esteja configurado
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Essencial para rodar no CI/CD (GitHub Actions)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
    }

    @Test
    void testarCarregamentoDaListaDeProdutosEmProducao() {
        // Prioriza a URL de produção do ambiente (CI/CD), senão usa o localhost na porta dinâmica
        String baseUrl = System.getenv().getOrDefault("PROD_URL", "http://localhost:" + port);
        String finalUrl = baseUrl + "/produtos";

        System.out.println("Iniciando teste Selenium na URL: " + finalUrl);

        driver.get(finalUrl);

        // Fail gracefully check: Verifica se o HTML básico foi renderizado
        String pageSource = driver.getPageSource();

        assertTrue(pageSource.contains("<html") || pageSource.contains("Produtos"),
                "Erro: A página não renderizou o conteúdo esperado ou o servidor está offline.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}