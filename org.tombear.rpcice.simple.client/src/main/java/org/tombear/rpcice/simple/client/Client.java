package org.tombear.rpcice.simple.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;

public class Client {
    public static void main(String[] args) {
        int status = 0;
        List<String> extraArgs = new ArrayList<>();

        //
        // try with resource block - communicator is automatically destroyed
        // at the end of this try block
        //
        try (Communicator communicator = Util.initialize(args, "config.client", extraArgs)) {
            if (!extraArgs.isEmpty()) {
                System.err.println("too many arguments");
                status = 1;
            } else {
                status = run(communicator);
            }
        }

        System.exit(status);
    }

    private static int run(Communicator communicator) {
        // transfer remote Object Indentity and Endpoint to Proxy Object
        //ObjectPrx base = communicator.stringToProxy("helloObj:tcp -p 10000:udp -p 10000:ssl -p 10001");
        // or
        // Pass service unit perfix in config.client to create a Proxy Object
        ObjectPrx base = communicator.propertyToProxy("HelloNeverMind.Proxy");
        // Through checkedCast down forward change type and get remote Service proxy interface
        HelloApiPrx proxy = HelloApiPrx.checkedCast(base);// it has a rpc process for check, if improve performance, it can use uncheckCast
        HelloApiPrx twoway = proxy.ice_twoway().ice_secure(false);
        if (twoway == null) {
            System.err.println("invalid proxy");
            return 1;
        }
        HelloApiPrx oneway = twoway.ice_oneway();
        HelloApiPrx batchOneway = twoway.ice_batchOneway();
        HelloApiPrx datagram = twoway.ice_datagram();
        HelloApiPrx batchDatagram = twoway.ice_batchDatagram();

        boolean secure = false;
        int timeout = -1;
        int delay = 0;

        menu();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        do {
            try {
                System.out.print("==> ");
                System.out.flush();
                line = in.readLine();
                if (line == null) {
                    break;
                }
                if (line.equals("t")) {
                    twoway.sayHello(delay);
                } else if (line.equals("o")) {
                    oneway.sayHello(delay);
                } else if (line.equals("O")) {
                    batchOneway.sayHello(delay);
                } else if (line.equals("d")) {
                    if (secure) {
                        System.out.println("secure datagrams are not supported");
                    } else {
                        datagram.sayHello(delay);
                    }
                } else if (line.equals("D")) {
                    if (secure) {
                        System.out.println("secure datagrams are not supported");
                    } else {
                        batchDatagram.sayHello(delay);
                    }
                } else if (line.equals("f")) {
                    batchOneway.ice_flushBatchRequests();
                    batchDatagram.ice_flushBatchRequests();
                } else if (line.equals("T")) {
                    if (timeout == -1) {
                        timeout = 2000;
                    } else {
                        timeout = -1;
                    }

                    twoway = twoway.ice_invocationTimeout(timeout);
                    oneway = oneway.ice_invocationTimeout(timeout);
                    batchOneway = batchOneway.ice_invocationTimeout(timeout);

                    if (timeout == -1) {
                        System.out.println("timeout is now switched off");
                    } else {
                        System.out.println("timeout is now set to 2000ms");
                    }
                } else if (line.equals("P")) {
                    if (delay == 0) {
                        delay = 2500;
                    } else {
                        delay = 0;
                    }

                    if (delay == 0) {
                        System.out.println("server delay is now deactivated");
                    } else {
                        System.out.println("server delay is now set to 2500ms");
                    }
                } else if (line.equals("S")) {
                    secure = !secure;

                    twoway = twoway.ice_secure(secure);
                    oneway = oneway.ice_secure(secure);
                    batchOneway = batchOneway.ice_secure(secure);
                    datagram = datagram.ice_secure(secure);
                    batchDatagram = batchDatagram.ice_secure(secure);

                    if (secure) {
                        System.out.println("secure mode is now on");
                    } else {
                        System.out.println("secure mode is now off");
                    }
                } else if (line.equals("s")) {
                    twoway.shutdown();
                } else if (line.equals("x")) {
                    // Nothing to do
                } else if (line.equals("?")) {
                    menu();
                } else {
                    System.out.println("unknown command `" + line + "'");
                    menu();
                }
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            } catch (com.zeroc.Ice.LocalException ex) {
                ex.printStackTrace();
            }
        }
        while (!line.equals("x"));

        return 0;
    }

    private static void menu() {
        System.out.println(
                "usage:\n" +
                        "t: send greeting as twoway\n" +
                        "o: send greeting as oneway\n" +
                        "O: send greeting as batch oneway\n" +
                        "d: send greeting as datagram\n" +
                        "D: send greeting as batch datagram\n" +
                        "f: flush all batch requests\n" +
                        "T: set a timeout\n" +
                        "P: set a server delay\n" +
                        "S: switch secure mode on/off\n" +
                        "s: shutdown server\n" +
                        "x: exit\n" +
                        "?: help\n");
    }
}
