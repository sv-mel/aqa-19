import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class BesellerTests extends BaseTest{

    @BeforeEach
    public void openBeforeEach() {
        driver = new ChromeDriver();
        baseUrl = "https://demo.beseller.by";
        wait = new WebDriverWait(driver, Duration.of(7, ChronoUnit.SECONDS));
    }

    @Test
    public void shouldBuyProduct() {
        driver.get(baseUrl + "/bytovaya-tehnika/shveynye-mashiny/toyota_esoa_21/");

        driver.findElement(By.cssSelector("[data-gtm-id='add-to-cart-product']")).click();

        //после нажатия В корзину кнопка меняет название на Оформить
        assertThat(driver
                .findElement(By.cssSelector(".product-add-block .oformit-v-korzine"))
                .isDisplayed()
        ).isTrue();

        //Ждем пока появится всплываюшее сообщение о добавлении в корзину
//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.attributeContains(By.className("cart-alert"),
//                "class",
//                "active")
//                );

        //появилось всплывающее сообщение
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("cart-alert"))));
        //счетчик в корзине на странице товара
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".button-basket .product-counter"), "1"));

        //Нажимаем на Оформить и проверяем
        driver.findElement(By.cssSelector(".product-add-block .oformit-v-korzine")).click();
        //url поменялся
        assertEquals(driver.getCurrentUrl(), "https://demo.beseller.by/shcart/");

        //сравниваем название, в корзине 1 штука, цена
        assertThat(driver.findElement(By.cssSelector("ok-order__productName"))
                .isDisplayed()
        ).isTrue();
    }
}


