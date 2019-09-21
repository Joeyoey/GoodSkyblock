package io.github.goodskyblock.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import io.github.goodskyblock.annotations.NonNull;
import io.github.goodskyblock.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CacheWrapper<K, V> {
    private final Cache<K, V> cache;

    public CacheWrapper() {
        this.cache = CacheBuilder.newBuilder().build();
    }

    public CacheWrapper(long duration, TimeUnit timeUnit) {
        this.cache = CacheBuilder.newBuilder().expireAfterAccess(duration, timeUnit).build();
    }

    @NonNull
    public Cache<K, V> getCache() {
        return this.cache;
    }

    @NonNull
    public CacheStats getStatistics() {
        return this.cache.stats();
    }

    @Nullable
    public Optional<V> get(K key) {
        return Optional.ofNullable(this.cache.getIfPresent(key));
    }

    @Nullable
    public V get(@NonNull K key, @NonNull Function<K, V> entryIfAbsent) {
        V value = this.cache.getIfPresent(key);
        if (value == null) {
            this.cache.put(key, entryIfAbsent.apply(key));
        }
        return value;
    }

    public void set(@NonNull K key, @NonNull V value) {
        this.cache.put(key, value);
    }

    public void populateCache(@NonNull Map<K, V> map) {
        this.cache.putAll(map);
    }
}
