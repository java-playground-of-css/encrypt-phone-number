package io.github.hbcsmfr.encryptpn.encryption.algorithm;

import io.github.hbcsmfr.encryptpn.ConstantsEPN;
import io.github.hbcsmfr.encryptpn.encryption.model.PhoneNumber;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author christopher
 */
public abstract class AEncryptionAlgorithm {
    
    protected PhoneNumber phoneNumber;
    
    protected final Map<String, String> phoneNumberData;
    
    public AEncryptionAlgorithm(PhoneNumber aPhoneNumber) {
        this.phoneNumber = aPhoneNumber;
        this.phoneNumberData = new TreeMap();
    }
    
    public abstract void run();
    
    protected void after(String anOutputPN, String aKey) {
        this.phoneNumber.encrypt(anOutputPN, aKey);
    }
    
    protected void readNumberPhone() {
        String[] data = this.phoneNumber.getInputPN()
            .split(ConstantsEPN.PHONE_NUMBER_INPUT_SEPARATOR);
        
        this.phoneNumberData.put(ConstantsEPN.PHONE_PREFIX_KEY, data[0]);
        this.phoneNumberData.put(ConstantsEPN.PHONE_NUMBER_KEY, data[1]);
    }
    
}
