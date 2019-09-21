package io.github.goodskyblock.config.settings;

import io.github.goodskyblock.config.Config;
import io.github.goodskyblock.storage.MySqlCredentials;
import io.github.goodskyblock.storage.StorageType;

public class SettingsConfig extends Config {

    private static final StorageType STORAGE_TYPE = newDynamicKey(() -> StorageType.parseStorageType(newKey("storage-type", "yaml")));

    public static final MySqlCredentials MYSQL_CREDENTIALS = newDynamicKey(() -> {
        String host = newKey("database.host", "localhost");
        int port = newKey("database.port", 3306);
        String database = newKey("database.database", "good-skyblock");
        String username = newKey("database.username", "root");
        String password = newKey("database.password", "");
        int maxPoolSize = newKey("database.pool-settings.max-pool-size", 10);
        int minIdleConnections = newKey("database.pool-settings.min-idle", 10);
        int maxLifetime = newKey("database.pool-settings.max-lifetime", 1800000);
        int connectionTimeout = newKey("database.pool-settings.connection-timeout", 5000);
       return new MySqlCredentials(host, port, database, username, password, maxPoolSize, minIdleConnections, maxLifetime, connectionTimeout);
    });
}
