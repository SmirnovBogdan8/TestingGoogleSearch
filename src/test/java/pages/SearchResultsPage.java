package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import utils.GoogleUtils;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object класс для страницы результатов поиска Google.
 * Содержит элементы и методы для работы с результатами поиска и навигацией.
 */
public class SearchResultsPage {
    private final SelenideElement navigationBlock = $("div.L3eUgb > div:nth-child(6) > div");
    private final SelenideElement activePageNumber = $("td.YyVfkd.NKTSme");
    private final SelenideElement settingsButton = $("div.L3eUgb > div:nth-child(6) > div")
            .$$("a, button, [role='button']")
            .findBy(text("Настройки"));
    private final SelenideElement settingsMenu = $("#lb g-menu");

    /**
     * Возвращает коллекцию результатов поиска.
     *
     * @return коллекция элементов с результатами поиска
     */
    public ElementsCollection getSearchResults() {
        return $$("#rso h3")
                .filter(visible)
                .filterBy(attribute("class", "LC20lb MBeuO DKV0Md"));
    }

    /**
     * Переходит на указанную страницу результатов поиска.
     *
     * @param pageNumber номер страницы для перехода
     */
    public void navigateToPage(int pageNumber) {
        $$("#botstuff a[aria-label='Page " + pageNumber + "']")
                .findBy(text(String.valueOf(pageNumber)))
                .click();
        GoogleUtils.waitDefault();
    }

    /**
     * Возвращает элемент блока навигации.
     *
     * @return элемент блока навигации
     */
    public SelenideElement getNavigationBlock() {
        return navigationBlock;
    }

    /**
     * Открывает меню настроек.
     */
    public void openSettingsMenu() {
        settingsButton.click();
    }

    /**
     * Возвращает элемент меню настроек.
     *
     * @return элемент меню настроек
     */
    public SelenideElement getSettingsMenu() {
        return settingsMenu;
    }
}