package org.tombear.rpcice.icebox.client;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.sms.gen.SMSServicePrx;

/**
 *
 * Created by ji.zhang on 1/19/18.
 */
public class SMSClient {

    public static void main(String[] args) {
        int status = 0;
        Communicator ic = null;
        try {
            ic = Util.initialize(args);
            ObjectPrx base = ic.stringToProxy("SMSService:tcp -p 10010 -h localhost");
            SMSServicePrx smsService = SMSServicePrx.checkedCast(base);
            if (smsService == null) {
                throw new Error("Invalid proxy");
            }
            System.out.print("send...");
            smsService.sendSMS("Subscribe SMS!");
            System.out.println(" OK!");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        System.exit(status);

    }
}
