package com.challenge.common.util;

import java.util.Random;

public class RandomNumberUtils {
    private static final Random random = new Random();

    public static int randomInt(int bound){
        return random.nextInt(bound);
    }
}
