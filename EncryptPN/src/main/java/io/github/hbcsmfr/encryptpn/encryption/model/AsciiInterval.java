package io.github.hbcsmfr.encryptpn.encryption.model;

import java.util.List;

/**
 *
 * @author christopher
 */
public class AsciiInterval {
    
    private final List<String> intervalList;
    
    private final AsciiMinMax intervalMinMax;
    
    public AsciiInterval(List<String> anIntervalList) {
        this.intervalList = anIntervalList;
        this.intervalMinMax = new AsciiMinMax(this.intervalList);
    }

    public List<String> getIntervalList() {
        return intervalList;
    }

    public AsciiMinMax getIntervalMinMax() {
        return intervalMinMax;
    }
    
}
