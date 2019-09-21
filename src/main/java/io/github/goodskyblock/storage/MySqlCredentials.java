package io.github.goodskyblock.storage;

public class MySqlCredentials {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final int maxPoolSize;
    private final int minIdleConnections;
    private final int maxLifetime;
    private final int connectionTimeout;

    public MySqlCredentials(String host, int port, String database, String username, String password, int maxPoolSize, int minIdleConnections, int maxLifetime, int connectionTimeout) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.minIdleConnections = minIdleConnections;
        this.maxLifetime = maxLifetime;
        this.connectionTimeout = connectionTimeout;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public int getMinIdleConnections() {
        return this.minIdleConnections;
    }

    public int getMaxLifetime() {
        return this.maxLifetime;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }
}
