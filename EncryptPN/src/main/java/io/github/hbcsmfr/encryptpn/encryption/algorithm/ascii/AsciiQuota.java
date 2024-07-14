package io.github.hbcsmfr.encryptpn.encryption.algorithm.ascii;

/**
 *
 * @author christopher
 */
public class AsciiQuota {
    
    private int nbSymbol;
    
    private int nbNumber;
    
    private int nbCharLow;
    
    private int nbCharUp;
    
    public AsciiQuota(
        int aNbSymbol,
        int aNbNumber,
        int aNbCharLow,
        int aNbCharUp
    ) {
        this.nbSymbol = aNbSymbol;
        this.nbNumber = aNbNumber;
        this.nbCharUp = aNbCharUp;
        this.nbCharLow = aNbCharLow;
    }
    
    public AsciiQuota(AsciiQuota orignalQuota) {
        this.nbSymbol = orignalQuota.getNbSymbol();
        this.nbNumber = orignalQuota.getNbNumber();
        this.nbCharUp = orignalQuota.getNbCharUp();
        this.nbCharLow = orignalQuota.getNbCharLow();
    }

    public int getNbSymbol() {
        return nbSymbol;
    }

    public void setNbSymbol(int nbSymbol) {
        this.nbSymbol = nbSymbol;
    }

    public int getNbNumber() {
        return nbNumber;
    }

    public void setNbNumber(int nbNumber) {
        this.nbNumber = nbNumber;
    }

    public int getNbCharLow() {
        return nbCharLow;
    }

    public void setNbCharLow(int nbCharLow) {
        this.nbCharLow = nbCharLow;
    }

    public int getNbCharUp() {
        return nbCharUp;
    }

    public void setNbCharUp(int nbCharUp) {
        this.nbCharUp = nbCharUp;
    }
    
}
