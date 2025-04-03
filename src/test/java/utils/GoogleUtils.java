package utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.Screenshots;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.codeborne.selenide.Selenide.$;

/**
 * Утилитарный класс для работы с Google-страницами.
 * Содержит методы для работы со скриншотами, ожиданиями и капчей.
 */
public class GoogleUtils {
    private static final String SCREENSHOTS_DIR = "build/reports/tests/screenshots/";
    private static final int DEFAULT_WAIT_TIMEOUT = 3000;

    /**
     * Инициализирует директорию для сохранения скриншотов.
     * Создает папку, если она не существует.
     */
    public static void initScreenshotsDir() {
        new File(SCREENSHOTS_DIR).mkdirs();
    }

    /**
     * Создает и сохраняет скриншот с заданным именем.
     *
     * @param name базовое имя файла скриншота (без расширения)
     * @throws RuntimeException если не удалось сохранить скриншот
     */
    public static void takeScreenshot(String name) {
        try {
            File screenshot = Screenshots.takeScreenShotAsFile();
            File targetFile = new File(SCREENSHOTS_DIR + name + "_" + System.currentTimeMillis() + ".png");
            Assertions.assertNotNull(screenshot, "Не удалось создать скриншот");
            Files.copy(screenshot.toPath(), targetFile.toPath());
            System.out.println("📸 Скриншот сохранен: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("⚠ Не удалось сохранить скриншот: " + e.getMessage());
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }

    /**
     * Ожидание по умолчанию (3000 мс).
     * Используется для паузы между действиями.
     */
    public static void waitDefault() {
        Selenide.sleep(DEFAULT_WAIT_TIMEOUT);
    }

    /**
     * Ожидание заданное количество миллисекунд.
     *
     * @param milliseconds время ожидания в миллисекундах
     */
    public static void wait(int milliseconds) {
        Selenide.sleep(milliseconds);
    }

    /**
     * Проверяет, пройдена ли капча, и ожидает загрузки страницы с результатами.
     * Если обнаружена капча, выводит сообщение о необходимости ручного ввода.
     *
     * @throws AssertionError если капча не пройдена или страница не загрузилась
     *                        в течение максимального времени ожидания
     */
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