package org.swdc.dsl;

public class TestCell {

    private String name;

    private String prop;

    public TestCell(String name,String prop) {
        this.name = name;
        this.prop = prop;
    }

    public String getName() {
        return name;
    }

    public String getProp() {
        return prop;
    }
}
