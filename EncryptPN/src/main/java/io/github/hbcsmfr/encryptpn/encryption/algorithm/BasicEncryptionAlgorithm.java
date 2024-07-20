package io.github.hbcsmfr.encryptpn.encryption.algorithm;

import io.github.hbcsmfr.encryptpn.ConstantsEPN;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.ascii.AsciiQuota;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.ascii.TableAscii;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.enums.EAlgorithm;
import io.github.hbcsmfr.encryptpn.encryption.algorithm.utils.RandomNumber;
import io.github.hbcsmfr.encryptpn.encryption.model.PhoneNumber;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author christopher
 */
public class BasicEncryptionAlgorithm extends AEncryptionAlgorithm {
    
    public static final String FIRST_KEY_SEPARATOR = "-";
    
    public static final String OTHER_KEY_SEPARATOR = ":";
    
    private final AsciiQuota quota;
    
    private final List<String> asciiIntervalList;
    
    private final TableAscii table;
    
    private final Set<String> asciiAlreadyUsed;

    private final StringBuilder outputPN;
    
    private final StringBuilder key;
    
    public BasicEncryptionAlgorithm(PhoneNumber aPhoneNumber) {
        super(aPhoneNumber);
        this.quota = new AsciiQuota(3, 0, 1, 1);
        this.asciiIntervalList = Arrays.asList(
            TableAscii.ASCII_SYMBOL, 
            TableAscii.ASCII_CHAR_UPPERCASE, 
            TableAscii.ASCII_CHAR_LOWERCASE
        );
        this.table = new TableAscii();
        this.asciiAlreadyUsed = new TreeSet();
        this.asciiAlreadyUsed.add(String.valueOf((char)34)); // Interdire les doubles quote
        this.asciiAlreadyUsed.add(String.valueOf((char)92)); // Interdire l'anti slash
        this.outputPN = new StringBuilder();
        this.key = new StringBuilder();
    }

    @Override
    public void run() {
        this.encrypt();
        this.after(outputPN.toString(), key.toString());
    }
    
    private void encrypt() {
        this.readNumberPhone();
        String phoneNumber = this.phoneNumberData.get(ConstantsEPN.PHONE_NUMBER_KEY);
        String[] inputPN = phoneNumber.split(
            String.format("\\%s", ConstantsEPN.PHONE_NUMBER_SEPARATOR)
        );
        
        // Ajouter l'identifiant de l'algorithme dans la clé.
        this.key.append(EAlgorithm.BASIC.getIdentifier());
        this.key.append(OTHER_KEY_SEPARATOR);
        this.key.append(this.phoneNumberData.get(ConstantsEPN.PHONE_PREFIX_KEY));
        this.key.append(FIRST_KEY_SEPARATOR);
        
        if(inputPN.length == 0) {
            throw new UnsupportedOperationException("Impossible de découper le numéro de téléphone !!");
        } else {
            Arrays.asList(inputPN).stream().forEach(partPN -> this.encrypt(partPN));
        }
    }
    
    private void encrypt(String aPartPN) {
        String encryptedCharTry = null;
        String currentInterval = null;
        boolean doRetry;
        do {
            currentInterval = this.getRandomInterval(aPartPN);
            doRetry = ( currentInterval == null );
            if( ! doRetry ) {
                encryptedCharTry = this.getRandomAsciiForInterval(currentInterval, aPartPN);
                doRetry = ( encryptedCharTry == null );
            }
        } while(doRetry || this.isAlreadyUsed(encryptedCharTry));
        //
        // Si le caractère chiffré peut être utilisé...
        this.asciiAlreadyUsed.add(encryptedCharTry);
        this.updateQuota(currentInterval);
        this.updateKeyAndOutputPhoneNumber(aPartPN, encryptedCharTry);
    }
    
    private boolean isAlreadyUsed(String anEncryptedCharTry) {
        return this.asciiAlreadyUsed.contains(anEncryptedCharTry);
    }
    
    private String getRandomAsciiForInterval(String anInterval, String aPartPN) {
        String randomAscii;
        Optional<List<String>> intervalOpt = this.table.getInterval(anInterval);
        if(intervalOpt.isPresent()) {
            List<String> intervalList = intervalOpt.get();
            String intervalSelected;
            if(intervalList.size() > 1) {
                int nbPartPN = Integer.parseInt(aPartPN);
                List<String> resultIntervalList = intervalList.stream().filter(interval -> {
                    String[] intervalArray = interval.split(TableAscii.ASCII_INTERVAL_SEPARATOR);
                    int leftNumber = Integer.parseInt(intervalArray[0]);
                    return leftNumber > nbPartPN;
                })
                .toList();
                if( ! resultIntervalList.isEmpty() ) {
                    int intervalIndex = (new RandomNumber(0, resultIntervalList.size() - 1)).run();
                    intervalSelected = resultIntervalList.get(intervalIndex);
                } else {
                    intervalSelected = null;
                }
            } else {
                intervalSelected = intervalList.get(0);
            }
            if(intervalSelected != null) {
                String[] intervalSelectedArray = intervalSelected.split(TableAscii.ASCII_INTERVAL_SEPARATOR);
                int minInterval = Integer.parseInt(intervalSelectedArray[0]);
                int maxInterval = Integer.parseInt(intervalSelectedArray[1]);
                int randomNumber = (new RandomNumber(minInterval, maxInterval)).run();
                randomAscii = String.valueOf((char)randomNumber);
            } else {
                randomAscii = null;
            }
        } else {
            throw new UnsupportedOperationException("Intervalle ASCII requis !!");
        }
        return randomAscii;
    }
    
    private String getRandomInterval(String aPartPN) {
        int intervalIndex = 0;
        List<String> tmpIntervalList = null;
        if(this.asciiIntervalList.size() > 1) {
            tmpIntervalList = this.asciiIntervalList.stream()
                .filter(keyInterval -> this.isValidInterval(keyInterval, aPartPN))
                .toList();
            if( tmpIntervalList.size() > 1 ) {
                RandomNumber randomInterval = new RandomNumber(0, tmpIntervalList.size() - 1);
                intervalIndex = randomInterval.run();
            } else if( ! tmpIntervalList.isEmpty() ) {
                intervalIndex = 0;
            } else {
                intervalIndex = -1;
            }
        }
        return (
            ( tmpIntervalList == null || intervalIndex == -1 ) ? 
            null : 
            tmpIntervalList.get(intervalIndex)
        );
    }
    
    private boolean isValidInterval(String aKeyInterval, String aPartPN) {
        int nbRetry;
        switch(aKeyInterval) {
            case TableAscii.ASCII_SYMBOL:
                nbRetry = this.quota.getNbSymbol();
                break;
            case TableAscii.ASCII_CHAR_UPPERCASE:
                nbRetry = this.quota.getNbCharUp();
                break;
            case TableAscii.ASCII_CHAR_LOWERCASE:
                nbRetry = this.quota.getNbCharLow();
                break;
            default:
                throw new UnsupportedOperationException("Clé d'intervalle non gérée dans la contrôle de validité !!");
        }
        return (
            this.table.isSmallerThanMax(aKeyInterval, aPartPN) && nbRetry > 0
        );
    }
    
    private void updateQuota(String aCurrentInterval) {
        switch(aCurrentInterval) {
            case TableAscii.ASCII_SYMBOL:
                int nbSymbolUpdated = this.quota.getNbSymbol() - 1;
                this.quota.setNbSymbol(nbSymbolUpdated);
                break;
            case TableAscii.ASCII_CHAR_UPPERCASE:
                int nbCharUpUpdated = this.quota.getNbCharUp() - 1;
                this.quota.setNbCharUp(nbCharUpUpdated);
                break;
            case TableAscii.ASCII_CHAR_LOWERCASE:
                int nbCharLowUpdated = this.quota.getNbCharLow()- 1;
                this.quota.setNbCharLow(nbCharLowUpdated);
                break;
            default:
                throw new UnsupportedOperationException("Intervalle non géré pour la mise à jour du quota !!");
        }
    }
    
    private void updateKeyAndOutputPhoneNumber(String aPartPN, String anEncryptedChar) {
        int nbPartPN = Integer.parseInt(aPartPN);
        int nbEncryptedChar = (int)anEncryptedChar.charAt(0);
        int delta = nbEncryptedChar - nbPartPN;
        if(!this.key.toString().endsWith(FIRST_KEY_SEPARATOR)) {
            this.key.append(OTHER_KEY_SEPARATOR);
        }
        this.key.append(String.valueOf(delta));
        this.outputPN.append(anEncryptedChar);
    }
    
}
