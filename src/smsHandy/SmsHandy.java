package smsHandy;

import exceptions_package.SmsHandyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstrakte Basisklasse SmsHandy.
 * @version 1.6
 */
public abstract class SmsHandy {

    protected String number;
    protected String modelName;
    protected Provider provider;
    private String type;
    protected String providerName;
    /**List fuer sent messege*/
    private List<Message> sentSmsList = new ArrayList<>();
    /**List fuer receive messege*/
    private ArrayList<Message> receivedSmsList = new ArrayList<>();

    /**
     * Konstruktor fuer Objekte der Klasse SmsHandy
     * @param type  das Type
     * @param modelName derModelname
     * @param number die Handynummer
     * @param providerName der ProviderName
     * @param provider die Providerinstanz
     */
    public SmsHandy(String type, String modelName, String number, String providerName, Provider provider) {
        this.type = type;
        this.modelName = modelName;
        this.number=number;
        this.providerName = providerName;
        this.provider = provider;
    }

    /**
     * Abstrakte Methode zur Prüfung, ob der Versand der SMS noch bezahlt werden kann.
     * @return ist der Versand der SMS noch möglich?
     */
    abstract public boolean canSendSms();

    /**
     * Abstrakte Methode zum Bezahlen des SMS-Versand.
     * @throws SmsHandyException
     */
    abstract public void payForSms() throws SmsHandyException;

    /**
     * Schickt eine SMS ueber den Provider an den Empfaenger.
     * @param to der Empfaenger der SMS
     * @param content der Inhalt der SMS
     */
    public void sendSms(String to, String content){
        Message message = new Message(content, new Date(), this.getNumber(), to);
        provider.send(message);
    }

    /**
     * Schickt eine SMS ohne den Provider an den Empfaenger
     * @param peer das empfangende Handy
     * @param msg der Inhalt der SMS
     */
    public void sendSmsDirect(SmsHandy peer, String msg){
        Message message = new Message(msg, new Date(), this.getNumber(), peer.getNumber());
        this.sentSmsList(message);
        peer.receiveSms(message);
    }

    /**
     * Empfaengt eine SMS und speichert diese in den empfangenen SMS
     * @param message das Message-Objekt, welches an das zweite Handy gesendet werden soll
     * @throws OutOfMemoryError
     */
    public void receiveSms(Message message) throws OutOfMemoryError{
        receivedSmsList.add(message);
    }

    /**
     * Speichert message in sentSmsList
     * @param message das Message-Objekt
     */
    public void sentSmsList(Message message){
        sentSmsList.add(message);
    }

    /**
     * Gibt eine Liste aller gesendete SMS aus.
     * @return List<Message>
     */
    public List<Message> listSent(){
        return sentSmsList;
    }

    /**
     * Gibt eine Liste aller empfangenen SMS aus.
     * @return List<Message>
     */
    public List<Message> listReceived() { return receivedSmsList;
    }

    public String getNumber(){ return number;
    }

    public void setNumber(String number) { this.number = number;
    }

    public String getType() { return type;
    }

    public void setType(String type) { this.type = type;
    }

    public String getModelName() { return modelName;
    }

    public Provider getProvider() { return provider;
    }

    public void setProvider(Provider provider) { this.provider = provider;
    }

    public String getProviderName() { return providerName;
    }

    public void setProviderName(String providerName) { this.providerName = providerName;
    }

    public void setProvider(String provider) { this.providerName = provider;
    }
}
