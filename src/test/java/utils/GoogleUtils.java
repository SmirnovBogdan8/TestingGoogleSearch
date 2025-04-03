package utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Screenshots;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.codeborne.selenide.Selenide.$;

public class GoogleUtils {
    private static final String SCREENSHOTS_DIR = "build/reports/tests/screenshots/";
    private static final int DEFAULT_WAIT_TIMEOUT = 3000;

    // Работа со скриншотами
    public static void initScreenshotsDir() {
        new File(SCREENSHOTS_DIR).mkdirs();
    }

    public static void takeScreenshot(String name) {
        try {
            File screenshot = Screenshots.takeScreenShotAsFile();
            File targetFile = new File(SCREENSHOTS_DIR + name + "_" + System.currentTimeMillis() + ".png");
            Assertions.assertNotNull(screenshot, "Не удалось создать скриншот");
            Files.copy(screenshot.toPath(), targetFile.toPath());
            System.out.println("📸 Скриншот сохранен: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("⚠ Не удалось сохранить скриншот: " + e.getMessage());
        }
    }

    // Работа с таймерами
    public static void waitDefault() {
        Selenide.sleep(DEFAULT_WAIT_TIMEOUT);
    }

    public static void wait(int milliseconds) {
        Selenide.sleep(milliseconds);
    }

    // Работа с капчей
    public static void verifyCaptchaPassed() {
        boolean captchaShown = false;
        int maxAttempts = 10;
        int attempt = 0;

        while (attempt < maxAttempts) {
            attempt++;

            if ($("#captcha-form").exists() || $("iframe[src*='recaptcha']").exists()) {
                if (!captchaShown) {
                    System.out.println("⚠ Обнаружена капча! Пожалуйста, пройдите её вручную.");
                    captchaShown = true;
                }
                wait(5000);
            }
            else if ($("#rso").exists()) {
                System.out.println("✅ Капча пройдена (или её не было). Страница с результатами поиска загружена.");
                return;
            }
            else {
                System.out.println("⏳ Ожидаем загрузки страницы...");
                wait(3000);
            }
        }

        Assertions.fail("❌ Капча не пройдена или страница не загрузилась");
    }
}