import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Проверка функционала поисковой системы Google")
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
    @DisplayName("Проверка результатов поиска Google")
    public void searchAutomation() {
        // 1. Выполняем поиск
        $("#APjFqb").setValue("Selenide").pressEnter();
        sleep(5000);

        // 2. Проверяем и обрабатываем капчу
        assertTrue(checkAndHandleCaptcha(),
                "❌ Капча не пройдена или страница не загрузилась");

        // 3. Получаем отфильтрованные результаты
        ElementsCollection searchResults = $$("#rso h3")
                .filter(visible)
                .filterBy(attribute("class", "LC20lb MBeuO DKV0Md"));

        // 4. Проверяем что есть результаты
        assertFalse(searchResults.isEmpty(), "🔍 Нет подходящих результатов");

        // 5. Проверяем что все заголовки содержат Selenide (без учета регистра)
        System.out.println("\n📄 Найденные заголовки:");
        searchResults.forEach(result -> {
            String title = result.getText();
            System.out.println("- " + title);
            assertTrue(title.toLowerCase().contains("selenide"),
                    "Заголовок не содержит 'Selenide': " + title);
        });

        System.out.println("\n✅ Все заголовки содержат искомое слово");
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

    private boolean checkAndHandleCaptcha() {
        boolean captchaShown = false;
        int maxAttempts = 10;
        int attempt = 0;

        while (attempt < maxAttempts) {
            attempt++;

            if ($("#captcha-form").exists() || $("iframe[src*='recaptcha']").exists()) {
                if (!captchaShown) {
                    System.out.println("⚠ Обнаружена капча! Пожалуйста, пройдите её вручную.");
                    captchaShown = true;
                }
                sleep(5000);
            }
            else if ($("#rso").exists()) {
                System.out.println("✅ Капча пройдена (или её не было). Страница с результатами поиска загружена.");
                return true;
            }
            else {
                System.out.println("⏳ Ожидаем загрузки страницы...");
                sleep(3000);
            }
        }

        return false;
    }
}
