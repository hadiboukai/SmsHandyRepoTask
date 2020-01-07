package smsHandy;
import exceptions_package.SmsHandyException;

/**
 * Klasse PrepaidSmsHandy. Ein Handy, das über ein beim Provider verwaltetes Guthaben
 * verfügt und dessen SMS-Versand über dieses Guthaben abgerechnet wird.
 * @version 1.5
 */
public class PrepaidSmsHandy extends SmsHandy {
    /**preis SMS*/
    private final int COST_PER_SMS = 10;

    /**
     * Konstruktor zum Erstellen eines neuen PrepaidHandy
     * @param modelName Model Name
     * @param number die Handynummer
     * @param providerName Provider Name
     * @param provider die Providerinstanz
     * @throws SmsHandyException
     */
    public PrepaidSmsHandy(String modelName, String number, String providerName,Provider provider) throws SmsHandyException {

        super("prepaid",modelName,number,providerName,provider);
        super.provider.register(this);
    }

    /**
     * Prüft, ob das Guthaben noch für das Versenden einer SMS reicht.
     * @return ist das Guthaben noch ausreichend?
     */
    public boolean canSendSms(){
        return provider.getCreditsForSmsHandy(number)>10;
    }

    /**
     * Zieht die Kosten für eine SMS vom Guhaben ab.
     * @throws SmsHandyException
     */
    public void payForSms()throws SmsHandyException{
       provider.deposit(number,provider.getCreditsForSmsHandy(number)-COST_PER_SMS);

    }

    /**
     * Lädt das Guthaben fuer das SmsHandy-Objekt auf.
     * @param amount Menge, um die Aufgeladen werden soll
     * @throws SmsHandyException
     */
    public void deposit(int amount) throws SmsHandyException {
        checkAmount(amount);
        provider.deposit(number,provider.getCreditsForSmsHandy(number)+amount);
    }

    /**
     * prueft, das Menge mehr als 0
     * @param amount Menge, um die Aufgeladen werden soll
     * @throws SmsHandyException
     */
    private void checkAmount(int amount) throws SmsHandyException {
        if (amount < 0)
            throw new SmsHandyException("the amount can not be less than zero");
    }


}

