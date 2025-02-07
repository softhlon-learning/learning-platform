// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.common.text;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Token generator utility.
 */
public class TokenGenerator {
    private static final int LENGTH = 24;
    private static final boolean USE_LETTERS = true;
    private static final boolean USE_NUMBERS = true;

    public static String token() {
        return RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
    }
}
