package io.github.goodskyblock;

import io.github.goodskyblock.cache.IslandCache;
import io.github.goodskyblock.storage.Storage;
import io.github.goodskyblock.storage.implementations.YamlStorage;
import io.github.goodskyblock.world.WorldManager;
import io.github.goodskyblock.world.grid.Grid;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skyblock extends JavaPlugin {
    private Storage storage;
    private IslandCache islandCache;
    private WorldManager worldManager;
    private Grid grid;

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        this.storage = new YamlStorage();
        this.worldManager = new WorldManager();
        this.grid = new Grid();
        this.islandCache = new IslandCache(this, this.storage);
    }

    @Override
    public void onDisable() {
    }

    public Storage getStorage() {
        return this.storage;
    }

    public IslandCache getIslandCache() {
        return this.islandCache;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }
}
