package org.tombear.rpcice.IceGrid;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSServicePrx;

/**
 * create new client for SmsServiceG
 *
 * Created by ji.zhang on 1/22/18.
 */
public class SmsServiceGClient {
    public static void main(String[] args) {
        String[] initParams = new String[]{"--Ice.Default.Locator=DemoIceGrid/Locator:tcp -h localhost -p 4061"};
        try (Communicator communicator = Util.initialize(initParams)) {
//            ObjectPrx objectPrx = communicator.stringToProxy("HelloServiceG:tcp -h localhost -p 10000:udp -h localhost -p 10000");
            ObjectPrx objectPrx = communicator.stringToProxy("SmsGObject@SmsGAdapterId");
            SMSServicePrx proxy = SMSServicePrx.checkedCast(objectPrx);
            SMSServicePrx towWay = proxy.ice_twoway().ice_secure(false);
            System.out.printf("send... ");
            towWay.sendSMS("Hello Grid message!");
            System.out.println("OK!");
        }
    }
}
