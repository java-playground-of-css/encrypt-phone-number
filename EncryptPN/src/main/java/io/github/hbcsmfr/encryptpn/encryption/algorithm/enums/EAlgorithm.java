package io.github.hbcsmfr.encryptpn.encryption.algorithm.enums;

/**
 *
 * @author christopher
 */
public enum EAlgorithm {
    BASIC("b0");
    
    private String identifier;
    
    private EAlgorithm(String anIdentifier) {
        this.identifier = anIdentifier;
    }

    public String getIdentifier() {
        return identifier;
    }
    
}
