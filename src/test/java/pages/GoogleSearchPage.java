package pages;

import com.codeborne.selenide.SelenideElement;
import utils.GoogleUtils;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object класс для главной страницы поиска Google.
 * Содержит элементы и методы для взаимодействия с основной поисковой страницей.
 */
public class GoogleSearchPage {
    private final SelenideElement searchInput = $("#APjFqb");
    private final SelenideElement searchButton = $("div.FPdoLc.lJ9FBc input[value='Поиск в Google']");
    private final SelenideElement logo = $("#logo > svg");

    /**
     * Выполняет поиск по указанному запросу.
     *
     * @param query поисковый запрос
     */
    public void searchFor(String query) {
        searchInput.setValue(query).pressEnter();
        GoogleUtils.waitDefault();
    }

    /**
     * Кликает по логотипу Google.
     */
    public void clickLogo() {
        logo.click();
        GoogleUtils.waitDefault();
    }

    /**
     * Возвращает элемент кнопки поиска.
     *
     * @return элемент кнопки "Поиск в Google"
     */
    public SelenideElement getSearchButton() {
        return searchButton;
    }

    /**
     * Возвращает элемент строки поиска.
     *
     * @return элемент поисковой строки
     */
    public SelenideElement getSearchInput() {
        return searchInput;
    }
}