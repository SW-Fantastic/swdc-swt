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

    public void setName(String name) {
        this.name = name;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "TestCell{" +
                "name='" + name + '\'' +
                ", prop='" + prop + '\'' +
                '}';
    }
}
