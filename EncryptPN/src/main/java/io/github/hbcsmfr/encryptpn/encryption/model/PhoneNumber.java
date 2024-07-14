package io.github.hbcsmfr.encryptpn.encryption.model;

import java.util.Optional;

/**
 *
 * @author christopher
 */
public class PhoneNumber {
    
    /**
     * Numéro de téléphone (non chiffré).
     */
    private final String inputPN;
    
    /**
     * Numéro de téléphone chiffré.
     */
    private Optional<String> outputPnOpt;
    
    /**
     * Clé de chiffrement.
     */
    private Optional<String> keyOpt;
    
    public PhoneNumber(String anInputPN) {
        this.inputPN = anInputPN;
        this.outputPnOpt = Optional.empty();
        this.keyOpt = Optional.empty();
    }
    
    public void encrypt(String anOutputPN, String aKey) {
        this.outputPnOpt = Optional.of(anOutputPN);
        this.keyOpt = Optional.of(aKey);
    }

    public String getInputPN() {
        return inputPN;
    }

    public String getOutputPN() {
        if(this.outputPnOpt.isEmpty()) {
            return "";
        }
        return outputPnOpt.get();
    }

    public String getKey() {
        if(this.keyOpt.isEmpty()) {
            return "";
        }
        return keyOpt.get();
    }
    
    public boolean isAlreadyEncrypted() {
        return (
            this.outputPnOpt.isPresent() && 
            this.keyOpt.isPresent()
        );
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("PhoneNumber{");
        output.append(System.lineSeparator());
        output.append("Numéro (à chiffrer) : ");
        output.append(this.inputPN);
        if( ! this.isAlreadyEncrypted() ) {
            output.append(" (non chiffré)");
            output.append(System.lineSeparator());
        } else {
            output.append(System.lineSeparator());
            output.append("Numéro chiffré : \"");
            output.append(this.getOutputPN());
            output.append("\"");
            output.append(System.lineSeparator());
            output.append("Clé : \"");
            output.append(this.getKey());
            output.append("\"");
            output.append(System.lineSeparator());
        }
        output.append("}");
        return output.toString();
    }
    
}
