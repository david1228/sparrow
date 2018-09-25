package com.zoo.sparrow.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * Created by David.Liu on 17/5/11.
 */
public class StringTest {

    public static void main(String[] args) {
        Splitter sp = Splitter.on('|').trimResults(CharMatcher.is(','));
        Iterable<String> it = sp.split("2222222,");
        System.out.println(it.toString());
    }
}
