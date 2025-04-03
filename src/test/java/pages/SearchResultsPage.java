package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchResultsPage extends BasePage {
    private final SelenideElement navigationBlock = $("div.L3eUgb > div:nth-child(6) > div");
    private final SelenideElement activePageNumber = $("td.YyVfkd.NKTSme");
    private final SelenideElement settingsButton = $("div.L3eUgb > div:nth-child(6) > div")
            .$$("a, button, [role='button']")
            .findBy(text("Настройки"));
    private final SelenideElement settingsMenu = $("#lb g-menu");

    public ElementsCollection getSearchResults() {
        return $$("#rso h3")
                .filter(visible)
                .filterBy(attribute("class", "LC20lb MBeuO DKV0Md"));
    }

    public void navigateToPage(int pageNumber) {
        $$("#botstuff a[aria-label='Page " + pageNumber + "']")
                .findBy(text(String.valueOf(pageNumber)))
                .click();
        waitDefault();
    }

    public SelenideElement getActivePageNumber() {
        return activePageNumber;
    }

    public SelenideElement getNavigationBlock() {
        return navigationBlock;
    }

    public void openSettingsMenu() {
        settingsButton.click();
    }

    public SelenideElement getSettingsMenu() {
        return settingsMenu;
    }
}