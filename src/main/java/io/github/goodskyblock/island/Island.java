package io.github.goodskyblock.island;

import com.google.common.collect.Maps;
import io.github.goodskyblock.config.settings.SettingsConfig;
import io.github.goodskyblock.services.JoLocation;

import java.util.Map;
import java.util.UUID;

public class Island {
    private final JoLocation islandCenter;
    private JoLocation spawnLocation;
    private int islandSize;
    private UUID owner;
    private Map<UUID, Integer> members = Maps.newHashMap();
    private boolean locked;

    public Island(JoLocation joLocation, UUID owner) {
        this.islandCenter = joLocation;
        this.owner = owner;
        this.locked = false;
        this.islandSize = SettingsConfig.ISLAND_DIAMETER;
    }

    public Island(JoLocation islandCenter, JoLocation spawnLocation, int islandSize, UUID owner, Map<UUID, Integer> members, boolean locked) {
        this.islandCenter = islandCenter;
        this.spawnLocation = spawnLocation;
        this.islandSize = islandSize;
        this.owner = owner;
        this.members = members;
        this.locked = locked;
    }

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
}
