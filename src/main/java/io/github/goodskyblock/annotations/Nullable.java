package io.github.goodskyblock.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Target({METHOD, PARAMETER, FIELD})
public @interface Nullable {}
