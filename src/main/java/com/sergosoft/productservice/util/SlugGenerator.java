package com.sergosoft.productservice.util;

import com.ibm.icu.text.Transliterator;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class SlugGenerator {

    private static final String LATIN_TO_ASCII = "Any-Latin; Latin-ASCII";

    public static String generateSlug(String input) {
        log.debug("Generating slug from: {}", input);
        // Extra spaces clean
        String result = StringUtils.clearExtraSpaces(input);
        // Transliteration
        Transliterator transliterator = Transliterator.getInstance(LATIN_TO_ASCII);
        result = transliterator.transliterate(result);
        // Lower case
        result = result.toLowerCase();
        // Special symbols and spaces replacement on - symbol
        result = result.replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
        log.debug("Slug has been generated: {}", result);
        return result;
    }

}