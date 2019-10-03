package io.github.goodskyblock.services;

import io.github.goodskyblock.Skyblock;
import io.github.goodskyblock.utilobjects.FakeBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BlockArea {

    private Skyblock plugin;

    public BlockArea(Skyblock instance) { this.plugin = instance; }


    public void setSquare(Material material, Location location1, Location location2) {
        World world = location1.getWorld();
        int highestX = Math.max(location2.getBlockX(), location1.getBlockX());
        int lowestX = Math.min(location2.getBlockX(), location1.getBlockX());

        int highestY = Math.max(location2.getBlockY(), location1.getBlockY());
        int lowestY = Math.min(location2.getBlockY(), location1.getBlockY());

        int highestZ = Math.max(location2.getBlockZ(), location1.getBlockZ());
        int lowestZ = Math.min(location2.getBlockZ(), location1.getBlockZ());

        new BukkitRunnable() {
            int lastRun = 0;

            @Override
            public void run() {
                HashMap<Chunk, HashSet<Block>> chunkSplitter = new HashMap<>();
                for (int x = lowestX; x <= highestX; x++) {
                    for (int z = lowestZ; z <= highestZ; z++) {
                        for (int y = lowestY; y <= highestY; y++) {
                            Location location = new Location(world, x, y, z);
                            Chunk chunk = location.getChunk();
                            Block block = location.getBlock();
                            if (chunkSplitter.containsKey(chunk)) {
                                HashSet<Block> blockSet = chunkSplitter.get(chunk);
                                if (material != block.getType()) {
                                    blockSet.add(block);
                                    chunkSplitter.replace(chunk, blockSet);
                                }
                            } else {
                                HashSet<Block> blockSet = new HashSet<>();
                                if (material != block.getType()) {
                                    blockSet.add(block);
                                    chunkSplitter.put(chunk, blockSet);
                                }
                            }
                        }
                    }
                }
                for (HashSet<Block> entry : chunkSplitter.values()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Block block : entry) {
                                block.setType(material);
                            }
                        }
                    }.runTaskLater(plugin, lastRun + 3);
                    lastRun++;
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public Map<Vector, FakeBlock> toVectorMap(Location center, Location corner1, Location corner2, boolean relative) {
        Map<Vector, FakeBlock> blocks = new HashMap<>();

        Long start = System.currentTimeMillis();


        final World world = center.getWorld();
        int highestX = Math.max((int)corner2.getX(), (int)corner1.getX());
        int lowestX = Math.min((int)corner2.getX(), (int)corner1.getX());

        int highestY = Math.max((int)corner2.getY(), (int)corner1.getY());
        int lowestY = Math.min((int)corner2.getY(), (int)corner1.getY());

        int highestZ = Math.max((int)corner2.getZ(), (int)corner1.getZ());
        int lowestZ = Math.min((int)corner2.getZ(), (int)corner1.getZ());

        if (!relative) {
            center = new Location(center.getWorld(), lowestX, lowestY, lowestZ);
        }

        for (int x = lowestX; x <= highestX; x++) {
            for (int z = lowestZ; z <= highestZ; z++) {
                for (int y = lowestY; y <= highestY; y++) {
                    Location location = new Location(world, x, y, z);
                    Vector middle = center.toVector().clone();
                    if (location.getBlock().getType().equals(Material.CHEST) || location.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
                        ItemStack[] contents = ((org.bukkit.block.Chest) location.getBlock().getState()).getInventory().getContents();
                        FakeBlock block = new FakeBlock(location.getBlock().getType(), location.getBlock().getData(), contents);
                        Vector result = middle.subtract(location.toVector());
                        blocks.put(result, block);
                    } else {
                        @SuppressWarnings("deprecation")
                        FakeBlock block = new FakeBlock(location.getBlock().getType(), location.getBlock().getData());
                        Vector result = middle.subtract(location.toVector());
                        blocks.put(result, block);
                    }
                }
            }
        }

        Bukkit.getLogger().severe("done took: " + (System.currentTimeMillis() - start) + "ms");

        return blocks;

    }

/*
    public void saveBlockArea(Location center, Location corner1, Location corner2, String name, boolean relative) {
        plugin.getAreas().put(name.toUpperCase(), toVectorMap(center, corner1, corner2, relative));
        Bukkit.getLogger().severe(plugin.getAreas().size() + " number of blockareas, " + plugin.getAreas().get(name.toUpperCase()).size() + " number of objects in subMap");
    }
*/

    public void pasteBlockArea(Map<Vector, FakeBlock> map, Location center, int blocksPerPaste) {
        int i = 1;
        int j = 0;
        int count = 40;
        if (blocksPerPaste != 0) {
            count = blocksPerPaste;
        }
        if (blocksPerPaste == -1) {
            count = 1000;
        }
        for (Map.Entry<Vector, FakeBlock> a : map.entrySet()) {
            Vector middle = center.toVector().clone();
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (a.getValue().isChest()) {
                        middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().setType(a.getValue().getMat(), true);
                        Levelled levelledData = (Levelled) middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().getBlockData();
                        levelledData.setLevel(a.getValue().getData());
                        middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().setBlockData(levelledData, true);
                        Chest chest = (Chest) middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().getState();
                        chest.getInventory().setContents(a.getValue().getItemStacks());
                        chest.update();
                    } else {
                        middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().setType(a.getValue().getMat(), true);
                        Levelled levelledData = (Levelled) middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().getBlockData();
                        levelledData.setLevel(a.getValue().getData());
                        middle.subtract(a.getKey()).toLocation(center.getWorld()).getBlock().setBlockData(levelledData, true);
                    }
                }

            }.runTaskLater(plugin, i);
            if (j % count == 0) {
                i++;
            }
            j++;
        }
    }


    public void pasteBlockArea(Map<Vector, FakeBlock> map, int x, int y, int z, String world, int blocksPerPaste) {
        int i = 1;
        int j = 0;
        Vector center = new Vector(x, y, z);
        World worlda = Bukkit.getWorld(world);
        int count = 40;
        if (blocksPerPaste != 0) {
            count = blocksPerPaste;
        }
        if (blocksPerPaste == -1) {
            count = 1000;
        }
        for (Map.Entry<Vector, FakeBlock> a : map.entrySet()) {
            Vector middle = center.clone();
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (a.getValue().isChest()) {
                        middle.subtract(a.getKey()).toLocation(worlda).getBlock().setType(a.getValue().getMat(), true);
                        Levelled levelledData = (Levelled) middle.subtract(a.getKey()).toLocation(worlda).getBlock().getBlockData();
                        levelledData.setLevel(a.getValue().getData());
                        middle.subtract(a.getKey()).toLocation(worlda).getBlock().setBlockData(levelledData, true);
                        Chest chest = (Chest) middle.subtract(a.getKey()).toLocation(worlda).getBlock().getState();
                        chest.getInventory().setContents(a.getValue().getItemStacks());
                        chest.update();
                    } else {
                        middle.subtract(a.getKey()).toLocation(worlda).getBlock().setType(a.getValue().getMat(), true);
                        Levelled levelledData = (Levelled) middle.subtract(a.getKey()).toLocation(worlda).getBlock().getBlockData();
                        levelledData.setLevel(a.getValue().getData());
                        middle.subtract(a.getKey()).toLocation(worlda).getBlock().setBlockData(levelledData, true);
                    }
                }

            }.runTaskLater(plugin, i);
            if (j % count == 0) {
                i++;
            }
            j++;
        }
    }
}
