package io.github.hbcsmfr.encryptpn;

import io.github.hbcsmfr.encryptpn.encryption.algorithm.AEncryptionAlgorithm;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.EncryptionAlgorithmBuilder;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.enums.EAlgorithm;
import io.github.hbcsmfr.encryptpn.encryption.model.PhoneNumber;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author christopher
 */
public class EncryptPN {

    public static void main(String[] args) {
        try {
            List<String> phoneNumberList = EncryptPN.getInputPhoneNumberList();
            if( ! phoneNumberList.isEmpty() ) {
                phoneNumberList.stream().forEach(phoneNumberStr -> {
                    PhoneNumber phoneNumber = new PhoneNumber(phoneNumberStr);
                    AEncryptionAlgorithm encryptionPhoneNumber = EncryptionAlgorithmBuilder.create(
                        EncryptPN.getAlgorithmMode(), 
                        phoneNumber
                    );
                    encryptionPhoneNumber.run();
                    System.out.println(phoneNumber.toString());
                });
            } else {
                System.err.println("Aucun numéro de téléphone à chiffrer !!");
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private static List<String> getInputPhoneNumberList() {
        return Arrays.asList();
    }
    
    private static EAlgorithm getAlgorithmMode() {
        return EAlgorithm.BASIC;
    }
    
}
