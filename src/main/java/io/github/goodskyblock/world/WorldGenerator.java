package io.github.goodskyblock.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

import static org.bukkit.Bukkit.createChunkData;

public class WorldGenerator extends ChunkGenerator {


    @Override
    public ChunkGenerator.ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biome) {
        ChunkGenerator.ChunkData chunk = createChunkData(world);

        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                for (int Y = 0; Y < 256; Y++) {
                    chunk.setBlock(X, Y, Z, Material.AIR);
                }
            }
        }
        return chunk;
    }


}
