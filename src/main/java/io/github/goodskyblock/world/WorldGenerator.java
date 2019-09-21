package io.github.goodskyblock.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    /**
     * This is the island chunk generator, this basically generates a simple void world.
     * @param world the world that's being generated.
     * @param random the random variable, this is not used.
     * @param chunkX the X-Coordinate of the chunk.
     * @param chunkZ the Z-Coordinate of the chunk.
     * @param biome the instance of the biome grid that shows what biomes are where, this is not used.
     *
     * @return this returns chunk data that is used to build the chunks.
     */
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
