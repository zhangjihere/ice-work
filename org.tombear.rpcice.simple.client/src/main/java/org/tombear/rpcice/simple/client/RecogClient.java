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

        int threadNum = 1;
        int workNum = 1;

        try {
            for (int i = 0; i < threadNum; i++) {
                runThread(i, workNum, threadNum, twoway);
            }
            Thread.sleep(1000 * 60 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static void runThread(int start, int time, int interval, RecogApiPrx twoway) throws InterruptedException {
        Thread thread = new Thread(() -> {
            boolean accept;
            String sessionID = "71574e02-df55-4c91-9abb-ccc314467f55";
            for (int i = start; i < time * interval; i += interval) {
                accept = twoway.acceptRequest(sessionID);
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
