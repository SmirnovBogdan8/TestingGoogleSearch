import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {
    /**
     *
     */
    @BeforeAll
    public static void setUp() {
        open("https://www.google.com");
    }

    /**
     *
     */
    @Test
    public void searchAutomation() {
        $("#APjFqb").setValue("Selenide").pressEnter();

        try {
            // Ждем появления результатов
            $$("#search .g").shouldHave(sizeGreaterThanOrEqual(10));


        } catch (Throwable e) {
            System.out.println("Возможно, появилась капча. Проверьте вручную!");
            sleep(3000);
        }

        // Проверяем первые 7 результатов через for-loop
        for (int i = 0; i < 7; i++) {
            String title = $$("#search").get(i).$("h3").getText().toLowerCase();
            assertTrue(title.contains("selenide"),
                    "Заголовок результата #" + (i+1) + " не содержит 'selenide': " + title);
        }
    }

    /**
     *
     */
    @Test
    public void checkPagination() {

    }

    /**
     *
     */
    @Test
    public void checkImage() {

    }

    /**
     *
     */
    @Test
    public void checkElements() {

    }

    /**
     *
     */
    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
