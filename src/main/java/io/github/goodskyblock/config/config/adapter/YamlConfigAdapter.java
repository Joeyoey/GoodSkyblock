package io.github.goodskyblock.config.config.adapter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Map;

public class YamlConfigAdapter implements ConfigAdapter {

    private final Plugin plugin;
    private final File file;

    private YamlConfiguration configuration;

    public YamlConfigAdapter(Plugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        this.reload();
    }

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public String getString(String path, String def) {
        return this.configuration.getString(path, def);
    }

    @Override
    public int getInteger(String path, int def) {
        return this.configuration.getInt(path, def);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return this.configuration.getBoolean(path, def);
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        return this.configuration.getStringList(path);
    }

    @Override
    public List<String> getKeys(String path, List<String> def) {
        ConfigurationSection section = this.configuration.getConfigurationSection(path);

        if (section == null) {
            return def;
        }

        return Lists.newArrayList(section.getKeys(false));
    }

    @Override
    public Map<String, String> getStringMap(String path, Map<String, String> def) {
        Map<String, String> map = Maps.newHashMap();
        ConfigurationSection section = this.configuration.getConfigurationSection(path);

        if (section == null) {
            return def;
        }

        for (String key : section.getKeys(false)) {
            map.put(key, section.getString(key));
        }
        return map;
    }
}
