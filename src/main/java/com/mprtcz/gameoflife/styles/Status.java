package com.mprtcz.gameoflife.styles;

/**
 * @author Michal_Partacz
 */
public enum Status {
    ALIVE("-fx-base: #000000;"),
    DEAD("-fx-base: #ffffff;"),
    DEFAULT("-fx-base: #ffffff;"),
    VISITED("-fx-base: #afed87;");

    String style;

    Status(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
