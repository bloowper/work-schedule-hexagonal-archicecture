package com.orchowski.smartcharginghexagon.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NullCheck {

    public static <T> void throwExceptionWhenAnyNull(Supplier<RuntimeException> exceptionSupplier, T... t) {
        if (!ObjectUtils.allNotNull(t)) {
            throw exceptionSupplier.get();
        }
    }
}
