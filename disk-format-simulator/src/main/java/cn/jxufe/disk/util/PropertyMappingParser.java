package cn.jxufe.disk.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class PropertyMappingParser {

    private PropertyMappingParser() {
    }

    public static Map<String, String> parse(String source) {
        return Optional.ofNullable(source)
                .stream()
                .flatMap(value -> Arrays.stream(value.split(",")))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(item -> item.split("=", 2))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(
                        pair -> pair[0].trim().toUpperCase(),
                        pair -> pair[1].trim(),
                        (left, right) -> right,
                        LinkedHashMap::new
                ));
    }
}