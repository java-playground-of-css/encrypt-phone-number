package io.github.hbcsmfr.encryptpn.encryption.algorithm.utils;

import java.util.Random;

/**
 *
 * @author christopher
 */
public class RandomNumber {
    
    private final int min;
    
    private final int max;
    
    public RandomNumber(int aMin, int aMax) {
        this.min = aMin;
        this.max = aMax;
    }
    
    public int run() {
        Random random = new Random();
        return (random.nextInt(this.max - this.min) + this.min);
    }
    
}
