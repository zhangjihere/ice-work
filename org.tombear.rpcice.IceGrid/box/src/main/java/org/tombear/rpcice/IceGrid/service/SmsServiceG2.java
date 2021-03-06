package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.IceGrid.utils.IceClientUtil;
import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSService;

/**
 * another IceBoxService HelloSericeApi implement
 * In method sendSMS, it has a nested request HelloApi Service
 * Created by ji.zhang on 1/29/18.
 */
public class SmsServiceG2 extends AbstractIceBoxService implements SMSService {
    private static Logger logger = LoggerFactory.getLogger(SmsServiceG2.class);

    @Override
    public String sendSMS(String msg, int index, int delay, Current current) {
        logger.debug("receive sms: {}--> {}", msg, this.name);
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException ignore) {
        }
        // Nested request HelloApi Service
        HelloApiPrx helloApiPrx = IceClientUtil.getServicePrx(HelloApiPrx.class);
        helloApiPrx = helloApiPrx.ice_twoway().ice_secure(false);
        String s = helloApiPrx.sayHello(2);
        return "BingGo Sms. " + index + " --> sayHello return: " + s;
    }

    @Override
    public Object createMyIceServiceObj(String[] args) {
        return this;
    }
}
