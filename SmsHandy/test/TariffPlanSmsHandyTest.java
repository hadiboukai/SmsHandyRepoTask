package test;

import exceptions_package.SmsHandyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smsHandy.Provider;
import smsHandy.TariffPlanSmsHandy;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;

public class TariffPlanSmsHandyTest {

    private Provider megafon;
    private TariffPlanSmsHandy megafonTariffPlanSmsHandy1;
    private TariffPlanSmsHandy megafonTariffPlanSmsHandy2;
    private Provider tele2;
    private TariffPlanSmsHandy tele2TariffPlanSmsHandy;

    @Before
    public void initTest() throws Exception {
        this.megafon = new Provider("Megafon");
        this.megafonTariffPlanSmsHandy1 = new TariffPlanSmsHandy("megafonTariffPlanSmsHandy1","5555", this.megafon.getName(),this.megafon);
        this.megafonTariffPlanSmsHandy2 = new TariffPlanSmsHandy("megafonTariffPlanSmsHandy2","6666", this.megafon.getName(),this.megafon);

        megafon.register(megafonTariffPlanSmsHandy1);
        megafon.register(megafonTariffPlanSmsHandy2);

        this.tele2 = new Provider("Tele2");
        this.tele2TariffPlanSmsHandy = new TariffPlanSmsHandy("tele2TariffPlanSmsHandy","7777", this.tele2.getName(),this.tele2);

        tele2.register(tele2TariffPlanSmsHandy);
    }

    @After
    public void afterTest() throws Exception {
        megafon = null;
        megafonTariffPlanSmsHandy1 = null;
        megafonTariffPlanSmsHandy2 = null;
        tele2 = null;
        tele2TariffPlanSmsHandy = null;
    }

    @Test
    public void canSendSms() throws SmsHandyException {
        assertTrue(megafonTariffPlanSmsHandy1.canSendSms());
        for (int i = 0; i < 100 ; i++) {
            tele2TariffPlanSmsHandy.payForSms();
        }
        assertFalse(tele2TariffPlanSmsHandy.canSendSms());
    }

    @Test
    public void payForSms() throws SmsHandyException {
        megafonTariffPlanSmsHandy1.payForSms();
        assertEquals(99, megafonTariffPlanSmsHandy1.getRemainingFreeSms());
    }

    @Test
    public void getRemainingFreeSms() throws SmsHandyException {
        for (int i = 0; i < 10 ; i++) {
            megafonTariffPlanSmsHandy2.payForSms();
        }
        assertEquals(90, megafonTariffPlanSmsHandy2.getRemainingFreeSms());
    }
}