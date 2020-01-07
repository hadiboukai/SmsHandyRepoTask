package test;

import exceptions_package.SmsHandyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smsHandy.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ProviderTest {

    private Provider mts;
    private Provider beeline;
    private PrepaidSmsHandy beelinePrepaidSmsHandy;
    private TariffPlanSmsHandy beelineTariffPlanSmsHandy;
    private PrepaidSmsHandy mtsPrepaidSmsHandy;
    @BeforeEach
    void setUp() throws SmsHandyException {
        this.mts = new Provider("MTC");
        this.beeline = new Provider("Beeline");
        this.beelinePrepaidSmsHandy = new PrepaidSmsHandy("beelinePrepaidSmsHandy","5555",this.beeline.getName(),this.beeline);
        this.beelineTariffPlanSmsHandy = new TariffPlanSmsHandy("beelineTariffPlanSmsHandy","7777",this.beeline.getName(),this.beeline);
        this.mtsPrepaidSmsHandy = new PrepaidSmsHandy("mtsPrepaidSmsHandy","6666",this.mts.getName(),this.mts);
    }

    @AfterEach
    void tearDown() {
        mts = null;
        beeline = null;
    }

    @Test
    public void register() {
        assertTrue(beeline.getSubscribers().containsKey("5555"));
    }

    @Test
    void send() {
        Message testMessage = new Message("Hallo", new Date(),beelinePrepaidSmsHandy.getNumber(), beelineTariffPlanSmsHandy.getNumber());
        beeline.send(testMessage);
        //assertEquals("100",beelinePrepaidSmsHandy.listSent().toString());
        //assertEquals("100",beelineTariffPlanSmsHandy.listReceived().toString());
    }

    @Test
    public void deposit() throws SmsHandyException {
        beelinePrepaidSmsHandy.deposit(50);
        assertEquals(150, beeline.getCreditsForSmsHandy(beelinePrepaidSmsHandy.getNumber()));
    }

    @Test
    void getCreditsForSmsHandy() throws SmsHandyException {
        assertEquals(100, beeline.getCreditsForSmsHandy(beelinePrepaidSmsHandy.getNumber()));
        beelinePrepaidSmsHandy.deposit(300);
        assertEquals(1100, beeline.getCreditsForSmsHandy(beelinePrepaidSmsHandy.getNumber()));
    }

    @Test
    void getSubscribers() {
        Map<String, SmsHandy> tests = new HashMap<>();
        tests.put("5555",this.beelinePrepaidSmsHandy);
        tests.put("7777",this.beelineTariffPlanSmsHandy);
        assertEquals(tests, beeline.getSubscribers());
    }

    @Test
    void candSendto() {
      //  assertTrue(beeline.candSendto("5555"));
        assertFalse(beeline.candSendto("5556"));
        assertTrue(mts.candSendto("6666"));
    }

    @Test
    void findProviderFor() {
        assertEquals(beeline.getName(), Provider.findProviderFor("5555").getName());

    }

    @Test
    void getName() {
        assertEquals("MTC",mts.getName());
        assertEquals("Beeline",beeline.getName());
    }
}