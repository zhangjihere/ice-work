package org.tombear.rpcice.IceGrid.client;

import org.tombear.rpcice.IceGrid.utils.IceClientUtil;
import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSServicePrx;

/**
 * east client for IceGrid with IceBox
 * Created by ji.zhang on 1/29/18.
 */
public class EasyClient {

    public static void main(String[] args) {
        HelloApiPrx helloApiPrx = IceClientUtil.getServicePrx(HelloApiPrx.class);
        helloApiPrx = helloApiPrx.ice_twoway().ice_secure(false);
        System.out.printf("helloooo...");
        System.out.println(helloApiPrx.sayHello(3));
        System.out.println("ok!");

        SMSServicePrx smsServicePrx = IceClientUtil.getServicePrx(SMSServicePrx.class);
        smsServicePrx = smsServicePrx.ice_twoway().ice_secure(false);
        System.out.printf("sendooo...");
        System.out.println(smsServicePrx.sendSMS("See you, Sir!", 0, 2));
        System.out.println("Sms!");
    }
}
