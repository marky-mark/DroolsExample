package com.mtt.drools.rules;

import com.mtt.drools.model.Pnr;
import com.mtt.drools.model.PnrType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath:META-INF/application-context.xml" })
public class PnrRuleTest {

    @Autowired
    private StatefulKnowledgeSession ksession;

    @Test
    public void testRulePasses() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(2), "MK123", PnrType.AIR);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(true));
    }

    @Test
    public void testFailsDueToType() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(2), "MK123", PnrType.HOTEL);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(false));
    }

    @Test
    public void testFailsDueToAnotherDifferentType() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(2), "MK123", PnrType.CAR);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(false));
    }

    @Test
    public void testFailsDueToBeforeTimeLimit() {
        Pnr pnr = new Pnr((new DateTime()).minusMinutes(1), "MK123", PnrType.AIR);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(false));
    }

    @Test
    public void testFailsDueToAfterTimeLimit() {
        Pnr pnr = new Pnr((new DateTime()).plusMinutes(6), "MK123", PnrType.AIR);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(false));
    }

    @Test
    public void testIsNotFiredIfHasBeenFiredBefore() {
        Pnr pnr = mock(Pnr.class);

        when(pnr.getDateTime()).thenReturn((new DateTime()).plusMinutes(2));
        when(pnr.getPnrType()).thenReturn(PnrType.AIR);
        when(pnr.hasFired()).thenReturn(true);
        ksession.insert(pnr);
        ksession.fireAllRules();
        assertThat(pnr.hasFired(), equalTo(true));
        verify(pnr, never()).fire();
    }
}
