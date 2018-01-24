package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.tombear.rpcice.simple.sms.gen.SMSService;

/**
 * Sms service
 * Created by ji.zhang on 1/22/18.
 */
public class SmsServiceG implements SMSService, Service {
    @Override
    public void sendSMS(String msg, Current current) {
        System.out.println("recieve: " + msg);
    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        this.name = name;
        Properties props = communicator.getProperties();
        adapter = communicator.createObjectAdapter(props.getProperty("user.smsG.adapter.name"));
        adapter.add(this, Util.stringToIdentity(props.getProperty("user.smsG.object.name")));
        adapter.activate();
        System.out.println("start Service: " + name);
    }

    @Override
    public void stop() {
        adapter.destroy();
        System.out.println("destory adapter" + this.name);
    }

    private String name;
    private ObjectAdapter adapter;
}
