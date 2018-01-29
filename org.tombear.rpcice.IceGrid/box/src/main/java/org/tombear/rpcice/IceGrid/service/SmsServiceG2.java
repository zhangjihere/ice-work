package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.simple.sms.gen.SMSService;

/**
 * another IceBoxService HelloSericeApi implement
 * Created by ji.zhang on 1/29/18.
 */
public class SmsServiceG2 extends AbstractIceBoxService implements SMSService {
    private Logger logger = LoggerFactory.getLogger(SmsServiceG.class);

    @Override
    public void sendSMS(String msg, Current current) {
        logger.debug("receive sms: {}--> {}", msg, this.name);
    }

    @Override
    public Object createMyIceServiceObj(String[] args) {
        return this;
    }
}
