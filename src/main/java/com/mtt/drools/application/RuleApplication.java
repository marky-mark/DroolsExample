package com.mtt.drools.application;

import com.mtt.drools.model.Pnr;
import com.mtt.drools.model.PnrType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RuleApplication {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-context.xml");

        StatefulKnowledgeSession session = (StatefulKnowledgeSession) context.getBean("ksession");

        Pnr pnr1 = new Pnr((new DateTime()).plusMinutes(2), "MK123", PnrType.AIR);
        Pnr pnr2 = new Pnr((new DateTime()).plusMinutes(2), "MK500", PnrType.HOTEL);
        Pnr pnr3 = new Pnr((new DateTime()).plusMinutes(2), "IE6", PnrType.CAR);

        session.insert(pnr1);
        session.insert(pnr2);
        session.insert(pnr3);

        session.fireAllRules();
    }
}
