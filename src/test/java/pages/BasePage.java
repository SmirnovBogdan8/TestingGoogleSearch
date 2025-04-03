package pages;

import utils.GoogleUtils;

public abstract class BasePage {
    protected void takeScreenshot(String name) {
        GoogleUtils.takeScreenshot(name);
    }

    protected void waitDefault() {
        GoogleUtils.waitDefault();
    }
}