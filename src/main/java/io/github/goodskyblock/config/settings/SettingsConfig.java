package io.github.goodskyblock.config.settings;

import io.github.goodskyblock.config.Config;
import io.github.goodskyblock.storage.MySqlCredentials;
import io.github.goodskyblock.storage.StorageType;
import org.bukkit.plugin.Plugin;

public class SettingsConfig extends Config {

    public SettingsConfig(Plugin plugin) {
        super(plugin);
    }

    private static final StorageType STORAGE_TYPE = newDynamicKey(() -> StorageType.parseStorageType(newKey("storage-type", "yaml", true)));

    public static final MySqlCredentials MYSQL_CREDENTIALS = newDynamicKey(() -> {
        String host = newKey("database.host", "localhost", true);
        int port = newKey("database.port", 3306, true);
        String database = newKey("database.database", "good-skyblock", true);
        String username = newKey("database.username", "root", true);
        String password = newKey("database.password", "", true);
        int maxPoolSize = newKey("database.pool-settings.max-pool-size", 10, true);
        int minIdleConnections = newKey("database.pool-settings.min-idle", 10, true);
        int maxLifetime = newKey("database.pool-settings.max-lifetime", 1800000, true);
        int connectionTimeout = newKey("database.pool-settings.connection-timeout", 5000, true);
       return new MySqlCredentials(host, port, database, username, password, maxPoolSize, minIdleConnections, maxLifetime, connectionTimeout);
    });

    public static final int GRID_SIZE = newKey("world-options.grid-size", 200, true);

    public static final int ISLAND_DIAMETER = newKey("world-options.default-island-diameter", 100);
}
