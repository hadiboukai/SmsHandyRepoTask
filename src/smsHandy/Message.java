package smsHandy;

import java.util.Date;
/**
 * Klasse Message
 * @version 1.8
 */

public class Message {
    /**Inhalt des Message*/
    private String content;
    /**Date dea Message*/
    private Date date;
    /**Absender*/
    private String from;
    /**Empfaenger*/
    private String to;

    /**
     *Konstruktor mit Parametern
     */
    public Message(String content, Date date, String from, String to) {
        this.content = content;
        this.date = date;
        this.from = from;
        this.to = to;
    }
    /**
     *Konstruktor ohne Parametern
     */
    public Message(){
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }

    /**
     * Gibt den Inhalt der Nachricht zurueck.
     * @return
     */
    public String getContent() { return content;
    }

    /**
     * Setzt den Inhalt der Nachricht.
     * @param content
     */
    public void setContent(String content) { this.content = content;
    }

    /**
     * Gibt das Erstellungsdatum der Nachricht zurück.
     * @return
     */
    public Date getDate() { return date;
    }

    /**
     * Setzt das Erstellungsdatum der SMS.
     * @param date
     */
    public void setDate(Date date) { this.date = date;
    }

    /**
     * Gibt den Absender der Nachricht zurueck.
     * @return
     */
    public String getFrom() { return from;
    }

    /**
     * Setzt den Absender der Nachricht.
     * @param from
     */
    public void setFrom(String from) { this.from = from;
    }

    /**
     * Gibt den Empfaenger zurueck.
     * @return
     */
    public String getTo() { return to;
    }

    /**
     * Setzt den Empfaenger der Nachricht.
     * @param to
     */
    public void setTo(String to) { this.to = to;
    }
}

