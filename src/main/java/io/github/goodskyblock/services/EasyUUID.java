package io.github.goodskyblock.services;

import com.eatthepath.uuid.FastUUID;
import io.github.goodskyblock.annotations.NonNull;
import io.github.goodskyblock.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public class EasyUUID {

    /**
     * Wraps {@link FastUUID#parseUUID(CharSequence)}
     * to keep utilities for {@link UUID} in one place
     *
     * @param toParse the string we will parse
     * @return the parsed string
     */
    @NonNull
    public static UUID parse(@NonNull String toParse) {
        return FastUUID.parseUUID(toParse);
    }

    /**
     * Wraps {@link FastUUID#toString(UUID)} to
     * keep utilities for {@link UUID} in one place
     *
     * @param uuid the uuid we are converting to a string
     * @return the converted uuid
     */
    @NonNull
    public static String toString(@NonNull UUID uuid) {
        return FastUUID.toString(uuid);
    }

    /**
     * Generates an {@link UUID} and checks for
     * duplicates even though the change for a
     * duplicate is
     *
     * @param checkForDuplicates the collection we will check for duplicates
     * @return the generated uuid
     */
    @NonNull
    public static UUID generateUuid(@NonNull Collection<UUID> checkForDuplicates) {
        UUID uuid = UUID.randomUUID();
        while (!checkForDuplicates.contains(uuid)) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }
}
