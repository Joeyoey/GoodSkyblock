package io.github.goodskyblock.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class WorldManager {

    private World islandWorld = null;

    public World getIslandWorld() {
        if (this.islandWorld == null) {
            this.islandWorld = WorldCreator.name("GoodSkyblockIslandWorld")
                    .type(WorldType.FLAT)
                    .environment(World.Environment.NORMAL)
                    .generator(new WorldGenerator())
                    .createWorld();
        }
        return this.islandWorld;
    }


}
