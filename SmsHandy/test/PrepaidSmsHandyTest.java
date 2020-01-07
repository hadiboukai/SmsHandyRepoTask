package test;


import exceptions_package.SmsHandyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smsHandy.PrepaidSmsHandy;
import smsHandy.Provider;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * class PrepaidSmsHandyTest
 */
public class PrepaidSmsHandyTest {

    private Provider beeline;
    private PrepaidSmsHandy beelinePrepaidSmsHandy1;
    private PrepaidSmsHandy beelinePrepaidSmsHandy2;
    private Provider mts;
    private PrepaidSmsHandy mtsPrepaidSmsHandy;

    @Before
    public void initTest() throws SmsHandyException {
        this.beeline = new Provider("Beeline");

        this.beelinePrepaidSmsHandy1 = new PrepaidSmsHandy("beelinePrepaidSmsHandy1","5555",this.beeline.getName(),this.beeline);
        this.beelinePrepaidSmsHandy2 = new PrepaidSmsHandy("beelinePrepaidSmsHandy2","6666",this.beeline.getName(),this.beeline);

        //beeline.register(beelinePrepaidSmsHandy1);
        //beeline.register(beelinePrepaidSmsHandy2);

        this.mts = new Provider("MTC");
        this.mtsPrepaidSmsHandy = new PrepaidSmsHandy("mtsPrepaidSmsHandy","7777",this.mts.getName(),this.mts);

        //mts.register(mtsPrepaidSmsHandy);

    }

    @After
    public void afterTest() {
        beeline = null;
        mts = null;
        beelinePrepaidSmsHandy1 = null;
        beelinePrepaidSmsHandy2 = null;
        mtsPrepaidSmsHandy = null;
    }

    @Test
    public void canSendSms() {
        assertTrue(mtsPrepaidSmsHandy.canSendSms());
        assertTrue(beelinePrepaidSmsHandy1.canSendSms());
        assertTrue(beelinePrepaidSmsHandy2.canSendSms());
    }

    @Test
    public void payForSms() throws SmsHandyException {
        beelinePrepaidSmsHandy1.payForSms();
        assertEquals(90,beeline.getCreditsForSmsHandy(beelinePrepaidSmsHandy1.getNumber()));
        beelinePrepaidSmsHandy2.payForSms();
        beelinePrepaidSmsHandy2.payForSms();
        assertEquals(80,beeline.getCreditsForSmsHandy(beelinePrepaidSmsHandy2.getNumber()));
    }

    @Test
    public void deposit() throws SmsHandyException {
        mtsPrepaidSmsHandy.deposit(100);
        assertEquals(200, mts.getCreditsForSmsHandy(mtsPrepaidSmsHandy.getNumber()));
    }
}