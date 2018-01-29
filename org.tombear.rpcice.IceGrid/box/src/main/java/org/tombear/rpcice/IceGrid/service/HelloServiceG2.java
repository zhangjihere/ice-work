package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.simple.hello.gen.HelloApi;
import org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus;
import org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg;
import org.tombear.rpcice.simple.hello.gen.entity.ResultMsg;

/**
 * another IceBoxService HelloSericeApi implement
 *
 * Created by ji.zhang on 1/29/18.
 */
public class HelloServiceG2 extends AbstractIceBoxService implements HelloApi {
    private static Logger logger = LoggerFactory.getLogger(HelloServiceG2.class);

    @Override
    public void sayHello(int delay, Current current) {
        try {
            Thread.sleep(1000 * delay);
        } catch (InterruptedException ignore) {
        }
        logger.debug("Hello IceBox for Grid.--> {}", this.name);
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

    }

    @Override
    public Object createMyIceServiceObj(String[] args) {
        return this;
    }
}
