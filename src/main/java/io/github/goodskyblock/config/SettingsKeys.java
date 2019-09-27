package io.github.goodskyblock.config;

import io.github.goodskyblock.config.config.ConfigKey;
import io.github.goodskyblock.config.config.KeyFactory;
import io.github.goodskyblock.config.config.KeyHolder;
import io.github.goodskyblock.storage.MySqlCredentials;
import io.github.goodskyblock.storage.StorageType;

public class SettingsKeys extends KeyHolder {

    private static final ConfigKey<StorageType> STORAGE_TYPE = KeyFactory.newDynamicKey(adapter -> StorageType.parseStorageType(adapter.getString("storage-type", "yaml")));

    public static final ConfigKey<MySqlCredentials> MYSQL_CREDENTIALS = KeyFactory.newEnduringKey(
            KeyFactory.newDynamicKey(adapter -> {
                String host = adapter.getString("database.host", "localhost");
                int port = adapter.getInteger("database.port", 3306);
                String database = adapter.getString("database.database", "good-skyblock");
                String username = adapter.getString("database.username", "root");
                String password = adapter.getString("database.password", "");
                int maxPoolSize = adapter.getInteger("database.pool-settings.max-pool-size", 10);
                int minIdleConnections = adapter.getInteger("database.pool-settings.min-idle", 10);
                int maxLifetime = adapter.getInteger("database.pool-settings.max-lifetime", 1800000);
                int connectionTimeout = adapter.getInteger("database.pool-settings.connection-timeout", 5000);
                return new MySqlCredentials(host, port, database, username, password, maxPoolSize, minIdleConnections, maxLifetime, connectionTimeout);
            }));

    public static final ConfigKey<Integer> GRID_SIZE = KeyFactory.newEnduringKey(KeyFactory.newIntegerKey("world-options.grid-size", 200));

    public static final ConfigKey<Integer> ISLAND_DIAMETER = KeyFactory.newEnduringKey(KeyFactory.newIntegerKey("world-options.default-island-diameter", 100));
}
