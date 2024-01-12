package com.gleb.vinnikov.social_network.utils;

import org.springframework.data.util.Streamable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneralUtils {

    private GeneralUtils(){}

    public static <BeforeT, AfterT> List<AfterT> mapList(List<BeforeT> collection, Function<BeforeT, AfterT> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

}
