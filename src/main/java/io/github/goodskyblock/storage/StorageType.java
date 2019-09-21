package io.github.goodskyblock.storage;

import com.google.common.collect.Sets;

import java.util.Set;

public enum StorageType {
    YAML("yaml", "yml"),
    MYSQL("mysql", "sql");

    private final Set<String> identifiers;

    StorageType(String... identifiers) {
        this.identifiers = Sets.newHashSet(identifiers);
    }

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public static StorageType parseStorageType(String toParse) {
        for (StorageType storageType : values()) {
            if (storageType.getIdentifiers().contains(toParse.toLowerCase())) {
                return storageType;
            }
        }
        return YAML;
    }
}
