package io.github.goodskyblock.world.grid;


import io.github.goodskyblock.Constants;
import io.github.goodskyblock.annotations.NonNull;
import io.github.goodskyblock.config.SettingsKeys;
import io.github.goodskyblock.island.Island;

public class Grid {
    private final int gridSize = Constants.getGridSize();
    private final Island[][] grid = new Island[this.gridSize][this.gridSize];


    /**
     * This gets the island grid.
     *
     * @return the island grid.
     */
    @NonNull
    public Island[][] getGrid() {
        return this.grid;
    }
}
