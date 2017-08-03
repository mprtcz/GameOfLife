package com.mprtcz.gameoflife.calc;

/**
 * @author Michal_Partacz
 */
public class Adjacency {

    public static int[] getXYCoordinates(int index, int width) {
        int[] coords = new int[2];
        coords[0] = index % width;
        coords[1] = index / width;
        return coords;
    }

    public static int getIndexFromXY(int x, int y, int width) {
        return y * width + x;
    }
}
