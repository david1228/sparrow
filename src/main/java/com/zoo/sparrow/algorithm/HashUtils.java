package com.zoo.sparrow.algorithm;

/**
 * Created by David.Liu on 17/5/22.
 */
public class HashUtils {

    public static long murMurHash(String str) {
        return Long.valueOf(new MururHash().hash32(str));
    }
}
