import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {
    /**
     *
     */
    @BeforeAll
    public static void setUp() {
        open("https://www.google.com");
    }

    /**
     *
     */
    @Test
    public void searchAutomation() {
        $("#APjFqb").setValue("Selenide").pressEnter();
        sleep(5000);
        boolean captchaShown = false;
        boolean captchaPassed = false;
        int maxAttempts = 10;
        int attempt = 0;

        while (attempt < maxAttempts && !captchaPassed) {
            attempt++;

            if ($("#captcha-form").exists() || $("iframe[src*='recaptcha']").exists()) {
                if (!captchaShown) {
                    System.out.println("⚠ Обнаружена капча! Пожалуйста, пройдите её вручную.");
                    captchaShown = true;
                }
                sleep(5000);
            }

            else if ($("#logo > svg").exists()) {
                captchaPassed = true;
                System.out.println("✅ Капча пройдена (или её не было). Продолжаем тест.");
            }

            else {
                System.out.println("⏳ Ожидаем загрузки страницы...");
                sleep(3000);
            }
        }

        if (!captchaPassed) {
            System.out.println("❌ Не удалось подтвердить прохождение капчи. Тест остановлен.");
            throw new AssertionError("❌ Капча не пройдена или страница не загрузилась.");
        }

        // Выводим все заголовки результатов поиска (#rso h3)
        ElementsCollection searchResults = $$("#rso h3");
        if (searchResults.isEmpty()) {
            System.out.println("🔍 Результаты поиска не найдены.");
        } else {
            System.out.println("\n📄 Найденные заголовки:");
            searchResults.forEach(result -> System.out.println("- " + result.getText()));
        }
    }

    /**
     *
     */
    @Test
    public void checkPagination() {

    }

    /**
     *
     */
    @Test
    public void checkImage() {

    }

    /**
     *
     */
    @Test
    public void checkElements() {

    }

    /**
     *
     */
    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
