package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class GoogleSearchPage extends BasePage {
    private final SelenideElement searchInput = $("#APjFqb");
    private final SelenideElement searchButton = $("div.FPdoLc.lJ9FBc input[value='Поиск в Google']");
    private final SelenideElement logo = $("#logo > svg");

    public void searchFor(String query) {
        searchInput.setValue(query).pressEnter();
        waitDefault();
    }

    public void clickLogo() {
        logo.click();
        waitDefault();
    }

    public SelenideElement getSearchButton() {
        return searchButton;
    }
}