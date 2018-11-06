package org.tombear.rpcice.simple.client;

import java.util.ArrayList;
import java.util.List;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import org.tombear.rpcice.simple.backend.RecogApiPrx;


public class RecogClient {
    public static void main(String[] args) {
        int status = 0;
        List<String> extraArgs = new ArrayList<>();

        //
        // try with resource block - communicator is automatically destroyed
        // at the end of this try block
        //
        try (Communicator communicator = Util.initialize(args, "config.client.recog", extraArgs)) {
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
        ObjectPrx base = communicator.propertyToProxy("Cp4ccRecog.Proxy");
        RecogApiPrx proxy = RecogApiPrx.checkedCast(base);// it has a rpc process for check, if improve performance, it can use uncheckCast
        RecogApiPrx twoway = proxy.ice_twoway().ice_secure(false);
        if (twoway == null) {
            System.err.println("invalid proxy");
            return 1;
        }

        int interval = 5;
        try {
            for (int i = 0; i < interval; i++) {
                runThread(i, interval, twoway);
            }
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static void runThread(int start, int interval, RecogApiPrx twoway) throws InterruptedException {
        Thread thread = new Thread(() -> {
            boolean accept;
            String sessionID = "Thread-" + start + "-sid-";
            for (int i = start; i < 50 * interval; i += interval) {
                accept = twoway.acceptRequest(sessionID + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("accept: %b\n", accept);
            }
        }, "Thread-" + start);
        thread.start();
    }

}
