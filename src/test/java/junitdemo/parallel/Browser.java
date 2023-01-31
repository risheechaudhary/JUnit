package junitdemo.parallel;

public enum Browser {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    SAFARI("safari");

    private String browserName;

    Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getName() {
        return browserName;
    }
}
