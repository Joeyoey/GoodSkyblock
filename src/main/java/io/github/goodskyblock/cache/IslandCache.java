package io.github.goodskyblock.cache;

import io.github.goodskyblock.island.Island;
import io.github.goodskyblock.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class IslandCache extends CacheWrapper<UUID, Island> {
    private final Plugin plugin;
    private final Storage storage;

    public IslandCache(Plugin plugin, Storage storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public void loadIslandIntoCache() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> storage.loadIslandsIntoCache(super.getCache()));
    }
}
