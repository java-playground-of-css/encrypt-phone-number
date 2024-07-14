package io.github.hbcsmfr.encryptpn.encryption.algorithm;

import io.github.hbcsmfr.encryptpn.encryption.model.PhoneNumber;

/**
 *
 * @author christopher
 */
public abstract class AEncryptionAlgorithm {
    
    protected PhoneNumber phoneNumber;
    
    public AEncryptionAlgorithm(PhoneNumber aPhoneNumber) {
        this.phoneNumber = aPhoneNumber;
    }
    
    public abstract void run();
    
    protected void after(String anOutputPN, String aKey) {
        this.phoneNumber.encrypt(anOutputPN, aKey);
    }
    
}
