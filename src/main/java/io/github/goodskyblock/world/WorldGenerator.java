package io.github.goodskyblock.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    @Override
    public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biome) {
        ChunkGenerator.ChunkData chunk = super.createChunkData(world);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    chunk.setBlock(x, y, z, Material.AIR);
                }
            }
        }
        return chunk;
    }
}
