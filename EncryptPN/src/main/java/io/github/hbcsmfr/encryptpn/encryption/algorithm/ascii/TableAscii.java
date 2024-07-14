package io.github.hbcsmfr.encryptpn.encryption.algorithm.ascii;

import io.github.hbcsmfr.encryptpn.encryption.model.AsciiInterval;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 *
 * @author christopher
 */
public class TableAscii {
    
    public static final String ASCII_SYMBOL = "SYMBOL";
    
    public static final String ASCII_NUMBER = "NUMBER";
    
    public static final String ASCII_CHAR_LOWERCASE = "CHAR_LOW";
    
    public static final String ASCII_CHAR_UPPERCASE = "CHAR_UP";
    
    public static final String ASCII_INTERVAL_SEPARATOR = "-";
    
    private Optional<Map<String, AsciiInterval>> tableOpt;
    
    public TableAscii() {
        this.tableOpt = Optional.empty();
    }
    
    private void initialize() {
        if(this.tableOpt.isEmpty()) {
            String intervalA = this.createInterval(33, 47);
            String intervalB = this.createInterval(48, 57);
            String intervalC = this.createInterval(58, 64);
            String intervalD = this.createInterval(65, 90);
            String intervalE = this.createInterval(91, 96);
            String intervalF = this.createInterval(97, 122);
            String intervalG = this.createInterval(123, 126);
            List<String> symbol = Arrays.asList(intervalA, intervalC, intervalE, intervalG);
            List<String> charUp = Arrays.asList(intervalD);
            List<String> charLow = Arrays.asList(intervalF);
            List<String> number = Arrays.asList(intervalB);
            Map<String, AsciiInterval> table = new TreeMap();
            table.put(ASCII_SYMBOL, new AsciiInterval(symbol));
            table.put(ASCII_CHAR_LOWERCASE, new AsciiInterval(charLow));
            table.put(ASCII_CHAR_UPPERCASE, new AsciiInterval(charUp));
            table.put(ASCII_NUMBER, new AsciiInterval(number));
            this.tableOpt = Optional.of(table);
        }
    }
    
    public Optional<List<String>> getInterval(String aKey) {
        this.initialize();
        Map<String, AsciiInterval> table = this.tableOpt.get();
        Optional<List<String>> interval = Optional.empty();
        if(table.containsKey(aKey)) {
            interval = Optional.of(table.get(aKey)
                .getIntervalList());
        }
        return interval;
    }
    
    public boolean isBetweenMinMax(String aKey, String anInput) {
        boolean isBetweenMinMax = false;
        this.initialize();
        Map<String, AsciiInterval> table = this.tableOpt.get();
        if(table.containsKey(aKey)) {
            isBetweenMinMax = table.get(aKey)
                .getIntervalMinMax()
                .isBetweenMinMax(anInput);
        }
        return isBetweenMinMax;
    }
    
    public boolean isSmallerThanMax(String aKey, String anInput) {
        boolean isGreaterThanOrEqualTo = false;
        this.initialize();
        Map<String, AsciiInterval> table = this.tableOpt.get();
        if(table.containsKey(aKey)) {
            isGreaterThanOrEqualTo = table.get(aKey)
                .getIntervalMinMax()
                .isSmallerThanMax(anInput);
        }
        return isGreaterThanOrEqualTo;
    }
    
    private String createInterval(int min, int max) {
        return String.format("%d%s%d", min, ASCII_INTERVAL_SEPARATOR, max);
    }
    
}
