package org.tombear.rpcice.simple.server;

import com.zeroc.Ice.Application;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.server.service.HelloApiImpl;

public class Server extends Application {
    @Override
    public int run(String[] args) {
        if (args.length > 0) {
            System.err.println(appName() + ": too many arguments");
            return 1;
        }

        // create ObjectAdapter named MyServiceAdapter, using default TCP protocol and UDP protocol for Datagram and SSL for secure
        //ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("HelloAdp", "default -p 10000:udp -p 10000:ssl -p 10001");
        // or
        ObjectAdapter adapter = communicator().createObjectAdapter("HelloAdp");//without '.Endpoints' from config.client
        HelloApiImpl servant = new HelloApiImpl();
        adapter.add(servant, Util.stringToIdentity("helloObj"));
        adapter.activate();
        System.out.println("server started.");
        communicator().waitForShutdown();
        return 0;
    }

    public static void main(String[] args) {
        Server app = new Server();
        int status = app.main("Server", args, "config.server");
        System.exit(status);
    }
}
