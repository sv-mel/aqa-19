import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    static ChromeDriver driver;

    @BeforeAll
    public static void setBeforeAll() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
    }

    @AfterEach
    public void closeAfterEach() {
        driver.close();
    }
}
