package io.github.goodskyblock.island;

import com.google.common.collect.Maps;
import io.github.goodskyblock.Skyblock;
import io.github.goodskyblock.config.settings.SettingsConfig;
import io.github.goodskyblock.services.JoLocation;
import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;

public class Island {
    private final JoLocation islandCenter;
    private JoLocation spawnLocation;
    private int islandSize;
    private UUID owner;
    private long islandworth;
    private Map<UUID, Integer> members = Maps.newHashMap();
    private Map<String, Integer> islandUpgrades = Maps.newHashMap();
    private boolean locked;

    public Island(JoLocation joLocation, UUID owner) {
        this.islandCenter = joLocation;
        this.owner = owner;
        this.members.put(owner, 0); // puts the island owner at the highest permission "0"
        this.locked = false; // locks the island initially
        this.islandSize = SettingsConfig.ISLAND_DIAMETER; // sets the island size to the default.
        this.islandworth = 0;
    }

    public Island(JoLocation islandCenter, JoLocation spawnLocation, int islandSize, UUID owner, long islandworth, Map<UUID, Integer> members, Map<String, Integer> islandUpgrades, boolean locked) {
        this.islandCenter = islandCenter;
        this.spawnLocation = spawnLocation;
        this.islandSize = islandSize;
        this.owner = owner;
        this.members = members;
        this.locked = locked;
        this.islandworth = islandworth;
        this.islandUpgrades = islandUpgrades;
    }

    /*
    This section is for getting and setting values from the island object.
     */

    public JoLocation getIslandCenter() {
        return islandCenter;
    }

    public int getIslandSize() {
        return this.islandSize;
    }

    public int getIslandRadius() {
        return this.islandSize / 2;
    }

    public void setOwner(UUID idOfNewOwner) {
        this.owner = idOfNewOwner;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public Map<UUID, Integer> getMembers() {
        return this.members;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public JoLocation getHomeLocation() {
        return this.spawnLocation;
    }

    public void setHomeLocation(JoLocation newHomeLocation) {
        this.spawnLocation = newHomeLocation;
    }

    public void setIslandUpgrades(Map<String, Integer> newUpgrades) {
        this.islandUpgrades = newUpgrades;
    }

    /**
     * This gets the island upgrade corresponding to the param.
     * @param upgrade the upgrade name.
     * @return the level of upgrade.
     */
    public int getIslandUpgradeLevel(String upgrade) {
        return this.islandUpgrades.getOrDefault(upgrade, 0);
    }

    public Map<String, Integer> getIslandUpgrades() {
        return this.islandUpgrades;
    }

    /*
    This section is for checking values and island functions.
     */

    /**
     * This method checks to see if the location is on the island object.
     * @param location the location to be checked.
     * @return whether the location is on the island or not.
     */
    public boolean onIsland(Location location) {
        if (!Skyblock.getWorldManager().getIslandWorld().equals(location.getWorld())) return false;

        JoLocation min = new JoLocation(this.islandCenter.getWorld(), this.islandCenter.getBlockX() - this.getIslandRadius(), this.islandCenter.getBlockY(), this.islandCenter.getBlockZ() - this.getIslandRadius());
        JoLocation max = new JoLocation(this.islandCenter.getWorld(), this.islandCenter.getBlockX() + this.getIslandRadius(), this.islandCenter.getBlockY(), this.islandCenter.getBlockZ() + this.getIslandRadius());

        return (min.getBlockX() <= location.getX()
                && min.getBlockZ() <= location.getZ()
                && max.getBlockX() >= location.getX()
                && max.getBlockZ() >= location.getZ());
    }




}
