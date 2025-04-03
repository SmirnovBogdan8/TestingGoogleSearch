# Тесты для поисковой системы Google

Проект содержит автоматизированные тесты для проверки функционала поисковой системы Google с использованием Selenide и JUnit 5.

## 📋 Содержание
1. [Технологии](#технологии)
2. [Требования](#требования)
3. [Запуск тестов](#запуск-тестов)
4. [Структура тестов](#структура-тестов)
5. [Особенности](#особенности)

## 🛠 Технологии
- **Java**
- **Selenide** - для автоматизации браузера
- **JUnit** - фреймворк для тестирования
- **Gradle** - сборка проекта

## 📝 Требования
1. JDK
2. Gradle
3. Chrome браузер
4. Доступ к https://www.google.com

## 🚀 Запуск тестов

### Стандартный запуск тестов:
```bash
./gradlew clean test
```

## 🧪 Структура тестов

### Основные тест-кейсы:
1. **Автоматизация поиска в Google**
    - Проверка загрузки страница с результатами поиска
    - Проверка заголовков на первой странице

2. **Проверка пагинации**
    - Проверка перехода на вторую страницу результатов поиска
    - Проверка отображения результаты поиска

3. **Проверка изображений на странице поиска**
    - Проверка вкладки "Изображения"
    - Проверка изображений

4. **Проверка отображения других элементов на странице**
    - Проверка кнопки поиска и кнопка "Поиск в Google"
    - Проверка меню настроек

## ⚠️ Особенности
1. **Обработка капчи**:
    - При обнаружении капчи тест приостанавливается
    - Требуется ручной ввод капчи в течение 30 секунд

2. **Тайминги**:
    - Используются неявные ожидания Selenide
    - Для стабильности добавлены явные паузы в критичных местах

3. **Селекторы**:
    - Используются как CSS-селекторы
    - Применяются текстовые локаторы и классы

4. **Скриншоты**
    - В папке 