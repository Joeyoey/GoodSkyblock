package io.github.goodskyblock.storage.implementations;

import com.google.common.cache.Cache;
import io.github.goodskyblock.island.Island;
import io.github.goodskyblock.storage.Storage;

import java.util.UUID;

public class MySqlStorage extends Storage {

    @Override
    public void loadIslandsIntoCache(Cache cache) {

    }

    @Override
    protected Island loadIsland(UUID uuid) {
        return null;
    }

    @Override
    protected void saveIsland(Island island) {

    }

    public void close() {

    }
}
