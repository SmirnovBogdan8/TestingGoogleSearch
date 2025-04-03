import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки функционала поисковой системы Google
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка функционала поисковой системы Google")
public class BaseTest {

    private static final String SEARCH_QUERY = "Selenide";
    private static final int WAIT_TIMEOUT = 3000;

    /**
     * Инициализация тестового окружения
     */
    @BeforeAll
    public static void setUp() {
        open("https://www.google.com");
    }

    /**
     * Проверка основного функционала поиска
     */
    @Test
    @Order(1)
    @DisplayName("Автоматизация поиска в Google")
    public void searchAutomation() {
        performSearch();
        ElementsCollection searchResults = getSearchResults();
        verifySearchResults(searchResults, "первой");
    }

    /**
     * Проверка пагинации в результатах поиска
     */
    @Test
    @Order(2)
    @DisplayName("Проверка пагинации")
    public void checkPagination() {
        navigateToPage();
        ElementsCollection pageResults = getSearchResults();
        verifySearchResults(pageResults, "второй");
    }

    /**
     * Проверка вкладки с изображениями
     */
    @Test
    @Order(3)
    @DisplayName("Проверка вкладки 'Картинки'")
    public void testImagesTab() {
        switchToTab();
        verifyImages();
    }

    /**
     * Проверка основных элементов интерфейса
     */
    @Test
    @Order(4)
    @DisplayName("Проверка основных элементов на странице результатов поиска")
    public void checkPageElements() {
        verifySearchButton();
        verifyNavigationBlock();
        verifySettingsMenu();
    }

    /**
     * Завершение тестового окружения
     */
    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }

    // ===== Вспомогательные методы =====

    /**
     * Выполняет поиск по указанному запросу
     */
    private void performSearch() {
        $("#APjFqb").setValue(BaseTest.SEARCH_QUERY).pressEnter();
        sleep(WAIT_TIMEOUT);
        verifyCaptchaPassed();
    }

    /**
     * Получает результаты поиска
     * @return коллекция элементов с результатами
     */
    private ElementsCollection getSearchResults() {
        return $$("#rso h3")
                .filter(visible)
                .filterBy(attribute("class", "LC20lb MBeuO DKV0Md"));
    }

    /**
     * Проверяет наличие и корректность результатов поиска
     * @param results коллекция результатов
     * @param pageName название страницы для сообщений
     */
    private void verifySearchResults(ElementsCollection results, String pageName) {
        assertFalse(results.isEmpty(), "❌ Нет подходящих результатов на " + pageName + " странице");
        System.out.println("✅ Найдены подходящие результаты на " + pageName + " странице");

        System.out.println("\n📄 Найденные заголовки:");
        results.forEach(result -> {
            String title = result.getText();
            System.out.println("- " + title);
            assertTrue(title.toLowerCase().contains("selenide"),
                    "❌ Заголовок не содержит 'Selenide': " + title);
        });
    }

    /**
     * Переключается на указанную вкладку
     */
    private void switchToTab() {
        $("#hdtb-sc").$$("a").findBy(text("Картинки")).click();
        sleep(WAIT_TIMEOUT);
    }

    /**
     * Проверяет отображение изображений
     */
    private void verifyImages() {
        ElementsCollection images = $$("#search img");
        assertFalse(images.isEmpty(), "❌ Не найдено изображений на странице");
        System.out.println("✅ Найдены изображения на странице");

        images.first().click();
        sleep(2000);

        assertTrue($("#Sva75c a > img").exists(),
                "❌ Не найдено увеличенного изображения");
        System.out.println("✅ Найдено увеличенное изображение");
    }

    /**
     * Переходит на указанную страницу
     */
    private void navigateToPage() {
        $$("#botstuff a[aria-label='Page " + 2 + "']")
                .findBy(text(String.valueOf(2)))
                .click();
        sleep(WAIT_TIMEOUT);

        assertEquals(String.valueOf(2), $("td.YyVfkd.NKTSme").getText(),
                "❌ Не удалось перейти на страницу " + 2);
        System.out.println("✅ Успешно перешли на страницу " + 2);
    }

    /**
     * Проверяет кнопку поиска
     */
    private void verifySearchButton() {
        $("#logo > svg").shouldBe(visible).click();
        sleep(WAIT_TIMEOUT);
        SelenideElement searchButton = $("div.FPdoLc.lJ9FBc")
                .$("input[value='Поиск в Google']");
        assertTrue(searchButton.exists(), "❌ Кнопка 'Поиск в Google' не найдена");
        System.out.println("✅ Кнопка 'Поиск в Google' найдена");
    }

    /**
     * Проверяет блок навигации
     */
    private void verifyNavigationBlock() {
        SelenideElement navBlock = $("div.L3eUgb > div:nth-child(6) > div");
        assertTrue(navBlock.exists(), "❌ Блок навигации не найден");
        System.out.println("✅ Блок навигации в подвале страницы найден");
    }

    /**
     * Проверяет меню настроек
     */
    private void verifySettingsMenu() {
        SelenideElement settingsButton = $("div.L3eUgb > div:nth-child(6) > div")
                .$$("a, button, [role='button']")
                .findBy(text("Настройки"));
        assertTrue(settingsButton.exists(), "❌ Кнопка 'Настройки' не найдена");
        System.out.println("✅ Кнопка 'Настройки' найдена");

        settingsButton.click();
        assertTrue($("#lb g-menu").exists(), "❌ Список настроек не найден");
        System.out.println("✅ Выпадающий список настроек найден");
    }

    /**
     * Проверяет и обрабатывает капчу
     */
    private void verifyCaptchaPassed() {
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
                return;
            }
            else {
                System.out.println("⏳ Ожидаем загрузки страницы...");
                sleep(3000);
            }
        }

        fail("❌ Капча не пройдена или страница не загрузилась");
    }
}