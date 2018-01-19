package org.tombear.rpcice.icebox;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSService;

/**
 * SMS starter
 * Created by ji.zhang on 1/19/18.
 */
public class SMSWithImplStarter implements SMSService, Service {

    private Communicator communicator;
    private ObjectAdapter adapter;

    public SMSWithImplStarter(Communicator communicator) {
        this.communicator = communicator;
        System.out.println("construct " + this.getClass());
    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        // name(Service Name) from config.icebox properties IceBox.Service.<name>
        Object object = this;
        adapter = communicator.createObjectAdapter(name); // create Adapter is the same as Service Name
        adapter.add(object, Util.stringToIdentity(name)); //if or not necessary the to adapter name
        adapter.activate();
        System.out.println(name + " started");
    }

    @Override
    public void stop() {
        System.out.println(adapter.getName() + " stopped");
        adapter.destroy();
    }

    @Override
    public void sendSMS(String msg, Current current) {
        System.out.println("Hi SMS");
        if (msg.startsWith("Subscribe")) {
            System.out.print("  -->");
            ObjectPrx base = adapter.getCommunicator().stringToProxy("helloObj");
            HelloApiPrx helloApi = HelloApiPrx.checkedCast(base);
            helloApi.sayHello(1);
        } else {
            System.out.println("send normal SMS!");
        }
    }
}
