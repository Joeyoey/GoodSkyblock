package io.github.goodskyblock.config.config;

import io.github.goodskyblock.config.config.adapter.ConfigAdapter;

/**
 * Represents a key in the configuration.
 *
 * @param <T> the value type
 */
public interface ConfigKey<T> {

    /**
     * Gets the position of this key within the assigned Keys "generic enum".
     *
     * <p>This is set shortly after the key is created, during the initialisation
     * of the assigned Keys "generic enum".</p>
     *
     * @return the position
     */
    int ordinal();

    /**
     * Resolves and returns the value mapped to this key using the given config instance.
     *
     * <p>The {@link Config#get(ConfigKey)} method should be used to
     * retrieve the value, as opposed to calling this directly.</p>
     *
     * @param adapter the config adapter instance
     * @return the value mapped to this key
     */
    T get(ConfigAdapter adapter);
}
