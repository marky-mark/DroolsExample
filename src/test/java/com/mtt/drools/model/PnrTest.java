package com.mtt.drools.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PnrTest {

    @Test
    public void testIsWithInMinutes() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(2), "111", PnrType.AIR);
        assertThat(pnr.isWithinMinutes(5), equalTo(true));
    }

    @Test
    public void testIsNotWithInMinutes() {
        Pnr pnr = new Pnr((new DateTime()).minusMinutes(1), "222", PnrType.CAR);
        assertThat(pnr.isWithinMinutes(5), equalTo(false));
    }

    @Test
    public void testIsBeyondMinutes() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(6), "333", PnrType.HOTEL);
        assertThat(pnr.isWithinMinutes(5), equalTo(false));
    }

}
