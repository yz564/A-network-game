package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2MapFactoryTest {
    private void checkMap(WorldMap worldmap, String[] names, int[][] adjacency, int[] groups) {
        for (int i = 0; i < names.length; i++) {
            Territory t = worldmap.getTerritory(names[i]);
            assertEquals(names[i], t.getName());
            for (int j = 0; j < adjacency[i].length; j++) {
                Territory n = worldmap.getTerritory(names[adjacency[i][j]]);
                assertTrue(t.isAdjacentTo(n));
            }
        }
    }

    @Test
    public void test_makemap() {
        WorldMapFactory factory = new V2MapFactory();
        WorldMap map2p = factory.makeWorldMap(2);
        WorldMap map3p = factory.makeWorldMap(3);
        WorldMap map4p = factory.makeWorldMap(4);
        WorldMap map5p = factory.makeWorldMap(5);
        String[] names1 = {
            "Fuqua",
            "Law",
            "Gross Hall",
            "FFRC",
            "Bryan Center",
            "LSRC",
            "Pratt",
            "Perkins Library",
            "Duke Hospital",
            "Duke Clinics",
            "Duke Garden",
            "Duke Chapel",
            "Student Housing",
            "Wilson Gym",
            "Cameron Stadium"
        };
        int[][] adjacency1 = {
            {1, 2, 14},
            {0, 2, 4, 14},
            {0, 1, 3, 4, 5},
            {2, 4, 5, 6},
            {1, 2, 3, 6, 11, 12, 14},
            {2, 3, 6, 8},
            {3, 4, 5, 7, 8, 9, 11},
            {6, 9, 10, 11},
            {5, 6, 9},
            {6, 7, 8, 10},
            {7, 9, 11, 12},
            {4, 6, 7, 10, 12},
            {4, 10, 11, 13, 14},
            {12, 14},
            {0, 1, 4, 12, 13}
        };
        String[] names2 = {
            "Fuqua",
            "Law",
            "Gross Hall",
            "FFRC",
            "Bryan Center",
            "LSRC",
            "Pratt",
            "Perkins Library",
            "Duke Hospital",
            "Duke Clinics",
            "Duke Garden",
            "Duke Chapel",
            "Student Housing",
            "Wilson Gym",
            "Cameron Stadium",
            "Wallace Stadium"
        };
        int[][] adjacency2 = {
            {1, 2, 14},
            {0, 2, 4, 14},
            {0, 1, 3, 4, 5},
            {2, 4, 5, 6},
            {1, 2, 3, 6, 11, 12, 14},
            {2, 3, 6, 8},
            {3, 4, 5, 7, 8, 9, 11},
            {6, 9, 10, 11},
            {5, 6, 9},
            {6, 7, 8, 10},
            {7, 9, 11, 12},
            {4, 6, 7, 10, 12},
            {4, 10, 11, 13, 14},
            {12, 14, 15},
            {0, 1, 4, 12, 13, 15},
            {13, 14}
        };
        int[] groups2p = {1, 2, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1};
        int[] groups4p = {1, 1, 2, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4};
        int[] groups3p = {1, 1, 1, 2, 1, 2, 1, 3, 2, 2, 2, 3, 3, 3, 3};
        int[] groups5p = {1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 2, 5, 5, 5};
        checkMap(map2p, names2, adjacency2, groups2p);
        checkMap(map3p, names1, adjacency1, groups3p);
        checkMap(map4p, names2, adjacency2, groups4p);
        checkMap(map5p, names1, adjacency1, groups5p);
    }

    @Test
    public void test_maketestmap() {
        WorldMapFactory factory = new V2MapFactory();
        WorldMap map = factory.makeTestWorldMap();
        String[] names = {
            "Narnia",
            "Midkemia",
            "Oz",
            "Elantris",
            "Roshar",
            "Scadrial",
            "Gondor",
            "Mordor",
            "Hogwarts"
        };
        int[][] adjacency = {
            {1, 3},
            {0, 2, 3, 4},
            {1, 5, 6, 7},
            {0, 1, 4, 5},
            {3, 5, 8},
            {1, 2, 3, 4, 7, 8},
            {2, 7},
            {2, 5, 6, 8},
            {4, 5, 7}
        };
        int[] groups = {1, 1, 1, 2, 2, 2, 3, 3, 3};
        checkMap(map, names, adjacency, groups);
    }
}
