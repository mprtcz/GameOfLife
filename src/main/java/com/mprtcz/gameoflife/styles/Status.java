package com.mprtcz.gameoflife.styles;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal_Partacz
 */
public enum Status {
    ALIVE("-fx-base: #000000;"),
    DEAD("-fx-base: #ffffff;"),
    DEFAULT("-fx-base: #ffffff;"),
    VISITED("-fx-base: #afed87;");

    private static Map<Status, Status> statuses = new HashMap<>();
    static {
        statuses.put(ALIVE, DEAD);
        statuses.put(DEAD, ALIVE);
        statuses.put(DEFAULT, ALIVE);
        statuses.put(VISITED, ALIVE);
    }

    public static Status getOppositeStatus(Status status) {
        return statuses.get(status);
    }

    String style;

    Status(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }
}
