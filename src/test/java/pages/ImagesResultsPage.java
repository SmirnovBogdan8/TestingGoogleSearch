package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import utils.GoogleUtils;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ImagesResultsPage {
    private final SelenideElement imagesTab = $("#hdtb-sc").$$("a").findBy(text("Картинки"));
    private final SelenideElement enlargedImage = $("#Sva75c a > img");

    public void switchToImagesTab() {
        imagesTab.click();
        GoogleUtils.waitDefault();
    }

    public ElementsCollection getImages() {
        return $$("#search img");
    }

    public void clickFirstImage() {
        getImages().first().click();
        GoogleUtils.waitDefault();
    }

    public SelenideElement getEnlargedImage() {
        return enlargedImage;
    }
}