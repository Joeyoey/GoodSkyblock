package io.github.goodskyblock.storage;

import com.google.common.cache.Cache;
import io.github.goodskyblock.island.Island;

import java.util.UUID;

public abstract class Storage {

    public abstract void loadIslandsIntoCache(Cache cache);

    protected abstract Island loadIsland(UUID uuid);

    protected abstract void saveIsland(Island island);
}
