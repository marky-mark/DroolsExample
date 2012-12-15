package com.mtt.drools.model;

import org.slf4j.Logger;

public abstract class BaseEvent {

    private boolean hasFired;

    public BaseEvent() {
        hasFired = false;
    }

    public boolean hasFired() {
        return hasFired;
    }

    public abstract void fire();

    public void fire(Logger logger, String...logParams) {

        String message = "";

        for(String partMsg : logParams) {
            message += partMsg;
        }

        logger.info(message);

        this.hasFired = true;

        //TODO: send message to jms Queue
    }
}
