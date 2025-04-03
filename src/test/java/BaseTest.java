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

        // Ожидаем, пока пользователь не пройдет капчу вручную
        boolean captchaPassed = false;
        while (!captchaPassed) {
            try {
                if ($("#logo > svg").exists()) {
                    captchaPassed = true;
                    System.out.println("✅ Капча пройдена, продолжаем тест");
                } else {
                    System.out.println("⏳ Ожидаем, пока пользователь не пройдет капчу вручную...");
                    sleep(5000);
                }
            } catch (Throwable e) {
                System.out.println("⚠ Возможно, появилась капча. Пожалуйста, пройдите её вручную!");
                sleep(10000);
            }
        }

        // После прохождения капчи продолжаем проверки
        if ($("#logo > svg").exists()) {
            System.out.println("✅ Иконка найдена!");
        } else {
            System.out.println("❌ Иконка не найдена!");
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
