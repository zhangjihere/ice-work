package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.simple.sms.gen.SMSService;

/**
 * Sms service
 * Created by ji.zhang on 1/22/18.
 */
public class SmsServiceG implements SMSService, Service {

    private Logger logger = LoggerFactory.getLogger(SmsServiceG.class);

    @Override
    public String sendSMS(String msg, int index, int delay, Current current) {
        logger.debug("receive sms: {}--> {}", msg, this.name);
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException ignore) {
        }
        return "BingGo Sms. " + index;
    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        this.name = name;
        Properties props = communicator.getProperties();
        adapter = communicator.createObjectAdapter(name);
        adapter.add(this, Util.stringToIdentity(props.getProperty("Service.Identity")));
        adapter.activate();
        logger.debug("start Service: {}", name);
    }

    @Override
    public void stop() {
        adapter.destroy();
        logger.debug("destory adapter {}", this.name);
    }

    private String name;
    private ObjectAdapter adapter;
}
