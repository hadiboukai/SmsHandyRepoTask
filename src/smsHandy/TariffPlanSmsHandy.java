package smsHandy;
import exceptions_package.SmsHandyException;
/**
 * KlasseTariffPlanSmsHandy. Ein Vertragshandy, das über eine bestimmte Menge an Frei-SMS verfügt.
 * In einer späteren Version könnten diese nach einer
 * bestimmten Zeit wieder zurückgesetzt werden. Dies wird vorerst noch nicht berücksichtigt.
 * @version 1.3
 */
public class TariffPlanSmsHandy extends SmsHandy {

    /**Menge frei Sms*/
    private int remainingFreeSms = 100;

    /**
     * Konstruktor zum Erstellen eines neuen TariffPlanHandy
     * @param modelName Model Name
     * @param number die Handynummer
     * @param providerName Provider Name
     * @param provider die Providerinstanz
     * @throws SmsHandyException
     */
    public TariffPlanSmsHandy(String modelName, String number, String providerName, Provider provider) throws SmsHandyException {
        super("tariffPlan",modelName,number,providerName,provider);
        super.provider.register(this);

    }

    /**
     * Prüft, ob Frei-SMS noch zum Senden ausreichen.
     * @return noch Frei-SMS vorhanden?
     */
    @Override
    public boolean canSendSms() {
        if(remainingFreeSms==0) {
            return false;
        }else {
            return true;
        }
    }


    /**
     * Reduziert die Frei-SMS.
     * @throws SmsHandyException
     */
    public void payForSms() throws SmsHandyException {
        checkRemainingFreeSms(remainingFreeSms);
        provider.deposit(number, provider.getCreditsForSmsHandy(number)-1);
    }

    /**
     * Liefert Anzahl der verbliebenen Frei-SMS.
     * @return Anzahl der Frei-SMS
     */
    public int getRemainingFreeSms() {
        return provider.getCreditsForSmsHandy(number);
    }

    /**
     * Pruft, das FreeSms gibt
     * @param remainingFreeSms
     * @throws SmsHandyException
     */
    private void checkRemainingFreeSms(int remainingFreeSms) throws SmsHandyException {
        if (remainingFreeSms < 0)
            throw new SmsHandyException("remaining free sms < 0");
    }
}
