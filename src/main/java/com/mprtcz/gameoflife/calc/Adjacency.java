package com.mprtcz.gameoflife.calc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Michal_Partacz
 */
public enum Adjacency {
    LEFT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            int[] coords = Adjacency.getXYCoordinates(index, boardWidth);
            return Adjacency.getIndexFromXY(coords[0] - 1, coords[1], boardWidth);
        }
    },
    RIGHT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            int[] coords = Adjacency.getXYCoordinates(index, boardWidth);
            return Adjacency.getIndexFromXY(coords[0] + 1, coords[1], boardWidth);
        }
    },
    ABOVE {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            int[] coords = Adjacency.getXYCoordinates(index, boardWidth);
            return Adjacency.getIndexFromXY(coords[0], coords[1] - 1, boardWidth);
        }
    },
    BELOW {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            int[] coords = Adjacency.getXYCoordinates(index, boardWidth);
            return Adjacency.getIndexFromXY(coords[0], coords[1] + 1, boardWidth);
        }
    },
    UPPER_LEFT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            return ABOVE.getAdjacentIndex(LEFT.getAdjacentIndex(index, boardWidth), boardWidth);
        }
    },
    UPPER_RIGHT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            return ABOVE.getAdjacentIndex(RIGHT.getAdjacentIndex(index, boardWidth), boardWidth);
        }
    },
    LOWER_LEFT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            return BELOW.getAdjacentIndex(LEFT.getAdjacentIndex(index, boardWidth), boardWidth);
        }
    },
    LOWER_RIGHT {
        @Override
        public int getAdjacentIndex(int index, int boardWidth) {
            return BELOW.getAdjacentIndex(RIGHT.getAdjacentIndex(index, boardWidth), boardWidth);
        }
    };

    public abstract int getAdjacentIndex(int index, int boardWidth);

    public static int[] getXYCoordinates(int index, int width) {
        int[] coords = new int[2];
        coords[0] = index % width;
        coords[1] = index / width;
        return coords;
    }

    public static int getIndexFromXY(int x, int y, int width) {
        if(x < 0 || y < 0 ||
                x >= width || y >= width) {return -1;}
        return y * width + x;
    }

    public static List<Integer> getAllAdjacentIndexesOf(int index, int boardIndex) {
        return Stream.of(values()).map(adjacency ->
                adjacency.getAdjacentIndex(index, boardIndex))
                .collect(Collectors.toList());
    }
}
