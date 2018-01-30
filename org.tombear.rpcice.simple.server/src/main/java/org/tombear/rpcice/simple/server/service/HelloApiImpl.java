package org.tombear.rpcice.simple.server.service;

import com.zeroc.Ice.Current;

import org.tombear.rpcice.simple.hello.gen.HelloApi;
import org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus;
import org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg;
import org.tombear.rpcice.simple.hello.gen.entity.ResultMsg;

/**
 * Service Implement
 * Created by ji.zhang on 1/16/18.
 */
public class HelloApiImpl implements HelloApi {

    @Override
    public String sayHello(int delay, Current current) {
        if (delay > 0) {
            try {
                Thread.currentThread();
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Hello World!");
        return "And You...";
    }

    @Override
    public ResultMsg process(ProcessMsg procMsg, Current current) {
        return null;
    }

    @Override
    public Cp4ccStatus check(String sessionId, Current current) {
        return null;
    }

    @Override
    public void shutdown(Current current) {
        System.out.println("Shutting down...");
        current.adapter.getCommunicator().shutdown();
    }
}
