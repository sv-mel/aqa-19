import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleSeleniumTest extends BaseTest{

    @BeforeEach
    public void openBeforeEach() {
        driver = new ChromeDriver();
        driver.get("https://www.tkani-feya.ru/");

    }


    @Test
    public void shouldSearchTextile() {
        //[name="find"]
        //search by text
        driver.findElement(By.name("find")).sendKeys("шелк" + Keys.ENTER);

        assertEquals(driver.getCurrentUrl(), "https://www.tkani-feya.ru/fabrics/?find=%D1%88%D0%B5%D0%BB%D0%BA");
        assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());

        for (WebElement el : driver.findElements(By.cssSelector(".text-block .name"))) {
            assertTrue(el.getText().contains("Шелк"));
        }
    }

    @Test
    public void shouldSearchTextileSpace () {
        //search by space
        driver.findElement(By.name("find")).sendKeys(" " + Keys.ENTER);

        assertEquals(driver.getCurrentUrl(), "https://www.tkani-feya.ru/fabrics/?find=+");
        assertTrue(driver.findElement(By.tagName("h1")).isDisplayed());
    }

}
