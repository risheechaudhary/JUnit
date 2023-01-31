package junitdemo;

public enum WebTechnology {
    BACKBONEJS("Backbone.js"),
    ANGULARJS("AngularJS");

    private String techName;

    WebTechnology(String techName) {
        this.techName = techName;
    }

    public String getTechName() {
        return techName;
    }
}
