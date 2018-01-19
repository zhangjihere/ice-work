package org.tombear.rpcice.icebox.service;

import com.zeroc.Ice.Current;

import org.tombear.rpcice.simple.hello.gen.HelloApi;
import org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus;
import org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg;
import org.tombear.rpcice.simple.hello.gen.entity.ResultMsg;

/**
 * new HelloApi implement
 * Created by ji.zhang on 1/19/18.
 */
public class HelloApiImpl2 implements HelloApi {

    @Override
    public void sayHello(int delay, Current current) {
        try {
            Thread.sleep(delay*1000);
        } catch (InterruptedException ignore) {
        }
        System.out.println("Hello IceBox!");

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
