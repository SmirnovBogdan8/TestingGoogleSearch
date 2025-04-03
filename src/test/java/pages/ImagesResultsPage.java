package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import utils.GoogleUtils;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object класс для страницы с результатами поиска изображений Google.
 * Содержит элементы и методы для работы с вкладкой "Картинки".
 */
public class ImagesResultsPage {
    private final SelenideElement imagesTab = $("#hdtb-sc").$$("a").findBy(text("Картинки"));
    private final SelenideElement enlargedImage = $("#Sva75c a > img");

    /**
     * Переключается на вкладку "Картинки".
     */
    public void switchToImagesTab() {
        imagesTab.click();
        GoogleUtils.waitDefault();
    }

    /**
     * Возвращает коллекцию найденных изображений.
     *
     * @return коллекция элементов изображений
     */
    public ElementsCollection getImages() {
        return $$("#search img");
    }

    /**
     * Кликает по первому изображению в результатах поиска.
     */
    public void clickFirstImage() {
        getImages().first().click();
        GoogleUtils.waitDefault();
    }

    /**
     * Возвращает элемент увеличенного изображения.
     *
     * @return элемент увеличенного изображения
     */
    public SelenideElement getEnlargedImage() {
        return enlargedImage;
    }
}