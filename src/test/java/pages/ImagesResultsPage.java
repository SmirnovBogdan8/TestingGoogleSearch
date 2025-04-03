package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ImagesResultsPage extends BasePage {
    private final SelenideElement imagesTab = $("#hdtb-sc").$$("a").findBy(text("Картинки"));
    private final SelenideElement enlargedImage = $("#Sva75c a > img");

    public void switchToImagesTab() {
        imagesTab.click();
        waitDefault();
    }

    public ElementsCollection getImages() {
        return $$("#search img");
    }

    public void clickFirstImage() {
        getImages().first().click();
        waitDefault();
    }

    public SelenideElement getEnlargedImage() {
        return enlargedImage;
    }
}