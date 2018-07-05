package com.mprtcz.gameoflife.calc;


import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author mprtcz
 */
@Test
public class AdjacencyTest {

    public void testGetXYCoordinates() throws Exception {
        int x = 1;
        int y = 1;
        int index = 4;
        int size = 3;
        int[] result = Adjacency.getXYCoordinates(index, size);
        assertEquals(result[0], x);
        assertEquals(result[y], y);
    }

    public void testGetIndexFromXY() throws Exception {
        int x = 1;
        int y = 1;
        int index = 4;
        int size = 3;
        int result = Adjacency.getIndexFromXY(x, y, size);
        assertEquals(result, index);
    }

    public void testGetAllAdjacentIndexesOf() throws Exception {
        int index = 4;
        int size = 3;
        List<Integer> result = Adjacency.getAllAdjacentIndexesOf(index, size);
        Collections.sort(result);
        List<Integer> expectedResult = Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8);
        assertEquals(expectedResult.toString(), result.toString());
        assertEquals(expectedResult, result);
    }

    public void testGetAllAdjacentIndexesOf_10size() throws Exception {
        int index = 23;
        int size = 10;
        List<Integer> result = Adjacency.getAllAdjacentIndexesOf(index, size);
        Collections.sort(result);
        List<Integer> expectedResult = Arrays.asList(12, 13, 14, 22, 24, 32, 33, 34);
        assertEquals(expectedResult.toString(), result.toString());
        assertEquals(expectedResult, result);
    }

}