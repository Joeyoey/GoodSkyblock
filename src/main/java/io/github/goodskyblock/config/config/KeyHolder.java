package io.github.goodskyblock.config.config;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;


public class KeyHolder {

    private final Map<String, ConfigKey<?>> keys;
    private final int size;

    public KeyHolder() {
        Map<String, ConfigKey<?>> keys = new LinkedHashMap<>();
        Field[] values = getClass().getFields();
        int i = 0;

        for (Field field : values) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;

            if (!ConfigKey.class.equals(field.getType()))
                continue;

            try {
                KeyFactory.BaseConfigKey<?> key = (KeyFactory.BaseConfigKey<?>) field.get(null);
                key.ordinal = i++;
                keys.put(field.getName(), key);
            } catch (Exception e) {
                throw new RuntimeException("Exception processing field: " + field, e);
            }
        }
        this.keys = ImmutableMap.copyOf(keys);
        this.size = i;
    }

    /**
     * Gets a map of the keys defined in this class.
     *
     * <p>The string key in the map is the {@link Field#getName() field name}
     * corresponding to each key.</p>
     *
     * @return the defined keys
     */
    public Map<String, ConfigKey<?>> getKeys() {
        return this.keys;
    }

    /**
     * Gets the number of defined keys.
     *
     * @return how many keys are defined in this class
     */
    public int size() {
        return this.size;
    }
}
