package io.github.goodskyblock.world.grid;

import io.github.goodskyblock.island.Island;

public class GridManager {
    private int nextIslandX, nextIslandZ, nextIndexX, nextIndexZ;
    private int islandDistance;
    private Grid grid;

    public GridManager(Grid grid) {
        this.grid = grid;
    }

    /**
     * This looks through the grid and finds the first open spot on the grid.
     */
    private void findNextIndexes() {
        for (int i = 0; i < this.grid.getGrid().length; i++) {
            for (int j = 0; j < this.grid.getGrid()[i].length; j++) {
                if (this.grid.getGrid()[i][j] == null) {
                    this.nextIndexX = i;
                    this.nextIndexZ = j;
                    this.nextIslandX = i * this.islandDistance;
                    this.nextIslandZ = j * this.islandDistance;
                    return;
                }
            }
        }
    }

    public boolean addNewIslandToGrid(Island island) {


        return false;
    }

}
