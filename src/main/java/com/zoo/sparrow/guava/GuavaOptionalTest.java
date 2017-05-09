package com.zoo.sparrow.guava;

        import com.google.common.base.Optional;

/**
 * Created by David.Liu on 17/3/26.
 */
public class GuavaOptionalTest {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("hello");
        if (optional.isPresent()) {
            System.out.println(optional.asSet());
        }
    }
}