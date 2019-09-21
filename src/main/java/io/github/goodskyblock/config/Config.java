package io.github.goodskyblock.config;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Supplier;

public class Config {
    private static Map<String, ConfigValue> configValues = Maps.newHashMap();

    public static void load() {

    }

    public static void reload() {

    }

    protected static <T> T newDynamicKey(Supplier<T> supplier) {
        return newDynamicKey(supplier, false);
    }

    protected static <T> T newDynamicKey(Supplier<T> supplier, boolean isEnduring) {
        return supplier.get();
    }

    protected static <T> T newKey(String path, T defaultValue) {
        return newKey(path, defaultValue, false);
    }

    protected static <T> T newKey(String path, T defaultValue, boolean isEnduring) {
        return isPathValid(path) ? getValue(path) : defaultValue;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getValue(String path) {
        return (T) configValues.get(path);
    }

    private static boolean isPathValid(String path) {
        return configValues.containsKey(path);
    }

    private static class ConfigValue {
        private final Object value;
        private final boolean isEnduring;

        public ConfigValue(Object value, boolean isEnduring) {
            this.value = value;
            this.isEnduring = isEnduring;
        }
    }
}
