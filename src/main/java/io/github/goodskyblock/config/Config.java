package io.github.goodskyblock.config;

import com.google.common.collect.Maps;
import io.github.goodskyblock.annotations.NonNull;
import io.github.goodskyblock.annotations.Nullable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Map;
import java.util.function.Supplier;

public class Config {
    private final String configName;
    private final File file;
    private YamlConfiguration configuration;

    private static Map<String, Boolean> pathPreferences = Maps.newHashMap();
    private static Map<String, Object> configKeyValues = Maps.newHashMap();

    public Config(Plugin plugin) {
        this.configName = this.getClass().getSimpleName().replace("Config", "") + ".yml";
        this.file = plugin.getDataFolder().toPath().resolve(this.configName).toFile();
        if (!this.file.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource(this.configName, false);
        }
    }

    public void load() {
        this.reload();
        for (String path : pathPreferences.keySet()) {
            configKeyValues.put(path, this.configuration.get(path));
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * This will return a custom value
     *
     * @param supplier   this is where you will create your custom value
     * @param <T>        the type
     * @return the custom value
     */
    @Nullable
    protected static <T> T newDynamicKey(@NonNull Supplier<T> supplier) {
        return supplier.get();
    }

    /**
     * Just a simplified version of {@link #newKey(String, Object, boolean)}
     */
    @Nullable
    protected static <T> T newKey(@NonNull String path, @Nullable T defaultValue) {
        return newKey(path, defaultValue, false);
    }

    /**
     * Gets the value from a specified path and return a
     * default value if the specified path is not valid
     *
     * @param path         the path
     * @param defaultValue the default value
     * @param isEnduring   if the key will not be reloaded
     * @param <T>          the type
     * @return the value
     */
    @Nullable
    protected static <T> T newKey(@NonNull String path, @Nullable T defaultValue, boolean isEnduring) {
        pathPreferences.put(path, isEnduring);
        return isPathValid(path) ? getValue(path) : defaultValue;
    }

    /**
     * Get a config-value from the configKeyValues map
     * and cast it to the specified type
     *
     * @param path the path we are getting the value from
     * @param <T>  the type
     * @return the value
     */
    @NonNull
    @SuppressWarnings("unchecked")
    private static <T> T getValue(@NonNull String path) {
        return (T) configKeyValues.get(path);
    }

    /**
     * Checks if a path is in the configKeyValues map
     *
     * @param path the path
     * @return if the path is in the map
     */
    private static boolean isPathValid(String path) {
        return configKeyValues.containsKey(path);
    }
}
