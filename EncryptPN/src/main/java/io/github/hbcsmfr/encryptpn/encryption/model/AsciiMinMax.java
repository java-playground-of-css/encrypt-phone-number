package io.github.hbcsmfr.encryptpn.encryption.model;

import io.github.hbcsmfr.encryptpn.encryption.algorithm.ascii.TableAscii;
import java.util.List;

/**
 *
 * @author christopher
 */
public class AsciiMinMax {
    
    private int min;
    
    private int max;
    
    public AsciiMinMax(List<String> intervalList) {
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
        intervalList.stream().forEach(interval -> {
            String[] numberArray = interval.split(TableAscii.ASCII_INTERVAL_SEPARATOR);
            int tmpMin = Integer.parseInt(numberArray[0]);
            int tmpMax = Integer.parseInt(numberArray[1]);
            min = Math.min(min, tmpMin);
            max = Math.max(max, tmpMax);
        });
    }
    
    public boolean isBetweenMinMax(String anInput) {
        int nb = Integer.parseInt(anInput);
        return (this.min <= nb && nb <= this.max);
    }
    
    public boolean isSmallerThanMax(String anInput) {
        int nb = Integer.parseInt(anInput);
        return (this.max > nb);
    }
    
}
