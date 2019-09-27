package io.github.goodskyblock;

import io.github.goodskyblock.cache.IslandCache;
import io.github.goodskyblock.config.SettingsKeys;
import io.github.goodskyblock.config.config.Config;
import io.github.goodskyblock.config.config.SimpleConfig;
import io.github.goodskyblock.storage.Storage;
import io.github.goodskyblock.storage.implementations.YamlStorage;
import io.github.goodskyblock.world.WorldManager;
import io.github.goodskyblock.world.grid.Grid;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skyblock extends JavaPlugin {
    private Storage storage;
    private IslandCache islandCache;
    private Grid grid;

    private static WorldManager worldManager;

    private Config settingsConfig;

    @Override
    public void onLoad() {
        this.settingsConfig = new SimpleConfig(this, new SettingsKeys(), path -> path.resolve("settings.yml"));
    }

    @Override
    public void onEnable() {
        new Constants(this.settingsConfig.get(SettingsKeys.GRID_SIZE), this.settingsConfig.get(SettingsKeys.ISLAND_DIAMETER));

        this.storage = new YamlStorage();
        worldManager = new WorldManager();
        this.grid = new Grid();
        this.islandCache = new IslandCache(this, this.storage);
    }

    @Override
    public void onDisable() {
    }

    public static WorldManager getWorldManager() {
        return worldManager;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public IslandCache getIslandCache() {
        return this.islandCache;
    }

    public Config getSettingsConfig() {
        return this.settingsConfig;
    }
}
