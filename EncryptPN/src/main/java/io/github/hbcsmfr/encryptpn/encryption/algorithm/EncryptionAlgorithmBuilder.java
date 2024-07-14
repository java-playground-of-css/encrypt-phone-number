package io.github.hbcsmfr.encryptpn.encryption.algorithm;

import io.github.hbcsmfr.encryptpn.encryption.algorithm.enums.EAlgorithm;
import io.github.hbcsmfr.encryptpn.encryption.model.PhoneNumber;

/**
 *
 * @author christopher
 */
public class EncryptionAlgorithmBuilder {
    
    private EncryptionAlgorithmBuilder() {}
    
    public static AEncryptionAlgorithm create(EAlgorithm algoMode, PhoneNumber aPhoneNumber) {
        AEncryptionAlgorithm algo = null;
        if(EAlgorithm.BASIC.equals(algoMode)) {
            algo = new BasicEncryptionAlgorithm(aPhoneNumber);
        }
        return algo;
    }
    
}
