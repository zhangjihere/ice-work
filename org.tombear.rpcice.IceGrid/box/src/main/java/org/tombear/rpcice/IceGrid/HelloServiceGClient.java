package org.tombear.rpcice.IceGrid;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSServicePrx;

/**
 * create new client for HelloServiceG and SmsServiceG
 *
 * Created by ji.zhang on 1/22/18.
 */
public class HelloServiceGClient {
    public static void main(String[] args) {
        String[] initParams = new String[]{"--Ice.Default.Locator=DemoIceGrid/Locator:tcp -h localhost -p 4061"};
        try (Communicator communicator = Util.initialize(initParams)) {
//            ObjectPrx objectPrx = communicator.stringToProxy("HelloServiceG:tcp -h localhost -p 10000:udp -h localhost -p 10000");
//            ObjectPrx objectPrx = communicator.stringToProxy("HelloGObject@HelloGAdapterId");// normal IceBox registry
            //HelloService
            ObjectPrx objectPrx = communicator.stringToProxy("hello");
            HelloApiPrx proxy = HelloApiPrx.checkedCast(objectPrx);
            HelloApiPrx helloTowWay = proxy.ice_twoway().ice_secure(false);
            System.out.printf("say... ");
            helloTowWay.sayHello(1);
            System.out.println("Hello!");
            //SmsService
            ObjectPrx smsObjPrx = communicator.stringToProxy("sms");
            SMSServicePrx smsPrx = SMSServicePrx.checkedCast(smsObjPrx);
            SMSServicePrx smsPrxTwoWay = smsPrx.ice_twoway().ice_secure(false);
            System.out.printf("send...");
            smsPrxTwoWay.sendSMS("See you tomorrow, Boy!");
            System.out.println("Sms!");

        }
    }
}
