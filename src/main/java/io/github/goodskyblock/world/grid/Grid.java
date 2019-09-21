package io.github.goodskyblock.world.grid;


import io.github.goodskyblock.annotations.NonNull;
import io.github.goodskyblock.config.settings.SettingsConfig;
import io.github.goodskyblock.island.Island;

public class Grid {
    private final int gridSize = SettingsConfig.GRID_SIZE;
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
