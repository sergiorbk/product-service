package com.sergosoft.productservice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class StringUtils {

    public static String clearExtraSpaces(String str) {
        log.debug("Clearing extra spaces from string: {}", str);
        String result = str.replaceAll("\\s+", " ");
        log.debug("String was cleared from spaces. Result string: {}", result);
        return result;
    }

}