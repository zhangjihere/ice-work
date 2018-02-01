package org.tombear.rpcice.IceGrid.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Queues;

import org.tombear.rpcice.IceGrid.utils.IceClientUtil;
import org.tombear.rpcice.simple.hello.gen.HelloApiPrx;
import org.tombear.rpcice.simple.sms.gen.SMSServicePrx;

/**
 * AsyncClient for Ice Service
 *
 * Created by ji.zhang on 1/30/18.
 */
public class AsyncClient {

    private static ConcurrentLinkedQueue<CompletableFuture<String>> stringResultQueue = Queues.newConcurrentLinkedQueue();

    public static void main(String[] args) {
        HelloApiPrx helloApiPrx = IceClientUtil.getServicePrx(HelloApiPrx.class);
        SMSServicePrx smsServicePrx = IceClientUtil.getServicePrx(SMSServicePrx.class);
        helloApiPrx = helloApiPrx.ice_twoway().ice_secure(false);
        smsServicePrx = smsServicePrx.ice_twoway().ice_secure(false);

        for (int i = 0; i < 10; i++) {
            stringResultQueue.add(helloApiPrx.sayHelloAsync(5));
            stringResultQueue.add(smsServicePrx.sendSMSAsync("Ok boy, ", i, 5));
        }
        System.out.println("Async requests are all sent OK!");

        while (!stringResultQueue.isEmpty()) {
            processPoll(stringResultQueue);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException ignore) {
            }
        }
        System.out.println("All Processed!");
    }

    private static <E> void processPoll(ConcurrentLinkedQueue<CompletableFuture<E>> queue) {
        while (!queue.isEmpty()) {
            CompletableFuture<E> result = queue.poll();
            if (result != null) {
                if (result.isDone()) {
                    E s = null;
                    try {
                        s = result.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.println("return result: " + s);
                } else {
                    queue.add(result);
                }
            }
        }
    }
}
