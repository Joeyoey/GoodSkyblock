package io.github.goodskyblock.hooks;

import org.bukkit.Bukkit;

public abstract class Hook {
    private final String pluginName;

    public Hook(String pluginName) {
        this.pluginName = pluginName;
    }

    public boolean canHook() {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    abstract void hook();
}
