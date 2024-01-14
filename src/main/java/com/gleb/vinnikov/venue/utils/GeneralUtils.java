package com.gleb.vinnikov.venue.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneralUtils {

    private GeneralUtils(){}

    public static <BeforeT, AfterT> List<AfterT> mapList(List<BeforeT> collection, Function<BeforeT, AfterT> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

}
