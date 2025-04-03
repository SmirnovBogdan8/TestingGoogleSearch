package pages;

import com.codeborne.selenide.SelenideElement;
import utils.GoogleUtils;

import static com.codeborne.selenide.Selenide.$;

public class GoogleSearchPage {
    private final SelenideElement searchInput = $("#APjFqb");
    private final SelenideElement searchButton = $("div.FPdoLc.lJ9FBc input[value='Поиск в Google']");
    private final SelenideElement logo = $("#logo > svg");

    public void searchFor(String query) {
        searchInput.setValue(query).pressEnter();
        GoogleUtils.waitDefault();
    }

    public void clickLogo() {
        logo.click();
        GoogleUtils.waitDefault();
    }

    public SelenideElement getSearchButton() {
        return searchButton;
    }

    public SelenideElement getSearchInput() {
        return searchInput;
    }
}