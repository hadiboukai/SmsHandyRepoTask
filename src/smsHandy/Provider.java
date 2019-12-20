package smsHandy;

import exceptions_package.SmsHandyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.swing.text.html.ListView;
import java.util.*;

/**
 * Klasse Provider
 * @version 1.8
 */
public class Provider extends Object {
    /**Name des Provider*/
    private String name;
    /*** Map fuer Speichern Guthaben*/
    private Map<String, Integer> credits = new HashMap<>();
    /** ArrayList alles Providers */
    public static ArrayList<Provider>providerList = new ArrayList<>();
    /** List fuer Speichern alle Subscribers des Provider */
    public Map<String,SmsHandy> subscribers = new HashMap<String,SmsHandy>();

    /**
     * Konstruktor fuer Objekte der Klasse Provider
     * @param name Namen des Providers
     */
    public Provider(String name) {
        this.name = name;
        providerList.add(this);

    }

    /**
     * Registriert ein neues Handy bei diesem Provider und hinzufuegt in zwei Liste.
     * @param smsHandy smsHandy - das neue Handy
     * @throws SmsHandyException
     */
    public void register(SmsHandy smsHandy) throws SmsHandyException{
        checkRegisterNummer(smsHandy.getNumber());
        subscribers.put(smsHandy.getNumber(),smsHandy);
        credits.put(smsHandy.getNumber(),100);
    }

    /**
     * Sendet die SMS an den Empfaenger, wenn dieser bekannt ist.
     * @param message die zu sendente SMS
     */
    public void send(Message message){

        String to = message.getTo();
        String from = message.getFrom();
        if (to.equals("#101")) {
            Provider providerFrom = findProviderFor(from);
            SmsHandy smsHandyFrom = providerFrom.getSubscriber(from);
            int amountSum = smsHandyFrom.getProvider().getCreditsForSmsHandy(from);
            String amount = Integer.toString(amountSum);
            Message amountReport = new Message(amount, new Date(),from, to);
            smsHandyFrom.receiveSms(amountReport);
            return;
        }

        else {
            Provider providerTo = findProviderFor(to);
            Provider providerFrom = findProviderFor(from);

            SmsHandy smsHandyTo = providerTo.getSubscriber(to);
            SmsHandy smsHandyFrom = providerFrom.getSubscriber(from);

            smsHandyTo.receiveSms(message);
            smsHandyFrom.sentSmsList(message);
        }

    }

    /**
     * Laedt Guthaben für ein Handy auf. Das ist noetig, weil das Handy sein Guthaben nicht selbst aendern kann, sondern nur der Provider. Negative Geldmengen werden hier erlaubt, um ueber diese Funktion auch die Kosten fuer eine Nachricht abziehen zu koennen. Negative Werte beim haendischen Aufladen werden in der Klasse SmsHandy verhindert.
     * @param number Nummer des Telefons
     * @param amount Hoehe des Geldbetrages
     * @throws SmsHandyException
     */
    public void deposit(String number, int amount) throws SmsHandyException{

        checkDeposit(amount);
        credits.replace(number,amount);

    }

    /**
     * Gibt das aktuelle Guthaben des betreffenden Handys zurück
     * @param number Nummer des gewuenschten Handys
     * @return aktuelles Guthaben des Handys
     * @throws NoSuchElementException
     */
    public int getCreditsForSmsHandy(String number) throws NoSuchElementException{

        return credits.get(number);

    }

    /**
     * Gibt das SmsHandy durch des Nummer zurück
     * @param number Nummer des Hendys
     * @return SmsHandy
     * @throws NoSuchElementException
     */
    public SmsHandy getSubscriber(String number) throws NoSuchElementException {
        return subscribers.get(number);
    }

    /**
     * Gibt alle Subscribers dieser Provider zurück
     * @return Map fuer Subscribers
     */
    public Map<String,SmsHandy> getSubscribers(){
        return subscribers;
    }

    /**
     * Prueft, gibt es Nummer in Subscribers List
     * @param receiver
     * @return true, wenn Nummer gibt's, false in anderen Situation
     * @throws NoSuchElementException
     */
    public boolean candSendto(String receiver) throws NoSuchElementException{

        return subscribers.containsKey(receiver);

    }

    /**
     * Sucht Provider fuer Nummer
     * @param receiver
     * @return Provider
     * @throws NoSuchElementException
     */
    public static Provider findProviderFor(String receiver) throws NoSuchElementException{
                return  providerList.stream().filter(x->x.candSendto(receiver)).findFirst().orElse(null);
       //return null;
    }

    /**
     * Gibt String Name des Provider zurueck
     * @return String Name
     */
    public String getName() {
        return name;
    }

    /**
     * Überprüfen Sie, ob eine Nummer in unserer Datenbank vorhanden ist, falls ein Fehler vorliegt.
     * @param number register Nummer
     * @throws SmsHandyException
     */
    private void checkRegisterNummer(String number) throws SmsHandyException {
        if (subscribers.containsKey(number) && credits.containsKey(number))
            throw new SmsHandyException("Number already registered");
    }

    /**
     * Überprüft, dass  wir die Hoehe des Geldbetrages auffüllen.
     * @param amount
     * @throws SmsHandyException
     */
    private void checkDeposit(int amount) throws SmsHandyException {
        if (amount < 0 || amount > 500)
            throw new SmsHandyException("unrealistic request");
    }

    public static void main(String[] args) throws SmsHandyException {
        Provider beeline = new Provider("Beeline");
        Provider mts = new Provider("mts");
        PrepaidSmsHandy mtsPrepaidSmsHandy = new PrepaidSmsHandy("mtsPrepaidSmsHandy", "9999", mts.getName(), mts);
        PrepaidSmsHandy beelinePrepaidSmsHandy = new PrepaidSmsHandy("beelinePrepaidSmsHandy", "5555", beeline.getName(), beeline);
        TariffPlanSmsHandy beelineTariffPlanSmsHandy = new TariffPlanSmsHandy("beelineTariffPlanSmsHandy", "7777", beeline.getName(), beeline);
        Message testMessage = new Message("Hallo", new Date(), beelinePrepaidSmsHandy.getNumber(), "#101");

        List<SmsHandy>smsHandyList = new ArrayList<SmsHandy>();
        smsHandyList.add(mtsPrepaidSmsHandy);
        smsHandyList.add(beelineTariffPlanSmsHandy);
        System.out.println(smsHandyList.get(0).getProvider().getCreditsForSmsHandy(smsHandyList.get(0).getNumber()));
        smsHandyList.get(0).payForSms();
        System.out.println(smsHandyList.get(0).getProvider().getCreditsForSmsHandy(smsHandyList.get(0).getNumber()));

//        System.out.println(beelinePrepaidSmsHandy.listSent());
//        System.out.println(beelinePrepaidSmsHandy.listReceived());
        //System.out.println(mtsPrepaidSmsHandy.listReceived());
    }
}
