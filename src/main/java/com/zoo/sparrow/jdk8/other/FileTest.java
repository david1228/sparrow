package com.zoo.sparrow.jdk8.other;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by David.Liu on 17/8/11.
 */
public class FileTest {

    public static void main(String[] args) throws Exception {
        Files.lines(Paths.get("/letv/data/input.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
    }
}
