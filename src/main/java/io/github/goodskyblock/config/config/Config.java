package io.github.goodskyblock.config.config;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

public interface Config {

    /**
     * Gets the plugin
     */
    Plugin getPlugin();

    /**
     * Reloads the configuration.
     */
    void reload();

    /**
     * Loads all configuration values.
     */
    void load() throws InvocationTargetException, IllegalAccessException;

    /**
     * Gets the value of a given context key.
     *
     * @param key the key
     * @param <T> the key return type
     * @return the value mapped to the given key. May be null.
     */
    <T> T get(ConfigKey<T> key);
}
