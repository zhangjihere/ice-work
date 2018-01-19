package org.tombear.rpcice.icebox;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.tombear.rpcice.icebox.service.HelloApiImpl2;

/**
 * IceBox starter
 * Created by ji.zhang on 1/19/18.
 */
public class HelloApiStarter implements Service {

    private Communicator communicator;
    private ObjectAdapter adapter;
    private String iceObject;

    public HelloApiStarter(Communicator communicator) {
        this.communicator = communicator;
        System.out.println("construct " + this.getClass());
    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        // name(Service Name) from config.icebox properties IceBox.Service.<name>
        Object object = new HelloApiImpl2();
        for (String arg : args) {
            if (arg.startsWith("iceObject")) {
                iceObject = arg.substring(arg.indexOf("=") + 1);
            }
        }
        adapter = communicator.createObjectAdapter(name); // create Adapter is same as Service Name
        adapter.add(object, Util.stringToIdentity(iceObject)); //if or not necessary the to adapter name
        adapter.activate();
        System.out.println(name + " started");
    }

    @Override
    public void stop() {
        System.out.println(adapter.getName() + " stopped");
        adapter.destroy();
    }
}
