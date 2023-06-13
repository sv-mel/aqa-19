import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BesellerTests extends BaseTest{

    @BeforeEach
    public void openBeforeEach() {
        driver = new ChromeDriver();
        baseUrl = "https://demo.beseller.by";
        wait = new WebDriverWait(driver, Duration.of(15, ChronoUnit.SECONDS));

    }

    @Test
        public void shouldAddProductToCart() {

        driver.get(baseUrl + "/bytovaya-tehnika/shveynye-mashiny/toyota_esoa_21/");

        driver.findElement(By.cssSelector("[data-gtm-id='add-to-cart-product']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("cart-alert"))));
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".button-basket .product-counter"), "1"));

        WebElement orderButton = driver.findElement(By.cssSelector(".product-add-block .oformit-v-korzine"));
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));

        orderButton.click();

        assertThat(driver.getCurrentUrl()).isEqualTo(baseUrl + "/shcart/");
        assertTrue(driver.findElement(By.className("ok-order__title")).isDisplayed());

        //проверка что в корзине есть товары
        assertThat(driver.findElements(By.cssSelector("[data-product-item]")).size()).isGreaterThan(0);
        WebElement product = driver.findElements(By.cssSelector("[data-product-item]")).get(0);

        //проверка что товар в корзине отображается корректно
        assertThat(product.findElement(By.cssSelector(".ok-order__image img")).getAttribute("alt")).contains("TOYOTA1 ESOA 21");
        assertThat(product.findElement(By.className("ok-order__text")).getText()).contains("TOYOTA1 ESOA 21");
        assertThat(product.findElement(By.className("ok-order__text")).getText()).contains("20102");
        assertEquals(
                product.findElement(By.cssSelector("[data-product-item-input-quantity]")).getAttribute("value"),
                "1"
        );
       // assertEquals(product.findElement(By.cssSelector(".ok-table-el.f-tac.-size-half.hidden-xs")).getText(),"шт.");
        assertEquals(
                product.findElement(By.cssSelector("[data-product-item-sum] [data-price-value]")).getText(), "49500");



        //оформление заказа
//        String fio = faker.name().fullName();
//        String address = faker.address().fullAddress();
//        String phone = faker.phoneNumber().phoneNumber();
//        String comment = faker.lorem().sentence();

        driver.findElement(By.name("fio")).sendKeys(faker.name().fullName());
        driver.findElement(By.name("registration")).sendKeys(faker.address().fullAddress());
        driver.findElement(By.name("phone")).sendKeys(faker.phoneNumber().phoneNumber());
        driver.findElement(By.name("comment")).sendKeys(faker.lorem().sentence());

        //переход на страницу Заказ оформлен
        driver.findElement(By.id("terms_btn_cart_fast")).click();
        assertEquals(driver.getCurrentUrl(), "https://demo.beseller.by/shcart/finish");
//        assertThat(driver.findElement(By.className("ok-order__title")).getText()).contains("ЗАКАЗ №", "ОФОРМЛЕН");

        //проверяем данные в заказе
        WebElement orderedProduct = driver.findElements(By.cssSelector(".ok-table-row")).get(0);
       // assertThat(orderedProduct.findElement(By.cssSelector(".ok-order__image img")).getAttribute("alt")).contains("TOYOTA1 ESOA 21");
        assertThat(orderedProduct.findElement(By.className("ok-order__productName")).getText()).contains("TOYOTA1 ESOA 21");
        assertThat(orderedProduct.findElement(By.className("ok-order__text")).getText()).contains("20102");
        assertEquals(orderedProduct.findElement(By.cssSelector(".ok-order__count")).getText(), "1");
        // assertEquals(product.findElement(By.cssSelector(".ok-table-el.f-tac.-size-half.hidden-xs")).getText(),"шт.");
        assertEquals(orderedProduct.findElement(By.cssSelector("[data-finish-order-value] [data-price-value]")).getText(), "49500");


    }


}


