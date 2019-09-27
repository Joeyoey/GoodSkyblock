package io.github.goodskyblock;

public class Constants {
    private static int gridSize;
    private static int islandDiameter;

    Constants(int gridSize, int islandDiameter) {
        Constants.gridSize = gridSize;
        Constants.islandDiameter = islandDiameter;
    }

    public static int getGridSize() {
        return gridSize;
    }

    public static int getIslandDiameter() {
        return islandDiameter;
    }
}
