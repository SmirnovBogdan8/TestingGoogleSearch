import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Автоматизация поиска в Google")
    public void searchAutomation() {
        // 1. Выполняем поиск
        $("#APjFqb").setValue("Selenide").pressEnter();
        sleep(3000);

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
    }

    /**
     *
     */
    @Test
    @DisplayName("Проверка пагинации")
    public void checkPagination() {
        // 1. Выполняем поиск
        $("#APjFqb").setValue("Selenide").pressEnter();
        sleep(3000);

        // 2. Проверяем и обрабатываем капчу
        assertTrue(checkAndHandleCaptcha(),
                "❌ Капча не пройдена или страница не загрузилась");

        // 3. Переходим на страницу 2
        $$("#botstuff a[aria-label='Page 2']").findBy(text("2")).click();
        sleep(3000);

        // 4. Проверяем что мы действительно перешли на страницу 2
        assertEquals("2", $("td.YyVfkd.NKTSme").getText(), "Текущая страница не является 2й. Актуальная страница: " +
                $("td.YyVfkd.NKTSme").getText());

        // 5. Проверяем результаты на второй странице
        ElementsCollection page2Results = $$("#rso h3")
                .filter(visible)
                .filterBy(attribute("class", "LC20lb MBeuO DKV0Md"));

        assertFalse(page2Results.isEmpty(),
                "🔍 На второй странице нет результатов");

        // 6. Проверяем что все заголовки содержат Selenide
        page2Results.forEach(result -> {
            String title = result.getText();
            assertTrue(title.toLowerCase().contains("selenide"),
                    "Заголовок не содержит 'Selenide': " + title);
        });
    }

    /**
     *
     */
    @Test
    @DisplayName("Проверка вкладки 'Картинки'")
    public void testImagesTab() {
        // 1. Выполняем поиск
        $("#APjFqb").setValue("Selenide").pressEnter();
        sleep(3000);

        // 2. Проверяем и обрабатываем капчу
        assertTrue(checkAndHandleCaptcha(),
                "❌ Капча не пройдена или страница не загрузилась");

        // 3. Находим и кликаем вкладку "Картинки"
        $("#hdtb-sc").$$("a").findBy(text("Картинки")).click();
        sleep(3000);

        // 4. Проверяем что отображаются изображения
        ElementsCollection images = $$("#search img");
        assertFalse(images.isEmpty(), "Не найдено изображений на странице");

        // 5. Кликаем на первое изображение
        images.first().click();
        sleep(2000);

        // 6. Проверяем что открылась увеличенная версия
        assertTrue($("#Sva75c a > img").exists(),
                "Не найдено увеличенного изображения");
        assertTrue($("#Sva75c a > img").isDisplayed(),
                "Увеличенное изображение не отображается");
    }

    /**
     *
     */
    @Test
    @DisplayName("Проверка основных элементов на странице результатов поиска")
    public void checkElements() {
        // 1. Проверка кнопки "Поиск в Google"
        SelenideElement buttonsContainer = $("div.FPdoLc.lJ9FBc")
                .shouldBe(visible)
                .shouldHave(attribute("class", "FPdoLc lJ9FBc"));

        buttonsContainer.$("input[value='Поиск в Google']")
                .shouldBe(visible)
                .shouldHave(attribute("type", "submit"))
                .shouldHave(attribute("value", "Поиск в Google"))
                .shouldBe(interactable);

        System.out.println("✅ Кнопка 'Поиск в Google' найдена и доступна");

        // 2. Проверяем блок навигации внизу страницы
        SelenideElement navBlock = $("div.L3eUgb > div:nth-child(6) > div")
                .shouldBe(visible);

        System.out.println("✅ Блок навигации в подвале страницы отображается");

        // 3. Проверяем кнопку "Настройки"
        SelenideElement settingsButton = navBlock.$$("a, button, [role='button']")
                .findBy(text("Настройки"))
                .shouldBe(visible)
                .shouldBe(interactable);

        System.out.println("✅ Кнопка 'Настройки' найдена и доступна");

        // 4. Проверяем выпадающее меню настроек
        settingsButton.click();

        $("#lb g-menu")
                .shouldBe(visible)
                .shouldHave(attribute("role", "menu"));

        $$("#lb g-menu [role='menuitem']")
                .shouldHave(CollectionCondition.sizeGreaterThan(2));

        System.out.println("✅ Меню настроек открыто и содержит пункты");
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
