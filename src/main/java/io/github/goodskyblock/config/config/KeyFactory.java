package io.github.goodskyblock.config.config;

import com.google.common.collect.ImmutableMap;
import io.github.goodskyblock.config.config.adapter.ConfigAdapter;

import java.util.Map;
import java.util.function.Function;

public class KeyFactory {

    private static final LocalKeyFactory<Boolean> BOOLEAN = ConfigAdapter::getBoolean;
    private static final LocalKeyFactory<Integer> INTEGER = ConfigAdapter::getInteger;
    private static final LocalKeyFactory<String> STRING = ConfigAdapter::getString;
    private static final LocalKeyFactory<String> LOWERCASE_STRING = (adapter, path, def) -> adapter.getString(path, def).toLowerCase();
    private static final LocalKeyFactory<Map<String, String>> STRING_MAP = (config, path, def) -> ImmutableMap.copyOf(config.getStringMap(path, ImmutableMap.of()));

    public static BaseConfigKey<Boolean> newBooleanKey(String path, boolean def) {
        return BOOLEAN.createKey(path, def);
    }

    public static BaseConfigKey<Integer> newIntegerKey(String path, int def) {
        return INTEGER.createKey(path, def);
    }

    public static BaseConfigKey<String> newStringKey(String path, String def) {
        return STRING.createKey(path, def);
    }

    public static BaseConfigKey<String> newLowercaseStringKey(String path, String def) {
        return LOWERCASE_STRING.createKey(path, def);
    }

    public static BaseConfigKey<Map<String, String>> newMapKey(String path) {
        return STRING_MAP.createKey(path, null);
    }

    public static <T> CustomKey<T> newDynamicKey(Function<ConfigAdapter, T> function) {
        return new CustomKey<>(function);
    }

    public static <T> EnduringKey<T> newEnduringKey(ConfigKey<T> delegate) {
        return new EnduringKey<>(delegate);
    }

    /**
     * Functional interface that extracts values from a {@link ConfigAdapter}.
     *
     * @param <T> the value type.
     */
    @FunctionalInterface
    public interface LocalKeyFactory<T> {

        T getValue(ConfigAdapter config, String path, T def);

        default BaseConfigKey<T> createKey(String path, T def) {
            return new FunctionalKey<>(this, path, def);
        }
    }

    public abstract static class BaseConfigKey<T> implements ConfigKey<T> {

        int ordinal = -1;

        BaseConfigKey() {

        }

        @Override
        public int ordinal() {
            return this.ordinal;
        }
    }

    private static class FunctionalKey<T> extends BaseConfigKey<T> implements ConfigKey<T> {

        private final LocalKeyFactory<T> factory;
        private final String path;
        private final T def;

        FunctionalKey(LocalKeyFactory<T> factory, String path, T def) {
            this.factory = factory;
            this.path = path;
            this.def = def;
        }

        @Override
        public T get(ConfigAdapter adapter) {
            return this.factory.getValue(adapter, this.path, this.def);
        }
    }

    public static class CustomKey<T> extends BaseConfigKey<T> {

        private final Function<ConfigAdapter, T> function;

        private CustomKey(Function<ConfigAdapter, T> function) {
            this.function = function;
        }

        @Override
        public T get(ConfigAdapter adapter) {
            return this.function.apply(adapter);
        }
    }

    public static class EnduringKey<T> extends BaseConfigKey<T> {

        private final ConfigKey<T> delegate;

        private EnduringKey(ConfigKey<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get(ConfigAdapter adapter) {
            return this.delegate.get(adapter);
        }
    }

}