// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.common.text;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Id printer utility.
 */
public class IdPrinter {

    /**
     * Print UUID as short (8 chars) string.
     * @param id UUID
     * @return Short UUID
     */
    public static String printShort(
          UUID id) {

        if (id == null) return null;

        return '#' + id
              .toString()
              .substring(0, 8);

    }

}
