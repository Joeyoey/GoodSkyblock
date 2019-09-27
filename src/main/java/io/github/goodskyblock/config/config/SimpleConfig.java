package io.github.goodskyblock.config.config;

import io.github.goodskyblock.config.config.adapter.ConfigAdapter;
import io.github.goodskyblock.config.config.adapter.YamlConfigAdapter;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

/**
 * An abstract implementation of {@link Config}.
 *
 * <p>Values are loaded into memory on init.</p>
 */
public class SimpleConfig implements Config {

    /**
     * The configurations loaded values.
     *
     * <p>The value corresponding to each key is stored at the index defined
     * by {@link ConfigKey#ordinal()}.</p>
     */
    private Object[] values;

    private final Plugin plugin;
    private final KeyHolder configKeys;
    private final ConfigAdapter configAdapter;
    private final File file;

    public SimpleConfig(Plugin plugin, KeyHolder configKeys, Function<Path, Path> path) {
        this.plugin = plugin;
        this.configKeys = configKeys;
        this.file = path.apply(plugin.getDataFolder().toPath()).toFile();
        this.configAdapter = new YamlConfigAdapter(plugin, this.file);
        this.createFileIfAbsent();
        this.load();
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(ConfigKey<T> key) {
        return (T) this.values[key.ordinal()];
    }

    @Override
    public synchronized void load() {
        boolean reload = true;

        if (this.values == null) {
            this.values = new Object[configKeys.size()];
            reload = false;
        }
        for (ConfigKey<?> configKey : this.configKeys.getKeys().values()) {
            if (reload && configKey instanceof KeyFactory.EnduringKey)
                continue;

            this.values[configKey.ordinal()] = configKey.get(this.configAdapter);
        }
    }

    @Override
    public void reload() {
        this.configAdapter.reload();
        this.load();
    }

    private void createFileIfAbsent() {
        if (!this.file.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource(file.getName(), false);
        }
    }
}
