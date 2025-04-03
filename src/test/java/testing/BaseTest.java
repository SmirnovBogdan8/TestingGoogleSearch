package testing;

import pages.*;
import utils.GoogleUtils;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка функционала поисковой системы Google")
public class BaseTest {
    private static final String SEARCH_QUERY = "Selenide";
    private static GoogleSearchPage googleSearchPage;
    private static SearchResultsPage searchResultsPage;
    private static ImagesResultsPage imagesResultsPage;

    @BeforeAll
    public static void setUp() {
        GoogleUtils.initScreenshotsDir();
        googleSearchPage = open("https://www.google.com", GoogleSearchPage.class);
        searchResultsPage = new SearchResultsPage();
        imagesResultsPage = new ImagesResultsPage();
    }

    @Test
    @Order(1)
    @DisplayName("Автоматизация поиска в Google")
    public void searchAutomation() {
        googleSearchPage.searchFor(SEARCH_QUERY);
        GoogleUtils.verifyCaptchaPassed();
        GoogleUtils.takeScreenshot("after_search");

        ElementsCollection searchResults = searchResultsPage.getSearchResults();
        verifySearchResults(searchResults, "первой");
    }

    @Test
    @Order(2)
    @DisplayName("Проверка пагинации")
    public void checkPagination() {
        searchResultsPage.navigateToPage(2);
        ElementsCollection pageResults = searchResultsPage.getSearchResults();
        verifySearchResults(pageResults, "второй");
    }

    @Test
    @Order(3)
    @DisplayName("Проверка вкладки 'Картинки'")
    public void testImagesTab() {
        imagesResultsPage.switchToImagesTab();
        verifyImages();
    }

    @Test
    @Order(4)
    @DisplayName("Проверка основных элементов на странице результатов поиска")
    public void checkPageElements() {
        verifySearchInput();
        verifySearchButton();
        verifyNavigationBlock();
        verifySettingsMenu();
    }

    @AfterAll
    public static void tearDown() {
        closeWebDriver();
    }

    // ===== Вспомогательные методы проверок =====
    private void verifySearchResults(ElementsCollection results, String pageName) {
        assertFalse(results.isEmpty(), "❌ Нет подходящих результатов на " + pageName + " странице");
        GoogleUtils.takeScreenshot("search_results_page_" + pageName);
        System.out.println("✅ Найдены подходящие результаты на " + pageName + " странице");

        System.out.println("\n📄 Найденные заголовки:");
        results.forEach(result -> {
            String title = result.getText();
            System.out.println("- " + title);
            assertTrue(title.toLowerCase().contains("selenide"),
                    "❌ Заголовок не содержит 'Selenide': " + title);
        });
    }

    private void verifyImages() {
        ElementsCollection images = imagesResultsPage.getImages();
        assertFalse(images.isEmpty(), "❌ Не найдено изображений на странице");
        GoogleUtils.takeScreenshot("images_tab");
        System.out.println("✅ Найдены изображения на странице");

        imagesResultsPage.clickFirstImage();
        assertTrue(imagesResultsPage.getEnlargedImage().exists(),
                "❌ Не найдено увеличенного изображения");
        GoogleUtils.takeScreenshot("enlarged_image");
        System.out.println("✅ Найдено увеличенное изображение");
    }

    private void verifySearchButton() {
        googleSearchPage.clickLogo();
        assertTrue(googleSearchPage.getSearchButton().exists(),
                "❌ Кнопка 'Поиск в Google' не найдена");
        GoogleUtils.takeScreenshot("search_button");
        System.out.println("✅ Кнопка 'Поиск в Google' найдена");
    }

    private void verifyNavigationBlock() {
        assertTrue(searchResultsPage.getNavigationBlock().exists(),
                "❌ Блок навигации не найден");
        GoogleUtils.takeScreenshot("navigation_block");
        System.out.println("✅ Блок навигации в подвале страницы найден");
    }

    private void verifySearchInput() {
        assertTrue(googleSearchPage.getSearchInput().exists(),
                "❌ Строка поиска не найдена");
        GoogleUtils.takeScreenshot("search_input");
        System.out.println("✅ Строка поиска найдена");
    }

    private void verifySettingsMenu() {
        searchResultsPage.openSettingsMenu();
        assertTrue(searchResultsPage.getSettingsMenu().exists(),
                "❌ Список настроек не найден");
        GoogleUtils.takeScreenshot("settings_menu_opened");
        System.out.println("✅ Выпадающий список настроек найден");
    }
}